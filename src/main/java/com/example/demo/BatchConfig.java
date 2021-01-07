package com.example.demo;

import com.example.demo.input.JobConfig2;
import com.example.demo.output.JobConfig;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.support.ApplicationContextFactory;
import org.springframework.batch.core.configuration.support.GenericApplicationContextFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableBatchProcessing(modular = true)
public class BatchConfig {

    @Bean
    public ApplicationContextFactory output() {
        return new GenericApplicationContextFactory(JobConfig.class);
    }

    @Bean
    public ApplicationContextFactory input() {
        return new GenericApplicationContextFactory(JobConfig2.class);
    }

}
