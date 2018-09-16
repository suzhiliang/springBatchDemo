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
 * 子job1 用来给父job调用
 * @author 苏志亮
 *
 */
@Configuration
@EnableBatchProcessing
public class ChildJobOne {
	
	@Autowired
    private JobBuilderFactory jobBuilderFactory; 
	
	@Autowired
	private StepBuilderFactory stepBuilderFactory;
	
	@Bean
	public Step childJobOneStep1(){
		return stepBuilderFactory.get("childJobOneStep1")
				.tasklet(new Tasklet() {
					
					@Override
					public RepeatStatus execute(StepContribution arg0, ChunkContext arg1) throws Exception {
						System.out.println("childJobOneStep1");
						return RepeatStatus.FINISHED;
					}
				}).build();
	}
	
	@Bean
	public Job  childJobOne1(){
		return jobBuilderFactory.get("childJobOne1").start(childJobOneStep1()).build();
	}
}
