package suzhiliang.springBatchDemo.suzhiliang.config;

import java.util.Arrays;
import java.util.List;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.support.ListItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import suzhiliang.springBatchDemo.suzhiliang.config.listener.ChunkListenerDemo;
import suzhiliang.springBatchDemo.suzhiliang.config.listener.JobListenerDemo;

/**
 * 监听器的使用
 *   用来监听批处理执行情况
 *   创建监听器可以用实现接口的形式或者注解的形式来实现；
 *   监听器接口：
 *     
 * @author 苏志亮
 *
 */
@Configuration
@EnableBatchProcessing
public class ListenerDemo {
	
	@Autowired
	private JobBuilderFactory jobBuilderFactory;

	@Autowired
	private StepBuilderFactory stepBuilderFactory;
	

	/**
	 * 创建job
	 * @return
	 */
	@Bean
	public Job  listenerJobDemo() {
		return jobBuilderFactory.get("listenerJobDemo")
				.start(stepListenDemo())
				.listener(new JobListenerDemo())//job监听器
				.build();
	}
    
	//step
	@Bean
	public Step stepListenDemo() {
		return stepBuilderFactory.get("stepListenDemo")
				.<String,String>chunk(2)//设置chunk读取2个单位输出；
		        .faultTolerant()
		        .listener(new ChunkListenerDemo())//chunk监听器
		        .reader(itemReader()) //读取
		        .writer(itemWriter())
		        .build();
	}
    
	//写字符串数据
	@Bean
	public ItemWriter<String> itemWriter() {
		return new ItemWriter<String>(){
			@Override
			public void write(List<? extends String> items) throws Exception {
				for(String item:items){
					System.out.println(item);
				}
			}
			
		};
	}
    
	//读取字符串数据
	@Bean
	public ItemReader<String> itemReader() {
		return new ListItemReader<>(Arrays.asList("java,listener,batch"));
	}
	
	
}
