package chat.me.spring.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;


@Configuration
@EnableWebMvc
@ComponentScan("chat.me.controller")
public class ServletContextConfig extends WebMvcConfigurerAdapter{

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/chat-me/**").addResourceLocations("/chat-me/");
		registry.addResourceHandler("/chat-me/home/**").addResourceLocations("/chat-me/home/");
		registry.addResourceHandler("/chat-me/messenger/**").addResourceLocations("/chat-me/messenger/");
	}
}
