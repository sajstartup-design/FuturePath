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
	
	private static final String USER_ACCOUNT_SQL = "SELECT EMAIL,PASSWORD,TRUE"
			+ " FROM USERS "
			+ " WHERE EMAIL = ?"
			+ " AND USERS.IS_DELETED = FALSE ";

	private static final String USER_ROLE_SQL = "SELECT EMAIL, ROLE FROM USERS WHERE EMAIL = ?";
	
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
						
						.requestMatchers("/admin/**").hasAnyAuthority(CommonConstant.ROLE_ADMIN)
						.requestMatchers("/api/admin/**").hasAnyAuthority(CommonConstant.ROLE_ADMIN)
						
						.anyRequest().permitAll()
						
						)
				.formLogin((form) -> form
						.loginPage("/login")
						.permitAll()
						.failureUrl("/login")
						//.failureHandler(authenticationFailureHandler())
						.usernameParameter("email")
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
