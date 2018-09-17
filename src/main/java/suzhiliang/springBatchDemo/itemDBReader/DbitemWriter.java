package suzhiliang.springBatchDemo.itemDBReader;

import java.util.List;

import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

@Component("dbitemWriter")
public class DbitemWriter implements ItemWriter<User>{

	@Override
	public void write(List<? extends User> users) throws Exception {
		for(User user:users){
			System.out.println(user);
			System.out.println("----------------------------------------");
		}
		
	}

}
