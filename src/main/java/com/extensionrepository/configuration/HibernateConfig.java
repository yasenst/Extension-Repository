package com.extensionrepository.configuration;

import com.extensionrepository.entity.Extension;
import com.extensionrepository.entity.Role;
import com.extensionrepository.entity.Tag;
import com.extensionrepository.entity.User;
import org.hibernate.SessionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;

@Configuration
public class HibernateConfig {
    @Bean
    public SessionFactory createFactory(){
        return new org.hibernate.cfg.Configuration()
                .configure("hibernate.cfg.xml")
                .addAnnotatedClass(User.class)
                .addAnnotatedClass(Role.class)
                .addAnnotatedClass(Extension.class)
                .addAnnotatedClass(Tag.class)
                .buildSessionFactory();
    }

}