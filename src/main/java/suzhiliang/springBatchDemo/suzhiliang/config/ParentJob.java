package suzhiliang.springBatchDemo.suzhiliang.config;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.job.flow.JobExecutionDecider;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.builder.JobStepBuilder;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

/**
 * 父job；
 * 一个job可以嵌套在另一个job中，叫做子job，
 * 外部的job称为父job，子job不能单独执行，必须由父job调用。
 * @author 苏志亮
 *
 */
@Configuration
@EnableBatchProcessing
public class ParentJob {
	
	@Autowired
    private JobBuilderFactory jobBuilderFactory; 
	
	//注入2个子job
	@Autowired
	private Job childJobOne1;
	
	@Autowired
	private Job childJobTwo2;
	
	//job的启动对象
	@Autowired
	private JobLauncher joblauncher;
	
	@Bean
	public Job  parentJobs(JobRepository jobRepository,PlatformTransactionManager transactionManager){
		return jobBuilderFactory.get("parentJobs")
				.start(childJob1(jobRepository, transactionManager))
				.next(childJob2(jobRepository, transactionManager)).build();
	}
    
	//返回JOB类型的Step，特殊的Step
	private Step childJob1(JobRepository jobRepository,PlatformTransactionManager transactionManager) {
		return new JobStepBuilder(new StepBuilder("childJob1"))
				.job(childJobOne1)
				.launcher(joblauncher)//使用启动父JOB的启动对象。
				.repository(jobRepository)
				.transactionManager(transactionManager)
				.build();
	}
	
	private Step childJob2(JobRepository jobRepository,PlatformTransactionManager transactionManager) {
		return new JobStepBuilder(new StepBuilder("childJob2"))
				.job(childJobTwo2)
				.launcher(joblauncher)//使用启动父JOB的启动对象。
				.repository(jobRepository)
				.transactionManager(transactionManager)
				.build();
	}
}
