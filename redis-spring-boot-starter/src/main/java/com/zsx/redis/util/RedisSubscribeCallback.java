package com.zsx.redis.util;

 
public interface RedisSubscribeCallback {
    void callback(String msg);
}
