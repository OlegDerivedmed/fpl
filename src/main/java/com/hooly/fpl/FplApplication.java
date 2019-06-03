package com.hooly.fpl;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@PropertySource(value = {"classpath:rest-application.properties"})
@EnableSwagger2
public class FplApplication {

    public static void main(String[] args) {
        SpringApplication.run(FplApplication.class, args);
    }

}
