package studio.secretingredients.consult4me;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.web.filter.CommonsRequestLoggingFilter;


/**
 * Spring boot application class
 *
 * @author corbandalas - created 12.05.2020
 * @since 0.1.0
 */

@SpringBootApplication
@EnableAspectJAutoProxy
public class Consult4meApplication {

	@Bean
	public CommonsRequestLoggingFilter requestLoggingFilter() {
		CommonsRequestLoggingFilter loggingFilter = new CommonsRequestLoggingFilter();
		loggingFilter.setIncludeClientInfo(true);
		loggingFilter.setIncludeQueryString(true);
		loggingFilter.setIncludePayload(true);
		loggingFilter.setMaxPayloadLength(64000);
		return loggingFilter;
	}

	public static void main(String[] args) {
		SpringApplication.run(Consult4meApplication.class, args);
	}

}
