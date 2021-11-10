package io.mosip.websub.perf.utility.config;
import javax.servlet.Filter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

import io.mosip.kernel.core.websub.spi.SubscriptionClient;
import io.mosip.kernel.core.websub.spi.SubscriptionExtendedClient;
import io.mosip.websub.perf.utility.filter.IntentVerificationFilter;


/**
 * This class consist all the general and common configurations for this api.
 * 
 * @author Urvil Joshi
 *
 */
@Configuration
@EnableAspectJAutoProxy
public class WebSubUtilityConfig {


	

	@Bean(name = "intentVerificationFilterBean")
	public FilterRegistrationBean<Filter> registerIntentVerificationFilterFilterBean() {
		FilterRegistrationBean<Filter> reqResFilter = new FilterRegistrationBean<>();
		reqResFilter.setFilter(registerIntentVerificationFilter());
		return reqResFilter;
	}

	@Bean
	public IntentVerificationFilter registerIntentVerificationFilter() {
		return new IntentVerificationFilter();
	}

}
