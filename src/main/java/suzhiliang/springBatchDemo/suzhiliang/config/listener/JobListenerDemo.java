package suzhiliang.springBatchDemo.suzhiliang.config.listener;

import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;

/**
 * 以实现接口的方式创建job监听器
 * @author 苏志亮
 *
 */
public class JobListenerDemo implements JobExecutionListener {
    
	//job执行之前
	@Override
	public void beforeJob(JobExecution jobExecution) {
       System.out.println(jobExecution.getJobInstance().getJobName()+"jobBefore.....");		
	}
    
	//job执行之后
	@Override
	public void afterJob(JobExecution jobExecution) {
		 System.out.println(jobExecution.getJobInstance().getJobName()+"jobAfter.....");	
	}

}
