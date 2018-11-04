package alivio.controller;

import alivio.model.Debtor;
import alivio.model.PaymentForm;
import alivio.views.builders.DebtorViewBuilder;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import alivio.config.GlobalProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
public class PaymentController {
    private static final Logger logger = LoggerFactory
            .getLogger(PaymentController.class);
    private GlobalProperties globalProperties;
    private ObjectMapper objectMapper;
    private RestTemplate restTemplate;

    public PaymentController(GlobalProperties globalProperties, ObjectMapper objectMapper, RestTemplateBuilder restTemplateBuilder) {
        this.globalProperties = globalProperties;
        this.objectMapper = objectMapper;
        this.restTemplate = restTemplateBuilder.build();
    }

    @RequestMapping(value = "/payment", method = RequestMethod.GET)
    public ModelAndView getDebtorBalance(HttpSession session) {
        String ssnAttribute = (String) session.getAttribute("ssn");
        final String resourceUri = globalProperties.getApiUrl()
                .concat(globalProperties.getApiBalanceEndpoint() + ssnAttribute);
        try {
            ModelAndView balance = new ModelAndView("paymentForm");
            Debtor debtor = restTemplate.getForObject(resourceUri, Debtor.class);
            session.setAttribute("debtor", debtor);
            balance.addObject("debtorview", new DebtorViewBuilder(debtor).build());
            balance.addObject("paymentForm", new PaymentForm());
            return balance;
        } catch (HttpServerErrorException | HttpClientErrorException e) {
            ModelAndView clientForm = new ModelAndView("verifyClientForm");
            clientForm.addObject("errorMsg", "There was problem with debts information availability. Try later.");
            logger.info("HttpServer error: {}. Returning paymentForm.html page", e.getMessage());
            return clientForm;
        }
    }

    @RequestMapping(value = "/payment", method = RequestMethod.POST)
    public ModelAndView saveDebtorPayment(HttpSession session,
                                          @Valid PaymentForm paymentForm,
                                          BindingResult bindingResult) {

        final String resourceUri = globalProperties.getApiUrl().concat(globalProperties.getApiPaymentEndpoint());
        Debtor debtor = (Debtor) session.getAttribute("debtor");
        ModelAndView paymentFormModel = new ModelAndView("paymentForm");
        paymentFormModel.addObject("debtorview", new DebtorViewBuilder(debtor).build());

        if (bindingResult.hasErrors()) {
            logger.info("Returning paymentForm.html page");
            return paymentFormModel;
        }

        paymentForm.setSsn(debtor.getSsn());
        paymentFormModel.addObject("paymentForm", paymentForm);

        try {
            String json = objectMapper.writeValueAsString(paymentForm);
            logger.info("Created JSON: {}", json);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<String> entity = new HttpEntity<>(json, headers);
            logger.info("Sending JSON format data to: {}", resourceUri);

            ResponseEntity<String> response = restTemplate.postForEntity(
                    resourceUri, entity, String.class);
            logger.info("Sending JSON as HTTPEntity: {}", json);

            if (HttpStatus.OK.equals(response.getStatusCode())) {
                logger.info("HttpStatus: {}", response.getStatusCode());
                logger.info("Redirect to: clientResult");
                return new ModelAndView("clientResult");
            } else {
                logger.info("HttpStatus is NOT OK. Returning verifyClientForm.html page");
                paymentFormModel.addObject("errorMsg", "Your data are incorrect. Enter the correct data!");
                return new ModelAndView("verifyClientForm");
            }
        } catch (JsonProcessingException e) {
            logger.info("HttpClient error: {}. Returning paymentForm.html page", e.getMessage());
            paymentFormModel.addObject("errorMsg", "Your data are incorrect. Enter the correct data!");
            return paymentFormModel;
        } catch (HttpClientErrorException e) {
            logger.info("HttpClient error: {}. Returning paymentForm.html page", e.getStatusCode());
            paymentFormModel.addObject("errorMsg", "Your data are incorrect. Enter the correct data!");
            return paymentFormModel;
        } catch (HttpServerErrorException | ResourceAccessException e) {
            logger.info("HttpServer error: {}. Returning paymentForm.html page", e.getMessage());
            paymentFormModel.addObject("errorMsg", "Connection error. Try again!");
            return paymentFormModel;
        }
    }
}