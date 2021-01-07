package com.example.demo;

import java.util.Properties;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.configuration.JobRegistry;
import org.springframework.batch.core.converter.DefaultJobParametersConverter;
import org.springframework.batch.core.converter.JobParametersConverter;
import org.springframework.batch.core.explore.JobExplorer;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.support.ExitCodeMapper;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.autoconfigure.batch.JobLauncherApplicationRunner;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

/**
  * OXPPApplicationRunner
  *
  * <p>
  * JobLauncherApplicationRunner，一个控制作业选择和执行的类，由SpringBoot自动注册为bean。
  *默认情况下，将执行Bean中定义的所有作业。
  *继承JobLauncherApplicationRunner并重写run方法以防止故障
  *
  * <p>
  *通知应用程序类退出代码（数值）
  *使用ExitCodeMapper将执行作业后获得的JobExecution中设置的StatusCode转换为数值，并将其保留在成员中。
  */
@Component
public class OApplicationRunner extends JobLauncherApplicationRunner {

    /** JobRegistry */
    @Autowired
    private final JobRegistry jobRegistry;
    /** JobLauncher */
    private final JobLauncher jobLauncher;
    /** ExitCodeMapper */
    private final ExitCodeMapper exitCodeMapper;
    /** JobParametersConverter */
    private final JobParametersConverter jobParametersConverter = new DefaultJobParametersConverter();

    /** ExitCode(数値) */
    private int exitCode;

    public int getExitCode() {
        return exitCode;
    }

    public OApplicationRunner(JobLauncher jobLauncher, JobExplorer jobExplorer, JobRepository jobRepository,
            JobRegistry jobRegistry, ExitCodeMapper exitCodeMapper) {
        super(jobLauncher, jobExplorer, jobRepository);
        this.jobRegistry = jobRegistry;
        this.jobLauncher = jobLauncher;
        this.exitCodeMapper = exitCodeMapper;
    }

    
    @Override
    public void run(ApplicationArguments args) throws Exception {
        
        String[] jobArguments = args.getNonOptionArgs().toArray(new String[0]);
        Properties properties = StringUtils.splitArrayElementsIntoProperties(jobArguments, "=");
        JobParameters jobParameters = jobParametersConverter.getJobParameters(properties);
        Job job = jobRegistry.getJob(jobArguments[0]);
        JobExecution execution = jobLauncher.run(job, jobParameters);
        exitCode = exitCodeMapper.intValue(execution.getExitStatus().getExitCode());

    }


}