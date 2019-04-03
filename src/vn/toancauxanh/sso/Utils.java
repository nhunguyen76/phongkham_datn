package vn.toancauxanh.sso;

import vn.toancauxanh.Application;

public class Utils {
	
	public static String getLogoutCasUrl() {
		CasProperties casProperties = Application.app.getCasProperties();
		return casProperties.getCasServerLogoutUrl() + casProperties.getAppServerUrl() + casProperties.getAppLoginUrl();
	}
}
