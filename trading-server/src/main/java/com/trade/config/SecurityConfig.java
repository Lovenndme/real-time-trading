package com.trade.config;

import com.trade.filter.JwtAuthenticationTokenFilter;
import com.trade.service.impl.AccessDeniedHandlerImpl;
import com.trade.service.impl.AuthenticationEntryPointImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter;

    private final AccessDeniedHandlerImpl accessDeniedHandler;

    private final AuthenticationEntryPointImpl authenticationEntryPoint;

    // 配置安全过滤链
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // 禁用CSRF保护
                .csrf(AbstractHttpConfigurer::disable)
                // 设置会话创建策略为无状态
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                // 配置授权规则
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/authorization/sendCode").anonymous()
                        .requestMatchers("/authorization/register").anonymous()
                        .requestMatchers("/authorization/login").anonymous()
                        .requestMatchers("/doc.html").anonymous()
                        .requestMatchers("/trade/paymentCode").anonymous()
                        .requestMatchers("/webjars/**").anonymous()
                        .requestMatchers("/v3/**").anonymous()
                        .requestMatchers("/swagger-resources/**").anonymous()
                        .anyRequest().authenticated()  // 任何其他请求都需要认证
                )
                // 开启跨域访问
                .cors(AbstractHttpConfigurer::disable)
                // 添加自定义过滤器
                .addFilterBefore(jwtAuthenticationTokenFilter, UsernamePasswordAuthenticationFilter.class)
                // 配置异常处理
                .exceptionHandling(exception -> exception.accessDeniedHandler(accessDeniedHandler).authenticationEntryPoint(authenticationEntryPoint));

        // 构建并返回安全过滤链
        return http.build();
    }
}
