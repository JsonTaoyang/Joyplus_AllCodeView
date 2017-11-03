package com.joyplus;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.scheduling.annotation.EnableAsync;

import com.joyplus.util.TaskThreadPoolConfig;




@SpringBootApplication
@EnableAsync
@EnableConfigurationProperties({TaskThreadPoolConfig.class}) // 开启配置属性支持 
public class JoyplusWebserverApplication extends SpringBootServletInitializer implements EmbeddedServletContainerCustomizer {


	public static void main(String[] args) {
		SpringApplication.run(JoyplusWebserverApplication.class, args);
		


	}
	@Override
	public void customize(ConfigurableEmbeddedServletContainer container) {
		 container.setPort(8081);
	}
}
