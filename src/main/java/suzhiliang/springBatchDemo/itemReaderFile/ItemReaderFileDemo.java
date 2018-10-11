package suzhiliang.springBatchDemo.itemReaderFile;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.validation.BindException;

/**
 * 从文件中读取数据
 * FlatFileItemReader 使用该类读取文件
 * @author 苏志亮
 *
 */
@Configuration
@EnableBatchProcessing
public class ItemReaderFileDemo {
	
	@Autowired
	private JobBuilderFactory jobBuilderFactory;

	@Autowired
	private StepBuilderFactory stepBuilderFactory;
    
	@Autowired
	@Qualifier("flatFileItemWriter")
	private ItemWriter<? super Customer> flatFileItemWriter;
	
	@Bean
	public Job itemReaderFileJob(){
		
		return jobBuilderFactory.get("itemReaderFileJob")
				.start(itemReaderFileStep())
				.build();
		
	}
	
	@Bean
	public Step itemReaderFileStep() {
		return stepBuilderFactory.get("itemReaderFileStep")
				.<Customer,Customer>chunk(10)
				.reader(flatFileItemReader())
				.writer(flatFileItemWriter)
				.build();
	}
    
	@Bean
	@StepScope
	public FlatFileItemReader<Customer> flatFileItemReader() {
		FlatFileItemReader<Customer> reader = new FlatFileItemReader<Customer>();
		reader.setResource(new ClassPathResource("customer.txt"));
		reader.setLinesToSkip(1);//跳过第一行
		
		//解析数据
		DelimitedLineTokenizer tokenizer = new DelimitedLineTokenizer();
		tokenizer.setNames(new String[]{"id","firstName","lastName","birthday"});//设置列字段名
		
		//将解析出来的行数据映射成customer对象
		DefaultLineMapper<Customer> mapper = new DefaultLineMapper<Customer>();
		mapper.setLineTokenizer(tokenizer);
		mapper.setFieldSetMapper(new FieldSetMapper<Customer>(){

			@Override
			public Customer mapFieldSet(FieldSet field) throws BindException {
				Customer customer = new Customer();
				customer.setId(field.readLong("id"));
				customer.setFirstName(field.readString("firstName"));
				customer.setLastName(field.readString("lastName"));
				customer.setBirthday(field.readString("birthday"));
				return customer;
			}
			
		});
		mapper.afterPropertiesSet();//检查
		reader.setLineMapper(mapper);
		return reader;
	}
}
