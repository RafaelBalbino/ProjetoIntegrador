package com.generation.caiv.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity
public class BasicSecurityConfig extends WebSecurityConfigurerAdapter {

	// compara os dados digitados com os dados salvos do banco de dados
	@Autowired
	private UserDetailsService userDetailsService;

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService);//utiliza a superclasse 'userDetailsService', para conseguir cadastrar usuarios
		auth.inMemoryAuthentication().withUser("root").//checa se o usuário é root	
		password(passwordEncoder().encode("root"))//checa se a senha é root 
		.authorities("ROLE_USER");
	}
	// notação que deixa uma função acessível em toda aplicação
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
			http.authorizeRequests()
				.antMatchers("/**").permitAll()
		        .antMatchers("/usuario/logar").permitAll() // De qualquer lugar, você terá acesso a  login
				.antMatchers("/usuario/cadastrar").permitAll() // e cadastro já que as rotas estão abertas
				.antMatchers(HttpMethod.OPTIONS).permitAll() /* Permite que as rotas estejam acessíveis com GET Permite saber quais métodos estão abertos na documentação da API e que estão abertos nela e é possível utilizar eles.*/
				.antMatchers(HttpMethod.GET, "/postagem").permitAll()
				.antMatchers(HttpMethod.GET, "/tema").permitAll()
				.anyRequest().authenticated() // Para outras requisições, tem que está ou cadastrado ou em memória
				.and().httpBasic() // HttpBasic = CRUD | Define que só será aceito métodos CRUD
				.and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)// < Define que toda requisição tem começo, meio e fim. Uma por vez e ajuda a prevenir ataques cibernéticos e invasões com várias requisições de uma formaTipo quando expira o token em um site como na plataforma da GenerationBrasil.
				.and().cors()//Funciona como o '@CrossOrigins', vendo de qual porta está vindo a requisição e liberando acesso para todas (Do Front-end pro Back-end basicamente)
				.and().csrf().disable(); // Autoriza PUT e DELETE na requisição
	}

}