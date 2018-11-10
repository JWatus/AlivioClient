package alivio.controller;

import alivio.config.GlobalProperties;
import alivio.providers.ResourcesProviderUtil;
import alivio.views.debtor.DebtorView;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.test.web.client.response.MockRestResponseCreators;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.math.BigDecimal;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;

@RunWith(SpringRunner.class)
@RestClientTest(PaymentController.class)
@EnableConfigurationProperties(GlobalProperties.class)
public class PaymentControllerTest {
    private String apiUri;
    private String resourceEndpoint;
    private String balanceJson;
    private HttpSession session;
    @Autowired
    private PaymentController paymentController;
    @Autowired
    private MockRestServiceServer server;
    @Autowired
    private GlobalProperties globalProperties;

    @Before
    public void setUpApiServerMock() throws IOException {
        apiUri = globalProperties.getApiUrl();
        resourceEndpoint = globalProperties.getApiBalanceEndpoint();
        balanceJson = ResourcesProviderUtil.getFileContent("balance.json");
        session = new MockHttpSession();
    }

    @Test
    public void shouldGetBalanceJsonAndReturnModelAndView() {
        //given
        String existingResource = "980-122-112";
        server.expect(requestTo(apiUri.concat(resourceEndpoint).concat(existingResource)))
                .andExpect(method(HttpMethod.GET))
                .andRespond(MockRestResponseCreators.withSuccess(balanceJson, MediaType.APPLICATION_JSON));
        session.setAttribute("ssn", existingResource);

        //when
        ModelAndView modelAndView = paymentController.getDebtorBalance(session);

        //then
        DebtorView debtorView = (DebtorView) modelAndView.getModel().get("debtorview");
        assertThat(debtorView.getDebtorSsn(), equalTo("980-122-112"));
        assertThat(debtorView.getDebtorFirstName(), equalTo("Joe"));
        assertThat(debtorView.getDebtorLastName(), equalTo("Doe"));
        assertThat(debtorView.getDebtViewList().size(), equalTo(4));
        assertThat(debtorView.getDebtorSummaryView().getDebtsAmountSum(), equalTo(new BigDecimal("149000.00")));
        assertThat(debtorView.getDebtorSummaryView().getDebtsNumberSum(), equalTo(4));
        assertThat(debtorView.getDebtorSummaryView().getRemainingAmountSum(), equalTo(new BigDecimal("132813.00")));
    }

    @Test
    public void shouldRedirectToFormWithErrorMessageWhenBadRequestFromApi() {
        //given
        String notExistingResource = "980-122-110";
        server.expect(requestTo(apiUri.concat(resourceEndpoint).concat(notExistingResource)))
                .andExpect(method(HttpMethod.GET))
                .andRespond(MockRestResponseCreators.withBadRequest());
        session.setAttribute("ssn", notExistingResource);

        //when
        ModelAndView modelAndView = paymentController.getDebtorBalance(session);
        String errorMsg = (String) modelAndView.getModel().get("errorMsg");

        //then
        assertThat(errorMsg, equalTo("There was problem with debts information availability. Try later."));
    }

    @Test
    public void shouldRedirectToFormWithErrorMessageWhenServerError() {
        //given
        String serverErrorResource = "980-122-109";
        server.expect(requestTo(apiUri.concat(resourceEndpoint).concat(serverErrorResource)))
                .andExpect(method(HttpMethod.GET))
                .andRespond(MockRestResponseCreators.withServerError());
        session.setAttribute("ssn", serverErrorResource);

        //when
        ModelAndView modelAndView = paymentController.getDebtorBalance(session);
        String errorMsg = (String) modelAndView.getModel().get("errorMsg");

        //then
        assertThat(errorMsg, equalTo("There was problem with debts information availability. Try later."));
    }
}
