package com.bolsaideas.springboot.app;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.EnableGlobalAuthentication;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.bolsaideas.springboot.app.auth.handler.LoginSuccesHandler;
import com.bolsaideas.springboot.app.models.service.JpaUserDetailsService;

//para habilitar seguridad por metodos.
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
@Configuration
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter{

	//se crearia la instancia de BCrypt y se guarda en el contexto de spring
	//Tiene que estar en una clase configuration
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	
	@Autowired
	private LoginSuccesHandler successHandler;
	
	@Autowired 
	private DataSource dataSource;
	
	@Autowired
	private JpaUserDetailsService jpaUser;
	
	//El metodo se marca con autowired para inyectar AuthenticationManagerBuilder
	@Autowired 
	public void configurerGlobal(AuthenticationManagerBuilder builder) throws Exception{
		PasswordEncoder encoder = passwordEncoder();
		
		
		builder.userDetailsService(jpaUser)
		.passwordEncoder(encoder);
		
		/**AUTENTICACION JDBC
		builder.jdbcAuthentication()
		.dataSource(dataSource)
		.passwordEncoder(encoder)
		.usersByUsernameQuery("select USUA_USERNAME, USUA_PASSWORD, USUA_ENABLED FROM USUA_USER WHERE USUA_USERNAME = ?")
		.authoritiesByUsernameQuery("SELECT U.USUA_USERNAME, A.AUTH_AUTHORITY FROM USUA_AUTHORITIES A INNER JOIN USUA_USER U ON (A.USUA_ID = U.USUA_ID) WHERE U.USUA_USERNAME = ?");
		
		**/
		//AUTENTICACION EN MEMORIA
		/*PasswordEncoder encoder = passwordEncoder();
		//UserBuilder users = User.builder().passwordEncoder(password -> encoder.encode(password));
		//Otra forma
		UserBuilder users = User.builder().passwordEncoder(encoder::encode);
		
		builder.inMemoryAuthentication()
		.withUser(users.username("admin").password("12345").roles("ADMIN", "USER"))
		.withUser(users.username("Sandra").password("12345").roles( "USER"));*/
		
		
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests().antMatchers("/","/css/**", "/js/**", "/images/**", "/listar", "/listar-rest", "/locale", "/api/usuarios/**").permitAll()
		/* OTRA FORMA DE AUTORIZACION .antMatchers("/ver/**").hasAnyRole("USER")
		.antMatchers("/form/**").hasAnyRole("ADMIN")
		.antMatchers("/eliminar/**").hasAnyRole("ADMIN")
		.antMatchers("/album/**").hasAnyRole("ADMIN")*/
		.anyRequest().authenticated()
		.and()
		.formLogin()
			.successHandler(successHandler)
			.loginPage("/login")
		.permitAll()
		.and()
		.logout().permitAll()
		.and()
		.exceptionHandling().accessDeniedPage("/error_403");
	}
}
