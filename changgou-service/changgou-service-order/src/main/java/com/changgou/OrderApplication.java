package com.changgou;

import com.changgou.order.interceptor.FeignInterceptor;
import entity.IdWorker;
import entity.TokenDecode;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients(basePackages = {"com.changgou.goods.feign","com.changgou.user.feign"})
@MapperScan(basePackages = {"com.changgou.order.dao"})
public class OrderApplication {

    public static void main(String[] args) {
        SpringApplication.run(OrderApplication.class,args);
    }

    /***
     * 创建拦截器Bean对象
     * @return
     */
    @Bean
    public FeignInterceptor feignInterceptor(){
        return new FeignInterceptor();
    }

    /**
     * 解析用户令牌对象
     * @return
     */
    @Bean
    public TokenDecode tokenDecode(){
        return new TokenDecode();
    }

    /**
     * ID生成
     * @return
     */
    @Bean
    public IdWorker idWorker(){
        return new IdWorker(0,1);
    }
}