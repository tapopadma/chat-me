package chat.me.spring.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.ServletListenerRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.security.web.session.HttpSessionEventPublisher;

import chat.me.service.impl.UserAccountServiceImpl;

@Configuration
@EnableWebSecurity
@ImportResource({
	"classpath:spring-servlet.xml"
})
public class SecurityConfig extends WebSecurityConfigurerAdapter{

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private UserAccountServiceImpl userAccountServiceImpl;
	
	@Autowired
	private UserLoginSuccessHandler userLoginSuccessHandler;
	
	@Autowired
	private UserLogoutSuccessHandler userLogoutSuccessHandler;
	
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception{
		auth.userDetailsService(userAccountServiceImpl);
		auth.authenticationProvider(authenticationProvider());
	}
	
	@Bean
	public DaoAuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
		authenticationProvider.setUserDetailsService(userAccountServiceImpl);
		//authenticationProvider.setPasswordEncoder(passwordEncoder());
		return authenticationProvider;
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public SessionRegistry sessionRegistry() {
		return new SessionRegistryImpl();
	}
	
	@Bean
	public ServletListenerRegistrationBean<HttpSessionEventPublisher> httpSessionEventPublisher() {
		return new ServletListenerRegistrationBean<HttpSessionEventPublisher>(
				new HttpSessionEventPublisher());
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
		.csrf().disable();
        
        http
        .authorizeRequests()
        .antMatchers("/login*").permitAll()
        .antMatchers("/app/signup.html").permitAll()
        .antMatchers("/app/home.html").permitAll()
        .antMatchers("/app/js/**").permitAll()
        .antMatchers("/signup**").permitAll()
        .antMatchers("/user**").permitAll()
        .antMatchers("/messenger**").permitAll()
        .anyRequest().authenticated()
        .and()
        .formLogin()
        .loginProcessingUrl("/perform_login")
        .successHandler(userLoginSuccessHandler)
        .failureUrl("/perform_login")
        .and()
        .httpBasic()
        .and()
        .logout()
        .logoutUrl("/perform_logout")
        .logoutSuccessHandler(userLogoutSuccessHandler)
        .deleteCookies("JSESSIONID");
        
        http.sessionManagement().maximumSessions(1).sessionRegistry(sessionRegistry());
	}
	
	@Bean
	public PersistentTokenRepository persistentTokenRepository() {
		JdbcTokenRepositoryImpl db = new JdbcTokenRepositoryImpl();
		db.setDataSource(jdbcTemplate.getDataSource());
		return db;
	}
	
}
