package com.example.demo.input;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;

/**
  *定义和每个步骤的Bean的作业
  *
  * <p>
  *此类不需要@Configuration批注
  *通过在BatchConfig.java中指定此类来加载每个bean。
  */
public class JobConfig2 {

	@Autowired
	public JobBuilderFactory jobBuilderFactory;

	@Autowired
	public StepBuilderFactory stepBuilderFactory;

	@Autowired
	private JobListener2 Joblistener;

	@Autowired
	@Qualifier("Step1Tasklet2")
	private Tasklet Step1Tasklet2;

	@Bean
	public Job Job(Step Step1) {
		return jobBuilderFactory.get("Job").listener(Joblistener).start(Step1).build();
	}

	@Bean
	public Step Step1() {
		return stepBuilderFactory.get("Step1").tasklet(Step1Tasklet2).build();

	}
}
