package com.upgrad.notificationservice;

import freemarker.cache.ClassTemplateLoader;
import freemarker.cache.TemplateLoader;
import freemarker.template.Configuration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

@SpringBootApplication
public class NotificationServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(NotificationServiceApplication.class, args);
    }

    @Bean
    FreeMarkerConfigurer freeMarkerConfigurer(){
        Configuration configuration = new Configuration(Configuration.VERSION_2_3_29);
        TemplateLoader loader = new ClassTemplateLoader(this.getClass(), "/templates");
        configuration.setTemplateLoader(loader);
        FreeMarkerConfigurer configurer = new FreeMarkerConfigurer();
        configurer.setConfiguration(configuration);
        return configurer;
    }
}
