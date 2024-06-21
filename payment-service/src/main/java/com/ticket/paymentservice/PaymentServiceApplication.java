package com.ticket.paymentservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@EnableDiscoveryClient
@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
public class PaymentServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(PaymentServiceApplication.class, args);
    }

}

@Configuration
class RestTemplateConfig {

    // Create a bean for restTemplate to call services
    @Bean
    @LoadBalanced // Load balance between service instances running at different ports.
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}