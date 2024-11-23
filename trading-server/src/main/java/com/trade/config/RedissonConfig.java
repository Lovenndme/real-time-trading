package com.trade.config;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RedissonConfig {

    @Bean
    public RedissonClient redissonClient() {
        Config config = new Config();
        config.useClusterServers()
                .addNodeAddress("redis://47.122.40.210:6379", "redis://47.122.40.210:6380",
                        "redis://47.122.40.210:6381", "redis://47.122.40.210:6382",
                        "redis://47.122.40.210:6383", "redis://47.122.40.210:6384");
        return Redisson.create(config);
    }

}
