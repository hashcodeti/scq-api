package com.bluebudy.SCQ;

import java.io.FileNotFoundException;

import org.dom4j.DocumentException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.bluebudy.SCQ.services.ScqMailSupportService;

@SpringBootApplication
public class ScqMobileApplication {

    public static void main(String[] args) throws FileNotFoundException, DocumentException {
        SpringApplication.run(ScqMobileApplication.class, args);

    }
    
    @Bean
    public ScqMailSupportService javaMailSender(){
        return new ScqMailSupportService();
    }

}

@Configuration
@EnableScheduling
@ConditionalOnProperty(name = "scheduling.enabled", matchIfMissing = true)
class SchedulingConfiguration {

}
