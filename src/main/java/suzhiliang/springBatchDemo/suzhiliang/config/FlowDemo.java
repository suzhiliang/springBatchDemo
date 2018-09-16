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

/**
 * 
 * @author 苏志亮
 *
 * flow 用法
 * 一个flow有多个step组成
 * 可以被多个job复用
 * 使用FlowBuilder来创建 
 */
@Configuration
@EnableBatchProcessing
public class FlowDemo {
	
	@Autowired
	private JobBuilderFactory jobBuilderFactory;
	
	@Autowired
	private StepBuilderFactory stepBuilderFactory;
	
	@Bean
	public Step flowStep3Demo() {
		return stepBuilderFactory.get("flowStep3Demo").tasklet(new Tasklet() {
			
			@Override
			public RepeatStatus execute(StepContribution arg0, ChunkContext arg1) throws Exception {
				System.out.println("flowStep3Demo");
				return RepeatStatus.FINISHED;
			}
		}).build();
	}
	
	@Bean
	public Step flowStep2Demo() {
		return stepBuilderFactory.get("flowStep2Demo").tasklet(new Tasklet() {
			
			@Override
			public RepeatStatus execute(StepContribution arg0, ChunkContext arg1) throws Exception {
				System.out.println("flowStep2Demo");
				return RepeatStatus.FINISHED;
			}
		}).build();
	}
	
	@Bean
	public Step flowStep1Demo() {
		return stepBuilderFactory.get("flowStep1Demo").tasklet(new Tasklet() {
			
			@Override
			public RepeatStatus execute(StepContribution arg0, ChunkContext arg1) throws Exception {
				System.out.println("flowStep1Demo");
				return RepeatStatus.FINISHED;
			}
		}).build();
	}
	
	/**
	 * 创建flow
	 * 包含Step1 和 Step2
	 * @return
	 */
	@Bean
	public Flow flowDemoFlow(){
		return new FlowBuilder<Flow>("flowDemoFlow")
				.start(flowStep1Demo())
				.next(flowStep2Demo()).build();
	}
	
	/**
	 * 创建job作业
	 * @return
	 */
	@Bean
	public Job flowDemoJOB() {
		return jobBuilderFactory.get("flowDemoJOB").
				start(flowDemoFlow())
				.next(flowStep3Demo()).end().build();

	}
}
