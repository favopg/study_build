package study.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig{
	
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http
			.anonymous()
				.key("customAnonymousKey")
				.principal("新規ユーザ")
			.and()
			.authorizeRequests()
				.antMatchers("/find_id").permitAll()
				.antMatchers("/introduce").permitAll()
				.antMatchers("/login/community").permitAll()
				.antMatchers("/community").hasAuthority("ADMIN")
				.antMatchers("/user_transition").permitAll()
				.antMatchers("/user_register").permitAll()
				.antMatchers("/img/**").permitAll()
				.antMatchers("/resources/**").permitAll()
			.anyRequest().authenticated()
			.and()
			.formLogin()
				.loginPage("/login")
				.permitAll()
				//.defaultSuccessUrl("/introduce")
				.defaultSuccessUrl("/login/community")
				.failureUrl("/login?error=true")
			.and()
			.logout()
				.invalidateHttpSession(true)
				.clearAuthentication(true)
				.deleteCookies("JSESSIONID")
				.logoutUrl("/logout")
				.logoutSuccessUrl("/login?logout")
				.permitAll();
				
		return http.build();
	}
		
	@Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

}
