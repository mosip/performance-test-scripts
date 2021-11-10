package io.mosip.websub.perf.utility.filter;
import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpMethod;
import org.springframework.web.filter.OncePerRequestFilter;



/**
 * This filter is used for handle intent verification request by hub after subscribe and unsubscribe
 * operations with help of metadata collected by {@link PreAuthenticateContentAndVerifyIntent} and
 * {@link IntentVerificationConfig} class.
 * 
 * @author Urvil Joshi
 *
 */
public class IntentVerificationFilter extends OncePerRequestFilter {
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {		
	
		if (request.getRequestURI().contains("/callback") &&  request.getMethod().equals(HttpMethod.GET.name())) {
				response.setStatus(HttpServletResponse.SC_ACCEPTED);
		}else {
			filterChain.doFilter(request, response);
		}
	}

}
