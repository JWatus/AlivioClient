package alivio.controller;

import alivio.jms.producer.JmsProducer;
import alivio.model.*;
import alivio.views.builders.DebtorViewBuilder;
import alivio.views.builders.PaymentPlanViewBuilder;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import alivio.config.GlobalProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.i18n.LocaleContextHolder;
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
public class PaymentConfirmationController {
    private static final Logger logger = LoggerFactory
            .getLogger(PaymentConfirmationController.class);
    private GlobalProperties globalProperties;
    private ObjectMapper objectMapper;
    private RestTemplate restTemplate;
    private JmsProducer jmsProducer;

    public PaymentConfirmationController(GlobalProperties globalProperties, ObjectMapper objectMapper, RestTemplateBuilder restTemplateBuilder, JmsProducer jmsProducer) {
        this.globalProperties = globalProperties;
        this.objectMapper = objectMapper;
        this.restTemplate = restTemplateBuilder.build();
        this.jmsProducer = jmsProducer;
    }

    @RequestMapping(value = "/paymentconfirmation", method = RequestMethod.GET)
    public ModelAndView showPaymentConfirmationForm(HttpSession session) {
        Debtor debtor = (Debtor) session.getAttribute("debtor");
        PaymentPlan paymentPlan = (PaymentPlan) session.getAttribute("paymentPlan");

        try {
            ModelAndView balance = new ModelAndView("paymentConfirmation");
            balance.addObject("debtorview", new DebtorViewBuilder(debtor).build());
            balance.addObject("paymentPlanView", new PaymentPlanViewBuilder(debtor, paymentPlan).build());

            PaymentConfirmation paymentConfirmation = new PaymentConfirmation(new PaymentDeclaration(), new CreditCard(CreditCardType.VISA));
            balance.addObject("paymentConfirmation", paymentConfirmation);
            return balance;
        } catch (HttpServerErrorException | HttpClientErrorException e) {
            ModelAndView clientForm = new ModelAndView("verifyClientForm");
            clientForm.addObject("errorMsg", "There was problem with debts information availability. Try later.");
            logger.info("HttpServer error: {}. Returning verifyClientForm.html page", e.getMessage());
            return clientForm;
        }
    }

    @RequestMapping(value = "/paymentConfirmationCreditCard", method = RequestMethod.GET)
    public ModelAndView showPaymentConfirmationCreditCardForm(HttpSession session) {
        Debtor debtor = (Debtor) session.getAttribute("debtor");
        PaymentPlan paymentPlan = (PaymentPlan) session.getAttribute("paymentPlan");

        try {
            ModelAndView balance = new ModelAndView("paymentConfirmationCreditCard");
            balance.addObject("debtorview", new DebtorViewBuilder(debtor).build());
            balance.addObject("paymentPlanView", new PaymentPlanViewBuilder(debtor, paymentPlan).build());

            PaymentConfirmation paymentConfirmation = new PaymentConfirmation(new PaymentDeclaration(), new CreditCard(CreditCardType.VISA));
            balance.addObject("paymentConfirmation", paymentConfirmation);
            return balance;
        } catch (HttpServerErrorException | HttpClientErrorException e) {
            ModelAndView clientForm = new ModelAndView("verifyClientForm");
            clientForm.addObject("errorMsg", "There was problem with debts information availability. Try later.");
            logger.info("HttpServer error: {}. Returning verifyClientForm.html page", e.getMessage());
            return clientForm;
        }
    }

    @RequestMapping(value = "/paymentConfirmationCreditCard", method = RequestMethod.POST)
    public ModelAndView createPaymentConfirmation(HttpSession session,
                                                  @Valid PaymentConfirmation paymentConfirmation,
                                                  BindingResult bindingResult) throws JsonProcessingException {

        final String resourceUri = globalProperties.getApiUrl().concat(globalProperties.getApiPaymentMethodsCreditcardEndpoint());

        Debtor debtor = (Debtor) session.getAttribute("debtor");
        PaymentPlan paymentPlan = (PaymentPlan) session.getAttribute("paymentPlan");
        paymentConfirmation.setPaymentDeclaration((PaymentDeclaration) session.getAttribute("paymentDeclaration"));

        ModelAndView paymentConfirmationModel = new ModelAndView("paymentConfirmationCreditCard");
        paymentConfirmationModel.addObject("debtorview", new DebtorViewBuilder(debtor).build());
        paymentConfirmationModel.addObject("paymentPlanView", new PaymentPlanViewBuilder(debtor, paymentPlan).build());

        paymentConfirmation.getPaymentDeclaration().setSsn(debtor.getSsn());
        paymentConfirmation.getCreditCard().setCreditCardType(CreditCardType.valueOf(paymentConfirmation.getCreditCard().getIssuingNetwork()));
        paymentConfirmationModel.addObject("paymentConfirmation", paymentConfirmation);

        if (bindingResult.hasErrors()) {
            logger.info("Returning paymentDeclaration.html page");
            return paymentConfirmationModel;
        }

        //   jmsProducer.send(".paymentsupdate", paymentConfirmation, "Alivio");

        try {
            String json = objectMapper.writeValueAsString(paymentConfirmation);
            logger.info("Created JSON: {}", json);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.setContentLanguage(LocaleContextHolder.getLocale());
            logger.info("Sending Locale Context as: {}", LocaleContextHolder.getLocale());

            HttpEntity<String> entity = new HttpEntity<>(json, headers);
            logger.info("Sending JSON format data to: {}", resourceUri);
            logger.info("Sending ENTITY :{}", entity);

            ResponseEntity<String> response = restTemplate.postForEntity(
                    resourceUri, entity, String.class);
            logger.info("Sending JSON as HTTPEntity: {}", json);

            if (HttpStatus.OK.equals(response.getStatusCode())) {
                logger.info("HttpStatus: {}", response.getStatusCode());
                logger.info("Redirect to: paymentConfirmationResult");
                paymentConfirmationModel.setViewName("paymentConfirmationResult");
                return paymentConfirmationModel;
            } else {
                logger.info("HttpStatus is NOT OK. Returning verifyClientForm.html page");
                paymentConfirmationModel.addObject("errorMsg", "Your data are incorrect. Enter the correct data!");
                return new ModelAndView("verifyClientForm");
            }
        } catch (JsonProcessingException e) {
            logger.info("Json processing error: {}. Returning paymentForm.html page", e.getMessage());
            paymentConfirmationModel.addObject("errorMsg", "Your data are incorrect. Enter the correct data!");
            return paymentConfirmationModel;
        } catch (HttpClientErrorException e) {
            logger.info("HttpClient error: {}. Returning paymentForm.html page", e.getStatusCode());
            paymentConfirmationModel.addObject("errorMsg", "Your data are incorrect. Enter the correct data!");
            return paymentConfirmationModel;
        } catch (HttpServerErrorException | ResourceAccessException e) {
            logger.info("HttpServer error: {}. Returning paymentForm.html page", e.getMessage());
            paymentConfirmationModel.addObject("errorMsg", "Connection error. Try again!");
            return paymentConfirmationModel;
        }
    }
}