package com.example.demo.input;

import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;
import org.springframework.stereotype.Component;

/**
  *在执行的开始和结束时接收事件通知
  *
  * <p>
  *要从Java批处理返回适当的退出代码（数值）到JobCenter
  *当作业结束时，根据执行状态（作业执行）判断退出状态，并输出日志。
  *
  * <p>
  *使用公共组件记录器将故障调查所需的信息作为应用程序日志输出。
  */
@Component
public class JobListener2 extends JobExecutionListenerSupport {

	public void beforeJob(JobExecution jobExecution) {
		System.out.println("Batch	开始");
	}

	public void afterJob(JobExecution jobExecution) {
		System.out.println("Batch	结束");
	}
}
