package com.stefan.security;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class SecurityApplication {

	public static void main(String[] args) {
		SpringApplication.run(SecurityApplication.class, args);


	}

//	@Bean
//	public CommandLineRunner commandLineRunner(
//			AuthenticationService service
//	) {
//		return args -> {
//			var admin = RegisterRequest.builder()
//					.firstname("Admin")
//					.lastname("Admin")
//					.email("admin@mail.com")
//					.password("password")
//				//	.role(ADMIN)
//					.build();
//			System.out.println("Admin token: " + service.register(admin).getToken());
//
//			var manager = RegisterRequest.builder()
//					.firstname("Admin")
//					.lastname("Admin")
//					.email("manager@mail.com")
//					.password("password")
//				//	.role(MANAGER)
//					.build();
//			System.out.println("Manager token: " + service.register(manager).getToken());
//
//		};
//	}
}
