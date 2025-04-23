package org.library.config;

import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.support.ResourceBundleMessageSource;

@Configuration
@EnableAspectJAutoProxy
@ComponentScan(basePackages = "org.library")
public class AppConfig {
    @Bean
    public CsvSchema csvSchema() {
        return CsvSchema.builder()
                .addColumn("id")
                .addColumn("title")
                .addColumn("author")
                .addColumn("description")
                .setUseHeader(true)
                .setColumnSeparator(';')
                .build();
    }

    @Bean
    public MessageSource messageSource() {
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        messageSource.setBasename("messages");
        messageSource.setDefaultEncoding("UTF-8");
        return messageSource;
    }

    @Bean
    public CsvMapper csvMapper() {
        return new CsvMapper();
    }
}
