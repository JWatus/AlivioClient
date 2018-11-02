package eu.sii.pl.alivio.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import eu.sii.pl.alivio.config.GlobalProperties;
import eu.sii.pl.alivio.jms.producer.JmsProducer;
import eu.sii.pl.alivio.model.Debtor;
import eu.sii.pl.alivio.model.PaymentDeclaration;
import eu.sii.pl.alivio.model.PaymentPlan;
import eu.sii.pl.alivio.views.builders.DebtorViewBuilder;
import eu.sii.pl.alivio.views.builders.PaymentPlanViewBuilder;
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
public class PaymentDeclarationController {
    private static final Logger logger = LoggerFactory
            .getLogger(PaymentDeclarationController.class);
    private GlobalProperties globalProperties;
    private ObjectMapper objectMapper;
    private RestTemplate restTemplate;
    private JmsProducer jmsProducer;

    public PaymentDeclarationController(GlobalProperties globalProperties, ObjectMapper objectMapper, RestTemplateBuilder restTemplateBuilder, JmsProducer jmsProducer) {
        this.globalProperties = globalProperties;
        this.objectMapper = objectMapper;
        this.restTemplate = restTemplateBuilder.build();
        this.jmsProducer = jmsProducer;
    }

    @RequestMapping(value = "/paymentdeclaration", method = RequestMethod.GET)
    public ModelAndView showPaymetDeclarationForm(HttpSession session) throws JsonProcessingException {
        String ssnAttribute = (String) session.getAttribute("ssn");

        final String resourceUri = globalProperties.getApiUrl()
                .concat(globalProperties.getApiBalanceEndpoint() + ssnAttribute);

      //  jmsProducer.send(".balance", ssnAttribute, "Alivio");

        try {
            Debtor debtor = restTemplate.getForObject(resourceUri, Debtor.class);
            logger.info("Received Debtor object from: {}", resourceUri);
            logger.info("Received Debtor object: {}", debtor.toString());
            session.setAttribute("debtor", debtor);

            ModelAndView balance = new ModelAndView("paymentDeclaration");
            balance.addObject("debtorview", new DebtorViewBuilder(debtor).build());
            balance.addObject("paymentDeclaration", new PaymentDeclaration());
            return balance;
        } catch (HttpServerErrorException | HttpClientErrorException e) {
            ModelAndView clientForm = new ModelAndView("verifyClientForm");
            clientForm.addObject("errorMsg", "There was problem with debts information availability. Try later.");
            logger.info("HttpServer error: {}. Returning verifyClientForm.html page", e.getMessage());
            return clientForm;
        }
    }

    @RequestMapping(value = "/paymentdeclaration", method = RequestMethod.POST)
    public ModelAndView createPaymentDeclaration(HttpSession session,
                                                 @Valid PaymentDeclaration paymentDeclaration,
                                                 BindingResult bindingResult) throws JsonProcessingException {

        final String resourceUri = globalProperties.getApiUrl().concat(globalProperties.getApiPaymentPlanEndpoint());
        Debtor debtor = (Debtor) session.getAttribute("debtor");
        paymentDeclaration.setSsn(debtor.getSsn());

        ModelAndView paymentDeclarationModel = new ModelAndView("paymentDeclaration");
        paymentDeclarationModel.addObject("debtorview", new DebtorViewBuilder(debtor).build());
        paymentDeclarationModel.addObject("paymentDeclaration", paymentDeclaration);
        session.setAttribute("paymentDeclaration", paymentDeclaration);

        if (bindingResult.hasErrors()) {
            logger.info("Returning paymentDeclaration.html page");
            return paymentDeclarationModel;
        }

       // jmsProducer.send(".paymentplan", paymentDeclaration, "Alivio");

        try {
            String json = objectMapper.writeValueAsString(paymentDeclaration);
            logger.info("Created JSON: {}", json);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.setContentLanguage(LocaleContextHolder.getLocale());
            logger.info("Sending Locale Context as: {}", LocaleContextHolder.getLocale());

            HttpEntity<String> entity = new HttpEntity<>(json, headers);
            logger.info("Sending JSON format data to: {}", resourceUri);
            logger.info("Sending ENTITY :{}", entity);

            ResponseEntity<PaymentPlan> response = restTemplate.postForEntity(resourceUri, entity, PaymentPlan.class);
            logger.info("Sending JSON as HTTPEntity: {}", json);
            logger.info("Received response body from Api: {}", response.getBody());

            PaymentPlan paymentPlan = response.getBody();
            session.setAttribute("paymentPlan", paymentPlan);

            if (HttpStatus.OK.equals(response.getStatusCode())) {
                logger.info("HttpStatus: {}", response.getStatusCode());
                paymentDeclarationModel.addObject("paymentPlanView", new PaymentPlanViewBuilder(debtor, paymentPlan).build());
                logger.info("Redirect to: paymentPlan");
                paymentDeclarationModel.setViewName("paymentPlan");
                return paymentDeclarationModel;
            } else {
                logger.info("HttpStatus is NOT OK. Returning verifyClientForm.html page");
                paymentDeclarationModel.addObject("errorMsg", "Your data is incorrect. Enter the correct data!");
                return new ModelAndView("verifyClientForm");
            }
        } catch (JsonProcessingException e) {
            logger.info("HttpClient error: {}. Returning paymentForm.html page", e.getMessage());
            paymentDeclarationModel.addObject("errorMsg", "Your data is incorrect. Enter the correct data!");
            return paymentDeclarationModel;
        } catch (HttpClientErrorException e) {
            logger.info("HttpClient error: {}. Returning paymentForm.html page", e.getStatusCode());
            paymentDeclarationModel.addObject("errorMsg", "Your data is incorrect. Enter the correct data!");
            return paymentDeclarationModel;
        } catch (HttpServerErrorException | ResourceAccessException e) {
            logger.info("HttpServer error: {}. Returning paymentForm.html page", e.getMessage());
            paymentDeclarationModel.addObject("errorMsg", "Connection error. Try again!");
            return paymentDeclarationModel;
        }
    }
}