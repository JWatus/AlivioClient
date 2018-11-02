package eu.sii.pl.alivio.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import eu.sii.pl.alivio.config.GlobalProperties;
import eu.sii.pl.alivio.jms.producer.JmsProducer;
import eu.sii.pl.alivio.model.Debtor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
public class VerifyClientController {
    private static final Logger logger = LoggerFactory
            .getLogger(VerifyClientController.class);
    private GlobalProperties globalProperties;
    private RestTemplate restTemplate;
    private JmsProducer jmsProducer;

    public VerifyClientController(GlobalProperties globalProperties, RestTemplateBuilder restTemplateBuilder, JmsProducer jmsProducer) {
        this.globalProperties = globalProperties;
        this.restTemplate = restTemplateBuilder.build();
        this.jmsProducer = jmsProducer;
    }

    @RequestMapping(value = {"/", "/form"}, method = RequestMethod.GET)
    public ModelAndView saveClientPage() {
        logger.info("Returning verifyClientForm.html page");
        ModelAndView verifyClientForm = new ModelAndView("verifyClientForm");
        verifyClientForm.addObject("debtor", new Debtor());
        return verifyClientForm;
    }

    @RequestMapping(value = "/form", method = RequestMethod.POST)
    public String saveClientAction(HttpSession session,
                                   @Valid Debtor debtor,
                                   BindingResult bindingResult, Model model) throws JsonProcessingException {

        final String resourceUri = globalProperties.getApiUrl().concat(globalProperties.getApiVerifyDebtorEndpoint());

        if (bindingResult.hasErrors()) {
            logger.info("Returning verifyClientForm.html page");
            return "verifyClientForm";
        }
        model.addAttribute("debtor", debtor);
        session.setAttribute("ssn", debtor.getSsn());

       // jmsProducer.send(".login", debtor, "Alivio");

        try {
            ResponseEntity<String> response = restTemplate.postForEntity(
                    resourceUri, debtor, String.class);
            logger.info("Sending Debtor object from to: {}", resourceUri);

            if (HttpStatus.OK.equals(response.getStatusCode())) {
                logger.info("HttpStatus: {}", response.getStatusCode());
                logger.info("Redirect to: redirect:/paymentdeclaration");
                return "redirect:/paymentdeclaration";

            } else {
                logger.info("HttpStatus is NOT OK. Returning verifyClientForm.html page");
                model.addAttribute("errorMsg", "User not found! Try again.");
                return "verifyClientForm";
            }
        } catch (HttpClientErrorException e) {
            logger.info("HttpClient error: {}. Returning verifyClientForm.html page", e.getStatusCode());
            model.addAttribute("errorMsg", "User not found! Try again.");
            return "verifyClientForm";
        } catch (HttpServerErrorException | ResourceAccessException e) {
            logger.info("HttpServer error: {}. Returning verifyClientForm.html page", e.getMessage());
            model.addAttribute("errorMsg", "Connection error. Try again!");
            return "verifyClientForm";
        }
    }
}