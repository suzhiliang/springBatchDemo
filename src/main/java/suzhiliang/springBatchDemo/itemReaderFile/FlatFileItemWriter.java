package suzhiliang.springBatchDemo.itemReaderFile;

import java.util.List;

import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

@Component("flatFileItemWriter")
public class FlatFileItemWriter implements ItemWriter<Customer> {

	@Override
	public void write(List<? extends Customer> customers) throws Exception {
		 for(Customer customer:customers){
			 System.out.println(customer);
		 }
	}

}
