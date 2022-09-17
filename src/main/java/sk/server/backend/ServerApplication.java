package sk.server.backend;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import sk.server.backend.controller.secure.JWTAuthorizationFilter;

//importy na pridanie na server

import sk.server.backend.domain.User;
import sk.server.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;

@SpringBootApplication
public class ServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(ServerApplication.class, args);
	}

//	public class CommandLineAppStartupRunner implements CommandLineRunner{		// Prípade pridania na server musi vytvoriť prvého admina
//
//		@Autowired
//		UserService userService;
//
//		@Override
//		public void run(String... args) throws Exception {
//			User admin = new User(1L,"Branislav","Socha","branislavsocha159@gmail.com","123",null,null);
//			userService.create(admin);
//		}
//	}


	@EnableWebSecurity
	@Configuration
	class WebSecurityConfig extends WebSecurityConfigurerAdapter {

		@Override
		protected void configure(HttpSecurity http) throws Exception {
			http.csrf().disable()
					.addFilterAfter(new JWTAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class)
					.authorizeRequests()
					.antMatchers(HttpMethod.POST, "/api/user/auth/").permitAll()
					.antMatchers(HttpMethod.GET,"/api/rezervation/kalendar").permitAll()
					.antMatchers(HttpMethod.GET,"/api/rezervation/free").permitAll()
					.anyRequest().authenticated();
		}
	}
}
