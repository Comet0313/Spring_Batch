package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.batch.BatchAutoConfiguration;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;

/**
  *不要使用SpringBootApplication批注
  *（为了防止@EnableAutoConfiguration自动初始化类路径中未使用的包）
  *而是添加以下注释。 ComponentScan扫描此类下的软件包
  * <p>
  *我们也考虑过使用CommandRineJobRunner而不是SpringBoot，但是由于以下原因，我们不会采用它。
  * JobRepository使用的数据源与主数据源相同
  *异常终止时难以自定义退出代码（数值）
 */
@ComponentScan
public class Application {

	/**
	*设置来源组
	*
	* <p>
	*定义一组类作为设置源传递给Spring Boot
	*通过指定所需的最小配置源组而不是EnableAutoConfiguration来防止意外地类类中包含的库被初始化。
	*/
	private static final Class<?>[] configSources = { Application.class, BatchAutoConfiguration.class };

	public static void main(String[] args) {
		int exitCode = OExitCodeMapper.EXITCODE_SUCCESS_0;
		try (ConfigurableApplicationContext ctx = SpringApplication.run(configSources, args)) {
			//从OApplicationRunner提取退出代码并进行设置
			exitCode = ctx.getBean(OApplicationRunner.class).getExitCode();
		} catch (Exception e) {
			//如果批处理启动失败，则将堆栈跟踪输出到标准错误输出
			//将exitCode设置为EXITCODE_FAILURE_9
			e.printStackTrace();
			exitCode = OExitCodeMapper.EXITCODE_FAILURE_9;
		}
		System.exit(exitCode);
	}
}
