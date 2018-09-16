package suzhiliang.springBatchDemo.suzhiliang.config;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.job.builder.FlowBuilder;
import org.springframework.batch.core.job.flow.Flow;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.retry.backoff.SleepingBackOffPolicy;

/**
 * split：实现任务的多个step或flow并发执行
 * @author 苏志亮
 *
 */
@Configuration
@EnableBatchProcessing
public class SplitDemo {
	
	@Autowired
    private JobBuilderFactory jobBuilderFactory; 
	
	@Autowired
	private StepBuilderFactory stepBuilderFactory;
	
	@Bean
	public Step splitStep1(){
		return stepBuilderFactory.get("splitStep1")
				.tasklet(new Tasklet() {
					
					@Override
					public RepeatStatus execute(StepContribution arg0, ChunkContext arg1) throws Exception {
						System.out.println("splitStep1");
						return RepeatStatus.FINISHED;
					}
				}).build();
	}
	
	@Bean
	public Step splitStep2(){
		return stepBuilderFactory.get("splitStep2")
				.tasklet(new Tasklet() {
					
					@Override
					public RepeatStatus execute(StepContribution arg0, ChunkContext arg1) throws Exception {
						System.out.println("splitStep2");
						return RepeatStatus.FINISHED;
					}
				}).build();
	}
	
	@Bean
	public Step splitStep3(){
		return stepBuilderFactory.get("splitStep3")
				.tasklet(new Tasklet() {
					
					@Override
					public RepeatStatus execute(StepContribution arg0, ChunkContext arg1) throws Exception {
						System.out.println("splitStep3");
						return RepeatStatus.FINISHED;
					}
				}).build();
	}
	
	@Bean
	public Step splitStep4(){
		return stepBuilderFactory.get("splitStep4")
				.tasklet(new Tasklet() {
					
					@Override
					public RepeatStatus execute(StepContribution arg0, ChunkContext arg1) throws Exception {
						System.out.println("splitStep4");
						return RepeatStatus.FINISHED;
					}
				}).build();
	}
	
	@Bean
	public Flow splitFlow1(){
		return new FlowBuilder<Flow>("splitFlow1")
				.start(splitStep3())
				.next(splitStep1()).build();
	}
	
	@Bean
	public Flow splitFlow2(){
		return new FlowBuilder<Flow>("splitFlow2")
				.start(splitStep2())
				.next(splitStep4()).build();
	}
	
	@Bean
	public Job splitJob(){
		return jobBuilderFactory.get("splitJob")
				.start(splitFlow1())
				.split(new SimpleAsyncTaskExecutor())//splitFlow1 和 splitFlow2 并发执行
				.add(splitFlow2(),splitFlow1()) //可以同时放多个step 或 flow
				.end().build();
		
		/* SimpleAsyncTaskExecutor
		 * 这个实现不重用任何线程，或者说它每次调用都启动一个新线程。
		 * 但是，它还是支持对并发总数设限，当超过线程并发总数限制时，
		 * 阻塞新的调用，直到有位置被释放。*/
	}
	
}
