package com.ticket.payment;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.mongo.MongoDataAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.web.client.RestTemplate;

@EnableMongoRepositories
@EnableDiscoveryClient
@SpringBootApplication(exclude = {
        DataSourceAutoConfiguration.class,
        MongoAutoConfiguration.class,
        MongoDataAutoConfiguration.class,
})
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


@Configuration
class MongoConfig extends AbstractMongoClientConfiguration {

    @Autowired
    private Environment env;

    @Override
    protected String getDatabaseName() {
        return "ticket";
    }

    @Bean
    @Override
    public MongoClient mongoClient() {
        return MongoClients.create("mongodb+srv://nsonbao0601:alNlxNVoma7mgxKo@cluster0.hknjigi.mongodb.net/?retryWrites=true&w=majority&appName=Cluster0");
    }

}