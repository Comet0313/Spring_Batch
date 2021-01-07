package com.example.demo.output;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.PlatformTransactionManager;

public class JobConfig {

    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Autowired
    private JobListener Joblistener;

    @Autowired
    protected PlatformTransactionManager postgresTransactionManager;

    @Autowired
    @Qualifier("Step1Tasklet")
    private Tasklet Step1Tasklet;


    @Bean
    public Job job(Step step1) {
        return jobBuilderFactory.get("job").listener(Joblistener).start(step1).build();
    }


    @Bean
    public Step step1() {
        return stepBuilderFactory.get("step1").transactionManager(postgresTransactionManager)
                .tasklet(Step1Tasklet).build();
    }

}