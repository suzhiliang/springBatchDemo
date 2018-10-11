package suzhiliang.springBatchDemo.itemReaderXml;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.input.XmlStreamReader;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.xml.StaxEventItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.oxm.xstream.XStreamMarshaller;

/**
 * 读取xml文件内容
 * @author 苏志亮
 *
 */
@Configuration
@EnableBatchProcessing
public class ItemReaderXmlDemo {
	
	@Autowired
	private JobBuilderFactory jobBuilderFactory;

	@Autowired
	private StepBuilderFactory stepBuilderFactory;
    
	@Autowired
	@Qualifier("staxEventItemWriter")
	private ItemWriter<? super Customer> staxEventItemWriter;
	
	@Bean
	public Job itemReaderXmlJob(){
		return jobBuilderFactory.get("itemReaderXmlJob")
				.start(itemReaderXmlStep())
				.build();
		
	}
	
    /**
     * 
     * @return
     */
	@Bean
	public Step itemReaderXmlStep() {
		return stepBuilderFactory.get("itemReaderXmlStep")
				.<Customer,Customer>chunk(10)
				.reader(staxEventItemReader())
				.writer(staxEventItemWriter)
		        .build();
	}
    
	@SuppressWarnings("rawtypes")
	@Bean
	@StepScope
	public StaxEventItemReader<Customer> staxEventItemReader() {
		StaxEventItemReader<Customer> reader = new StaxEventItemReader<Customer>();
		reader.setResource(new ClassPathResource("customer.xml"));//设置对应的xml文件资源
		
		reader.setFragmentRootElementName("customer");//指定需要处理的根标签
		//xml转化cuntomer对象
		XStreamMarshaller unmarshaller = new XStreamMarshaller();
		Map<String, Class> map = new HashMap<String, Class>();
		map.put("customer", Customer.class);
		unmarshaller.setAliases(map);
		reader.setUnmarshaller(unmarshaller);
		
		return reader;
	}
	

}
