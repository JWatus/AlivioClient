package eu.sii.pl.alivio.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties
public class GlobalProperties {
    private String apiUrl;
    private String apiBalanceEndpoint;
    private String apiVerifyDebtorEndpoint;
    private String apiPaymentEndpoint;
    private String apiPaymentPlanEndpoint;
    private  String apiPaymentMethodsCreditcardEndpoint;

    public String getApiUrl() {
        return apiUrl;
    }

    public void setApiUrl(String apiUrl) {
        this.apiUrl = apiUrl;
    }

    public String getApiBalanceEndpoint() {
        return apiBalanceEndpoint;
    }

    public void setApiBalanceEndpoint(String apiBalanceEndpoint) {
        this.apiBalanceEndpoint = apiBalanceEndpoint;
    }

    public String getApiVerifyDebtorEndpoint() {
        return apiVerifyDebtorEndpoint;
    }

    public void setApiVerifyDebtorEndpoint(String apiVerifyDebtorEndpoint) {
        this.apiVerifyDebtorEndpoint = apiVerifyDebtorEndpoint;
    }

    public String getApiPaymentEndpoint() {
        return apiPaymentEndpoint;
    }

    public void setApiPaymentEndpoint(String apiPaymentEndpoint) {
        this.apiPaymentEndpoint = apiPaymentEndpoint;
    }

    public String getApiPaymentPlanEndpoint() {
        return apiPaymentPlanEndpoint;
    }

    public void setApiPaymentPlanEndpoint(String apiPaymentPlanEndpoint) {
        this.apiPaymentPlanEndpoint = apiPaymentPlanEndpoint;
    }

    public String getApiPaymentMethodsCreditcardEndpoint() {
        return apiPaymentMethodsCreditcardEndpoint;
    }

    public void setApiPaymentMethodsCreditcardEndpoint(String apiPaymentMethodsCreditcardEndpoint) {
        this.apiPaymentMethodsCreditcardEndpoint = apiPaymentMethodsCreditcardEndpoint;
    }
}
