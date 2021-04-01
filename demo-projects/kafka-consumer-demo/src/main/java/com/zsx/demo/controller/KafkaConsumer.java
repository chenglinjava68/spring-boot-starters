package com.zsx.demo.controller;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.PartitionOffset;
import org.springframework.kafka.annotation.TopicPartition;
import org.springframework.kafka.listener.ConsumerAwareListenerErrorHandler;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

/**
 * @Description TODO
 * @Date 2021/3/12 17:41
 * @Created by zhushuxian
 */
@Component
public class KafkaConsumer {
    private final Logger logger = LoggerFactory.getLogger(KafkaConsumer.class);

    @KafkaListener(topics = {"test"})
    public void onMessage(ConsumerRecord<?, ?> consumerRecord) {
        Optional<?> optional = Optional.ofNullable(consumerRecord.value());
        if (optional.isPresent()) {
            Object msg = optional.get();
            logger.info("record:{}", consumerRecord);
            logger.info("message:{}", msg);
        }
    }

    // 消费监听
    @KafkaListener(topics = {"topic1"})
    public void onMessage1(ConsumerRecord<?, ?> record){
        // 消费的哪个topic、partition的消息,打印出消息内容
        System.out.println("简单消费："+record.topic()+"-"+record.partition()+"-"+record.value());
    }

    /**
     * @Title 指定topic、partition、offset消费
     * @Description 同时监听topic1和topic2，监听topic1的0号分区、topic2的 "0号和1号" 分区，指向1号分区的offset初始值为8
     * @Author long.yuan
     * @Date 2020/3/22 13:38
     * @Param [record]
     * @return void
     **/
    @KafkaListener(id = "consumer1",groupId = "felix-group",topicPartitions = {
            @TopicPartition(topic = "topic1", partitions = { "0" }),
            @TopicPartition(topic = "topic2", partitions = "0", partitionOffsets = @PartitionOffset(partition = "1", initialOffset = "8"))
    })
    public void onMessage2(ConsumerRecord<?, ?> record) {
        System.out.println("topic:"+record.topic()+"|partition:"+record.partition()+"|offset:"+record.offset()+"|value:"+record.value());
    }

    //批量消费
    @KafkaListener(id = "consumer2",groupId = "felix-group", topics = "topic1")
    public void onMessage3(List<ConsumerRecord<?, ?>> records) {
        System.out.println(">>>批量消费一次，records.size()="+records.size());
        for (ConsumerRecord<?, ?> record : records) {
            System.out.println(record.value());
        }
    }


    // 新建一个异常处理器，用@Bean注入
    @Bean
    public ConsumerAwareListenerErrorHandler consumerAwareErrorHandler() {
        return (message, exception, consumer) -> {
            System.out.println("消费异常："+message.getPayload());
            return null;
        };
    }

    // 将这个异常处理器的BeanName放到@KafkaListener注解的errorHandler属性里面
    @KafkaListener(topics = {"topic1"},errorHandler = "consumerAwareErrorHandler")
    public void onMessage4(ConsumerRecord<?, ?> record) throws Exception {
        throw new Exception("简单消费-模拟异常");
    }

    // 批量消费也一样，异常处理器的message.getPayload()也可以拿到各条消息的信息
    @KafkaListener(topics = "topic1",errorHandler="consumerAwareErrorHandler")
    public void onMessage5(List<ConsumerRecord<?, ?>> records) throws Exception {
        System.out.println("批量消费一次...");
        throw new Exception("批量消费-模拟异常");
    }
}