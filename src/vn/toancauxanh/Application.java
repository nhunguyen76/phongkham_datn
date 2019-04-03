package vn.toancauxanh;

import java.text.ParseException;

import javax.persistence.EntityManager;

import org.pac4j.cas.client.CasClient;
import org.pac4j.cas.config.CasConfiguration;
import org.pac4j.cas.config.CasProtocol;
import org.pac4j.core.authorization.authorizer.RequireAnyRoleAuthorizer;
import org.pac4j.core.client.Clients;
import org.pac4j.core.config.Config;
import org.pac4j.http.client.direct.HeaderClient;
import org.pac4j.http.client.direct.ParameterClient;
import org.pac4j.jwt.config.signature.SecretSignatureConfiguration;
import org.pac4j.jwt.config.signature.SignatureConfiguration;
import org.pac4j.jwt.credentials.authenticator.JwtAuthenticator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

import vn.toancauxanh.sso.CasProperties;
import vn.toancauxanh.sso.CustomAuthorizer;


@SpringBootApplication
@EnableScheduling
public class Application extends SpringBootServletInitializer {
	
public static Application app;
	
	@Autowired
	private EntityManager entityManager;
	
	public EntityManager getEntityManager() {
		return entityManager;
	}

	public Application() {
		app = this;
	}
	
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(Application.class);
	}

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Bean
	public Config configPac4j() throws ParseException {
		final SignatureConfiguration secretSignatureConfiguration = new SecretSignatureConfiguration(salt);
//		final SecretEncryptionConfiguration secretEncryptionConfiguration = new SecretEncryptionConfiguration(salt);
		final JwtAuthenticator authenticator = new JwtAuthenticator();
		authenticator.setSignatureConfiguration(secretSignatureConfiguration);
//		authenticator.setEncryptionConfiguration(secretEncryptionConfiguration);
		HeaderClient headerClient = new HeaderClient(HEADER_STRING, TOKEN_PREFIX + " ", authenticator);
		ParameterClient parameterClient = new ParameterClient("token", authenticator);
		parameterClient.setSupportGetRequest(true);
		
		CasConfiguration casConfiguration = new CasConfiguration(casProperties.getCasServerLoginUrl(), CasProtocol.CAS20);
		casConfiguration.setPrefixUrl(casProperties.getCasServerUrl());
		CasClient casClient = new CasClient(casConfiguration);
		casConfiguration.setRenew(true);
		
		final Clients clients = new Clients(casProperties.getAppCallbackUrl(), parameterClient, headerClient, casClient);
		final Config config = new Config(clients);
		config.addAuthorizer("admin", new RequireAnyRoleAuthorizer<>("ROLE_ADMIN"));
		config.addAuthorizer("custom", new CustomAuthorizer());
		return config;
	}
	
	static final long EXPIRATIONTIME = 864_000_000; // 10 days
	static final String TOKEN_PREFIX = "Bearer";
	static final String HEADER_STRING = "Authorization";
	@Value("${salt}")
	private String salt;
	
	@Autowired
	private CasProperties casProperties;
	
	public CasProperties getCasProperties() {
		return casProperties;
	}
}
