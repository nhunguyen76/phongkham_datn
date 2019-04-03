package vn.toancauxanh.sso;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class CasProperties {
	
	@Value("${cas.server.url}")
	private String casServerUrl;
	
	@Value("${cas.server.login_url}")
	private String casServerLoginUrl;
	
	@Value("${cas.server.logout_url}")
	private String casServerLogoutUrl;
	
	@Value("${cas.client.url}")
	private String appServerUrl;
	
	@Value("${cas.client.callback}")
	private String appCallbackUrl;
	
	@Value("${app.login.url}")
	private String appLoginUrl;
	
	@Value("${app.logout.url}")
	private String appLogoutUrl;
	
	@Value("${app.frontend.url}")
	private String frontendUrl;
	
	public String getCasServerUrl() {
		return casServerUrl;
	}
	
	public void setCasServerUrl(String casServerUrl) {
		this.casServerUrl = casServerUrl;
	}
	
	public String getCasServerLoginUrl() {
		return casServerLoginUrl;
	}
	
	public void setCasServerLoginUrl(String casServerLoginUrl) {
		this.casServerLoginUrl = casServerLoginUrl;
	}
	
	public String getCasServerLogoutUrl() {
		return casServerLogoutUrl;
	}
	
	public void setCasServerLogoutUrl(String casServerLogoutUrl) {
		this.casServerLogoutUrl = casServerLogoutUrl;
	}
	
	public String getAppServerUrl() {
		return appServerUrl;
	}
	
	public void setAppServerUrl(String appServerUrl) {
		this.appServerUrl = appServerUrl;
	}
	
	public String getAppLoginUrl() {
		return appLoginUrl;
	}
	
	public void setAppLoginUrl(String appLoginUrl) {
		this.appLoginUrl = appLoginUrl;
	}
	
	public String getAppLogoutUrl() {
		return appLogoutUrl;
	}
	
	public void setAppLogoutUrl(String appLogoutUrl) {
		this.appLogoutUrl = appLogoutUrl;
	}
	
	public String getAppCallbackUrl() {
		return appCallbackUrl;
	}
	
	public void setAppCallbackUrl(String appCallbackUrl) {
		this.appCallbackUrl = appCallbackUrl;
	}
	
	public String getFrontendUrl() {
		return frontendUrl;
	}
}
