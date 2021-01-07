package com.example.demo.output;

import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;
import org.springframework.stereotype.Component;

//监听
@Component
public class JobListener extends JobExecutionListenerSupport {

	public void beforeJob(JobExecution jobExecution) {
		System.out.println("Batch	开始");
	}

	public void afterJob(JobExecution jobExecution) {
		System.out.println("Batch	结束");
	}
}
