package com.zsx.rabbitmq.producer;

import com.zsx.rabbitmq.common.DetailResponse;

public interface MessageProducer {


    DetailResponse send(Object message);

}