package com.trade.properties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "trade.jwt")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class JwtProperties {

    private String secretKey;
    private long ttl;
    private String tokenName;


}
