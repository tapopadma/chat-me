package chat.me.spring.config;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

@Configuration
@ComponentScan({
	"chat.me.dao", "chat.me.service", "chat.me.spring.security.authentication.handlers", "chat.me.entity"
})
public class DBResourceConfig {
		
	@Bean
	public DataSource dataSource() {
		/*DriverManagerDataSource ds = new DriverManagerDataSource();
		ds.setDriverClassName("com.mysql.jdbc.Driver");
		ds.setUrl("jdbc:mysql://localhost:3306/chat_me");
		ds.setUsername("tapo");
		ds.setPassword("tapo123");
		return ds;*/
		DriverManagerDataSource ds = new DriverManagerDataSource();
		ds.setDriverClassName("com.mysql.jdbc.Driver");
		ds.setUrl("jdbc:mysql://us-cdbr-iron-east-04.cleardb.net/heroku_a32b55e98ed6831");
		ds.setUsername("b1a8f606c00912");
		ds.setPassword("a944bb13");
		return ds;
	}
		
	@Bean
	public JdbcTemplate jdbcTemplate() {
		return new JdbcTemplate(dataSource());
	}
}
