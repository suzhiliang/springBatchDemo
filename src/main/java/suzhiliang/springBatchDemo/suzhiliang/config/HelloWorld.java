package suzhiliang.springBatchDemo.suzhiliang.config;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 
 * hellowrld批处理类
 * @author 苏志亮
 *
 */
@Configuration
@EnableBatchProcessing
public class HelloWorld {
	 //注入批处理JOB工厂
	 //用来创建任务对象
	 @Autowired
	 private JobBuilderFactory jobBuilderFactory;
	 
	 //注入任务步骤Step对象
	 //任务的执行由Step来决定
	 @Autowired
	 private StepBuilderFactory stepBuilderFactory;	
	 
	 //创建任务对象
	 @Bean
	 public Job helloWorldJob(){
		return jobBuilderFactory.get("helloworldJob").start(step1()).build();
		 
	 }
	
	//创建Step步骤对象
	@Bean
	public Step step1() {
		
		return stepBuilderFactory.get("step1").tasklet(new Tasklet() {
			//具体要执行的任务方法--execute
			@Override
			public RepeatStatus execute(StepContribution arg0, ChunkContext arg1) throws Exception {
				System.out.println("---------->HelloWorld");
				return RepeatStatus.FINISHED;
			}
		}).build();
	}
	 

}
