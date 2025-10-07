package saj.startup.pj.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import saj.startup.pj.common.CommonConstant;

@EnableWebSecurity
@Configuration
public class SecurityConfig {

	@Autowired
	private DataSource dataSource;

	@Bean
	protected PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	private static final String USER_ACCOUNT_SQL = "SELECT USERNAME,PASSWORD,TRUE"
			+ " FROM USERS "
			+ " WHERE USERNAME = ?"
			+ " AND USERS.IS_DELETED = FALSE ";

	private static final String USER_ROLE_SQL = "SELECT USERNAME, ROLE FROM USERS WHERE USERNAME = ?";
	
	@Bean
	protected UserDetailsManager userDetailsService() {

		JdbcUserDetailsManager users = new JdbcUserDetailsManager(dataSource);

		users.setUsersByUsernameQuery(USER_ACCOUNT_SQL);
		users.setAuthoritiesByUsernameQuery(USER_ROLE_SQL);

		return users;
	}
	
	@Bean
	protected AuthenticationSuccessHandler authenticationSuccessHandler() {
		return new CustomSuccessHandler();
	}

	
	@Bean
	protected SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http
				.authorizeHttpRequests((requests) -> requests
						
						.requestMatchers("/images/**").permitAll()
						.requestMatchers("/css/**").permitAll()
						.requestMatchers("/script/**").permitAll()
						.requestMatchers("/fonts/**").permitAll()
						
						.requestMatchers("/admin/**").hasAnyAuthority(CommonConstant.ROLE_ADMIN)
						.requestMatchers("/api/admin/**").hasAnyAuthority(CommonConstant.ROLE_ADMIN)
						
						.requestMatchers("/dashboard/**").hasAuthority(CommonConstant.ROLE_USER)
						.requestMatchers("/quiz/**").hasAuthority(CommonConstant.ROLE_USER)
						
						.anyRequest().permitAll()
						
						)
				.formLogin((form) -> form
						.loginPage("/login")
						.permitAll()
						.failureUrl("/login")
						//.failureHandler(authenticationFailureHandler())
						.usernameParameter("username")
						.passwordParameter("password")
						 .defaultSuccessUrl("/dashboard")
						.successHandler(authenticationSuccessHandler()))
				.logout((logout) -> logout
						.logoutSuccessUrl("/login")
						.invalidateHttpSession(true)
						.permitAll());

		return http.build();
	}
}
