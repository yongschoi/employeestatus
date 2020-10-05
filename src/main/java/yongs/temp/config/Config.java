package yongs.temp.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration  
public class Config {
	@Bean
	@LoadBalanced // @LoadBalanced가 없으면 docker 환경에서 eureka/zipkin call 이 안됨 
	public WebClient.Builder loadBalancedWebClientBuilder() {
	    return WebClient.builder();
	}
}