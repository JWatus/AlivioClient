package eu.sii.pl.alivio.config;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;

import javax.jms.ConnectionFactory;

@Configuration
@EnableJms
public class ActiveMqConnectionFactoryConfig {

    @Value("${activemq.broker.url}")
    String brokerUrl;

    @Value("${activemq.borker.username}")
    String userName;

    @Value("${activemq.borker.password}")
    String password;

    @Bean
    public ConnectionFactory connectionFactory(){
        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory();
        connectionFactory.setBrokerURL(brokerUrl);
//        connectionFactory.setUserName(userName);
//        connectionFactory.setPassword(password);
        return connectionFactory;
    }
}
