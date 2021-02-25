package com.zsx.rabbitmq.producer;



import com.zsx.rabbitmq.common.DetailResponse;


public interface MessageProcess<T> {
    DetailResponse process(T message);
}
