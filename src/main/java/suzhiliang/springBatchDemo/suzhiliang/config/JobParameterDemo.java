package suzhiliang.springBatchDemo.suzhiliang.config;

import java.util.Map;

import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
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
 * 给job传递参数。	
 * job运行时可以用key=vlaue 的形式传递参数。
 * @author 苏志亮
 *
 */
@Configuration
@EnableBatchProcessing
public class JobParameterDemo implements StepExecutionListener {
	
	private Map<String, JobParameter> param;

	@Autowired
	private JobBuilderFactory jobBuilderFactory;

	@Autowired
	private StepBuilderFactory stepBuilderFactory;
	
	/**
	 * 创建job
	 * @return
	 */
	@Bean
	public Job JobParametersDemo() {
		return jobBuilderFactory.get("jobParametersDemo")
				.start(StepParameterDemo())
				.build();
	}
    
	//job中的参数在step中使用，所以只需要给step传递参数就行。
	//使用stepExecutionListener 监听器传递参数
	@Bean
	public Step StepParameterDemo() {
		return stepBuilderFactory.get("stepParameterDemo")
				//通过构造方法传递参数
				.listener(this)
				.tasklet(new Tasklet() {
					@Override
					public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
						//输出接受到的参数值
						System.out.println(param.get("param"));
						return RepeatStatus.FINISHED;
					}
				}).build();
	}
	
	@Override
	public void beforeStep(StepExecution stepExecution) {
		//任务执行前赋值传递的参数
		param = stepExecution.getJobParameters().getParameters();
		
	}

	@Override
	public ExitStatus afterStep(StepExecution stepExecution) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
