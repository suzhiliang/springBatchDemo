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

@Configuration
@EnableBatchProcessing
public class JobDemo {

	@Autowired
	private JobBuilderFactory jobBuilderFactory;

	@Autowired
	private StepBuilderFactory stepBuilderFactory;

	@Bean
	public Job jobDemoJOB() {
		return jobBuilderFactory.get("jobDemoJOB").
				start(set1())
				.next(set2()). /* *****next 顺序执行下一个Step**/
				next(step3()). 
				build();
				//.on("COMPLETED").to(set2())/*on--条件，to--到某个Step，from ---从某个Step**/
				//.from(set2()).on("COMPLETED") 
				//.to(step3()).from(step3()).end().build();

	}
	
	@Bean
	public Step step3() {
		return stepBuilderFactory.get("step3").tasklet(new Tasklet() {
			
			@Override
			public RepeatStatus execute(StepContribution arg0, ChunkContext arg1) throws Exception {
				System.out.println("step3");
				return RepeatStatus.FINISHED;
			}
		}).build();
	}
	
	@Bean
	public Step set2() {
		return stepBuilderFactory.get("step2").tasklet(new Tasklet() {
			
			@Override
			public RepeatStatus execute(StepContribution arg0, ChunkContext arg1) throws Exception {
				System.out.println("step2");
				return RepeatStatus.FINISHED;
			}
		}).build();
	}

	@Bean
	public Step set1() {
		return stepBuilderFactory.get("step1").tasklet(new Tasklet() {
			
			@Override
			public RepeatStatus execute(StepContribution arg0, ChunkContext arg1) throws Exception {
				System.out.println("step1");
				return RepeatStatus.FINISHED;
			}
		}).build();
	}

}
