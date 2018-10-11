package suzhiliang.springBatchDemo.itemReaderXml;

import java.util.List;


import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

@Component("staxEventItemWriter")
public class StaxEventItemWriter implements ItemWriter<Customer> {

	@Override
	public void write(List<? extends Customer> customers) throws Exception {
		 for(Customer customer:customers){
			 System.out.println(customer);
		 }
	}

}
