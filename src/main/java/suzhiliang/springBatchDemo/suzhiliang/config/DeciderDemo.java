package suzhiliang.springBatchDemo.suzhiliang.config;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.job.flow.JobExecutionDecider;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * JobExecutionDecider  决策器使用 ：
 * 用来判断复杂条件。
 * 实现决策器类。
 * @author 苏志亮
 *
 */
@Configuration
@EnableBatchProcessing
public class DeciderDemo {
	@Autowired
    private JobBuilderFactory jobBuilderFactory; 
	
	@Autowired
	private StepBuilderFactory stepBuilderFactory;
	
	@Bean
	public Step deciderStep1(){
		return stepBuilderFactory.get("deciderStep1")
				.tasklet(new Tasklet() {
					
					@Override
					public RepeatStatus execute(StepContribution arg0, ChunkContext arg1) throws Exception {
						System.out.println("deciderStep1");
						return RepeatStatus.FINISHED;
					}
				}).build();
	}
	
	@Bean
	public Step deciderStep2(){
		return stepBuilderFactory.get("deciderStep2")
				.tasklet(new Tasklet() {
					
					@Override
					public RepeatStatus execute(StepContribution arg0, ChunkContext arg1) throws Exception {
						System.out.println("even");//偶数
						return RepeatStatus.FINISHED;
					}
				}).build();
	}
	
	@Bean
	public Step deciderStep3(){
		return stepBuilderFactory.get("deciderStep3")
				.tasklet(new Tasklet() {
					
					@Override
					public RepeatStatus execute(StepContribution arg0, ChunkContext arg1) throws Exception {
						System.out.println("odd");//基数
						return RepeatStatus.FINISHED;
					}
				}).build();
	}
	

	/**
	 * 创建JobExecutionDecider
	 * @return
	 */
	@Bean
	public JobExecutionDecider jobExecutionDeciderDemo() {
		return new MyDecider();
	}
	
	/**
	 * 创建job
	 * @return
	 */
	@Bean
	public Job deciderJob() {
		return jobBuilderFactory.get("deciderJob")
				.start(deciderStep1())
				.next(jobExecutionDeciderDemo())
                .from(jobExecutionDeciderDemo()).on("odd").to(deciderStep3()) //当满足决策器基数时执行deciderStep3
                .from(jobExecutionDeciderDemo()).on("even").to(deciderStep2())//当满足决策器基数时执行deciderStep2
                .from(deciderStep3()).on("*").to(jobExecutionDeciderDemo())// * 表示 无论任何条件 deciderStep3执行完还会执行决策器
                .end().build();
    }
				
}
