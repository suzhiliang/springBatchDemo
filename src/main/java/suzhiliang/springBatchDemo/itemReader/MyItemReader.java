package suzhiliang.springBatchDemo.itemReader;

import java.util.Iterator;
import java.util.List;

import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;

public class MyItemReader implements ItemReader<String> {
	
	private Iterator<String> iterator;

	public MyItemReader(List<String> data) {
		iterator = data.iterator();
	}

	@Override
	public String read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
		//一个数据一个数据的读取
		if(iterator.hasNext()){
			return iterator.next();
		}else{
			return null;
		}
	}

}
