package suzhiliang.springBatchDemo.itemDBReader;

import java.sql.ResultSet;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JdbcPagingItemReader;
import org.springframework.batch.item.database.Order;
import org.springframework.batch.item.database.support.MySqlPagingQueryProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.RowMapper;


/**
 * 实现从数据库中读取数据
 * @author 苏志亮
 *
 */
@Configuration
@EnableBatchProcessing
public class ItemReaderDBDemo {
	
	@Autowired
	@Qualifier("dbitemWriter")
	private ItemWriter<? super User> dbitemWriter;

	@Autowired
	private JobBuilderFactory jobBuilderFactory;

	@Autowired
	private StepBuilderFactory stepBuilderFactory;
	
	@Autowired
	private DataSource dataSource;
	
	@Bean
	public Job itemReaderDBJobDemo(){
		return jobBuilderFactory.get("itemReaderDBJobDemo")
				.start(itemReaderDBStepDemo()).build();
		
	}

	@Bean
	public Step itemReaderDBStepDemo() {
		return stepBuilderFactory.get("itemReaderDBStepDemo")
				.<User,User>chunk(2)
				.reader(dbitemReader())
				.writer(dbitemWriter)
				.build();
	}
    
	@Bean
	public JdbcPagingItemReader<User> dbitemReader() {
		JdbcPagingItemReader<User> reader = new JdbcPagingItemReader<User>();
		reader.setDataSource(dataSource);//数据源
		reader.setRowMapper(new RowMapper<User>() {
			
			@Override
			public User mapRow(ResultSet rst, int rowNum) throws SQLException {
				User user = new User();
				user.setId(rst.getLong(1));
				user.setName(rst.getString(2));
				user.setAge(rst.getInt(3));
				user.setAddress(rst.getString(4));
				return user;
			}
		});
		MySqlPagingQueryProvider mySqlPagingQueryProvider = new MySqlPagingQueryProvider();
		mySqlPagingQueryProvider.setSelectClause("id,name,age,address");//查询的字段
		mySqlPagingQueryProvider.setFromClause("from user");//查询的表名
		
		//排序字段
		Map<String, Order> sortKeys = new HashMap<String,Order>(1);
		sortKeys.put("id", Order.ASCENDING);//按id排序
		
		mySqlPagingQueryProvider.setSortKeys(sortKeys);
		reader.setQueryProvider(mySqlPagingQueryProvider);
		
		return reader;
	}

	
}
