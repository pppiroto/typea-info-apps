package info.typea.custom_spring_security.security;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;

public class MyPreAuthenticatedProcessingFilter extends
		AbstractPreAuthenticatedProcessingFilter {

	@Override
	protected Object getPreAuthenticatedCredentials(HttpServletRequest arg0) {
		return "password";
	}

	@Override
	protected Object getPreAuthenticatedPrincipal(HttpServletRequest arg0) {
		return "user";
	}
}
