package chat.me.spring.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.ServletListenerRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.authentication.AuthenticationTrustResolver;
import org.springframework.security.authentication.AuthenticationTrustResolverImpl;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.security.web.session.HttpSessionEventPublisher;

import chat.me.service.impl.UserAccountServiceImpl;
import chat.me.spring.security.authentication.handlers.UserLoginSuccessHandler;
import chat.me.spring.security.authentication.handlers.UserLogoutSuccessHandler;

@Configuration
@EnableWebSecurity
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
	private SessionRegistry sessionRegistry;
	
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
        .antMatchers("/app/home/**").permitAll()
        .antMatchers("/user/**").permitAll()
        .antMatchers("/signup/**").permitAll()
        .anyRequest().authenticated()
        .and()
        .formLogin()
        .loginPage("/app/home/").permitAll()
        .loginProcessingUrl("/perform_login")
        .successHandler(userLoginSuccessHandler)
        //.failureUrl("/app/home/")
        .and()
        .httpBasic()
        .and()
        .logout()
        .logoutUrl("/perform_logout")
        .logoutSuccessHandler(userLogoutSuccessHandler)
        .deleteCookies("JSESSIONID");
        
        http.sessionManagement().maximumSessions(1).sessionRegistry(sessionRegistry);
	}
	
	@Bean
	public PersistentTokenRepository persistentTokenRepository() {
		JdbcTokenRepositoryImpl db = new JdbcTokenRepositoryImpl();
		db.setDataSource(jdbcTemplate.getDataSource());
		return db;
	}
	
	@Bean
	public RedirectStrategy redirectStrategy() {
		return new DefaultRedirectStrategy();
	}
	
	@Bean
	public AuthenticationTrustResolver authenticationTrustResolver() {
		return new AuthenticationTrustResolverImpl();
	}
	
}
