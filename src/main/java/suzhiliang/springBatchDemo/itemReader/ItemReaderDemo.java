package suzhiliang.springBatchDemo.itemReader;

import java.util.Arrays;
import java.util.List;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * itemReader数据读取
 * @author 苏志亮
 *
 */
@Configuration
@EnableBatchProcessing
public class ItemReaderDemo {
	
	@Autowired
	private JobBuilderFactory jobBuilderFactory;

	@Autowired
	private StepBuilderFactory stepBuilderFactory;
	
	@Bean
	public Job itemReaderJobDemo(){
		return jobBuilderFactory.get("itemReaderJobDemo")
				.start(itemReaderStepDemo()).build();
		
	}

	@Bean
	public Step itemReaderStepDemo() {
		return stepBuilderFactory.get("itemReaderStepDemo")
				.<String,String>chunk(2)
				.reader(itemReaderReader())
				.writer(list->{for(String item:list){
					System.out.println(item+">>>>");
					}
				}).build();
	}

	private MyItemReader itemReaderReader() {
		List<String> data = Arrays.asList("doc","cat","hun","tuzi");
		return new MyItemReader(data);
	}

}
