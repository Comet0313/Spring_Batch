package com.example.demo;

import java.util.HashMap;
import java.util.Map;

import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.launch.support.ExitCodeMapper;
import org.springframework.stereotype.Component;

/**
  * <p>
  *定义在作业结束时返回的ExitStatu和ExitCode（数值）之间的关联的类。
  *
  * <p>
  *除了正常终止（0），警告终止（1）和异常终止（9）外，正常终止分支可以使用100 --119，警告终止分支可以使用120 --139。
  *如果返回异常终止（9），则将停止执行整个作业网络。
  */
@Component
public class OExitCodeMapper implements ExitCodeMapper {

	/** 正常终止ExitStatus */
	public static final ExitStatus EXITSTATUS_SUCCESS_0 = ExitStatus.COMPLETED;
	/** 正常终止ExitCode */
	static int EXITCODE_SUCCESS_0 = 0;
	/** 警告终止ExitStatus */
	public static final ExitStatus EXITSTATUS_WARNING_1 = ExitStatus.FAILED;
	/** 警告终止ExitCode */
	static int EXITCODE_WARNING_1 = 1;
	/** 异常终止ExitStatus */
	public static final ExitStatus EXITSTATUS_FAILURE_9 = ExitStatus.UNKNOWN;
	/** 异常终止ExitCode */
	static int EXITCODE_FAILURE_9 = 9;
	/** 例示用MyStatusExitStatus */
	public static final ExitStatus EXITSTATUS_MYSTATUS_100 = new ExitStatus("MYSTATUS");
	/** 例示用MyStatusExitCode */
	static int EXITCODE_MYSTATUS_100 = 100;

	private Map<String, Integer> mapping;


	public OExitCodeMapper() {
		mapping = new HashMap<>();
		mapping.put(EXITSTATUS_SUCCESS_0.getExitCode(), EXITCODE_SUCCESS_0);
		mapping.put(EXITSTATUS_WARNING_1.getExitCode(), EXITCODE_WARNING_1);
		mapping.put(EXITSTATUS_FAILURE_9.getExitCode(), EXITCODE_FAILURE_9);
		mapping.put(ExitCodeMapper.JOB_NOT_PROVIDED, EXITCODE_FAILURE_9);
		mapping.put(ExitCodeMapper.NO_SUCH_JOB, EXITCODE_FAILURE_9);

		mapping.put(EXITSTATUS_MYSTATUS_100.getExitCode(), EXITCODE_MYSTATUS_100);
	}

	@Override
	public int intValue(String exitCode) {
		return mapping.get(exitCode).intValue();
	}
}