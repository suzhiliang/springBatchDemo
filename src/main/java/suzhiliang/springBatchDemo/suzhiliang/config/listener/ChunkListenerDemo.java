package suzhiliang.springBatchDemo.suzhiliang.config.listener;

import org.springframework.batch.core.annotation.AfterChunk;
import org.springframework.batch.core.annotation.BeforeChunk;
import org.springframework.batch.core.scope.context.ChunkContext;

/**
 * 以注解的方式执行chunk监听器
 * @author 苏志亮
 *
 */
public class ChunkListenerDemo {
	
	//chunk执行之前
	@BeforeChunk
    public void chunkBefore(ChunkContext chunkContext){
    	System.out.println(chunkContext.getStepContext().getStepName()+"chunkBefore.....");
    }
	
	//chunk执行之后
	@AfterChunk
	public void chunkAfter(ChunkContext chunkContext) {
		System.out.println(chunkContext.getStepContext().getStepName() + "chunkAfter.....");
	}
}
