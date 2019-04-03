package vn.toancauxanh.sso;

import org.pac4j.core.config.Config;
import org.pac4j.springframework.security.web.CallbackFilter;
import org.pac4j.springframework.security.web.Pac4jEntryPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@EnableWebSecurity
@Configuration
public class CasSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private Config config;

	@Autowired
	CasProperties properties;
	
	protected void configure(final HttpSecurity http) throws Exception {

		final CallbackFilter callbackFilter = new CallbackFilter(config, properties.getFrontendUrl());

		http.authorizeRequests().antMatchers("/cas/login").authenticated().anyRequest().permitAll().and()
				.exceptionHandling().authenticationEntryPoint(new Pac4jEntryPoint(config, "CasClient")).and()
				.addFilterBefore(callbackFilter, BasicAuthenticationFilter.class)
				.csrf().disable();
		http.logout().invalidateHttpSession(true).clearAuthentication(true)
				.deleteCookies("JSESSIONID")
//				.logoutUrl("/auth/logout")
				.logoutSuccessUrl("/auth/logout")
				.clearAuthentication(true)
				.invalidateHttpSession(true);
		http.headers().frameOptions().sameOrigin();
	}

}
