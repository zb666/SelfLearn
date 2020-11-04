import com.demo.ProducerApplication;
import com.demo.config.RabbitMQConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ProducerApplication.class)
public class RabbitMQTest {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Test
    public void test(){
        rabbitTemplate.convertAndSend(RabbitMQConfig.ITEM_TOPIC_EXCHANGE, "item.insert", "商品新增，routing key 为item.insert");
        rabbitTemplate.convertAndSend(RabbitMQConfig.ITEM_TOPIC_EXCHANGE, "item.update", "商品修改，routing key 为item.update");
        rabbitTemplate.convertAndSend(RabbitMQConfig.ITEM_TOPIC_EXCHANGE, "item.delete", "商品删除，routing key 为item.delete");
    }

    @Test
    public void ttlQueueTest(){
        rabbitTemplate.convertAndSend("my_ttl_queue","发送消息到过期队列my_ttl_queue,6秒内不消费则不能再被消费");
    }

    @Test
    public void dlxTTLMessageTest(){
        rabbitTemplate.convertAndSend("my_normal_exchange", "my_ttl_dlx", "测试过期消息；6秒过期后会被投递到死信交换机");
    }

}
