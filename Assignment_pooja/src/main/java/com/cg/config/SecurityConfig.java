package com.cg.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration

public class SecurityConfig {

		@Bean
		public UserDetailsService getUserDetailsService() {
			return new UserDetailsServiceImpl();
		}
	
	//We need the bean of BCryptPasswordEncoder for encoding our password
		@Bean
		public BCryptPasswordEncoder passwordEncoder() {
			return new BCryptPasswordEncoder();
		}
		

		//We need bean of DaoAuthenticationProvider because we will be our providing security for dao(memory/db) objects 
		@Bean
		public DaoAuthenticationProvider authenticationProvider() {
			DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
			daoAuthenticationProvider.setUserDetailsService(this.getUserDetailsService());
			daoAuthenticationProvider.setPasswordEncoder(this.passwordEncoder());
			return daoAuthenticationProvider;
		}
		
		//@Bean
		/*public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
			httpSecurity.csrf(csrf->csrf.disable())
						.authorizeHttpRequests(auth->
										auth.requestMatchers("/**").hasRole("USER").anyRequest().permitAll()
										.requestMatchers("/home/admin/*").hasRole("ADMIN").anyRequest()
										.authenticated()				
								)
							   .formLogin(obj->obj.defaultSuccessUrl("/home/admin/test"));
								
			return httpSecurity.build();
		} */
		
		@Bean
		public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {	 			
			return http.csrf(obj->obj.disable())
	                .authorizeHttpRequests(auth -> {
	                            auth.requestMatchers("/home/admin/**").hasRole("ADMIN");
	                            auth.requestMatchers("/home/user/*").hasRole("NORMAL_USER");
	                            auth.requestMatchers("/h2-console/**").permitAll();
	                            auth.requestMatchers("/**").permitAll();
	                            
	                            auth.anyRequest().authenticated();
	                        }
	                )
	                .formLogin(obj->obj
	                		.loginPage("/login")
	                		.loginProcessingUrl("/dologin")
	                		.defaultSuccessUrl("/home/user/index")
	                		)
	                .headers(headers -> headers.frameOptions().disable())  // Disable X-Frame-Options header
	  
	                .build();
		}
}
