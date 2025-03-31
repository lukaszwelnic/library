package org.library.config;

import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
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
    public CsvMapper csvMapper() {
        return new CsvMapper();
    }
}
