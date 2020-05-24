package studio.secretingredients.consult4me;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;


/**
 * Spring boot application class
 *
 * @author corbandalas - created 12.05.2020
 * @since 0.1.0
 */

@SpringBootApplication
@EnableAspectJAutoProxy
public class Consult4meApplication {

	public static void main(String[] args) {
		SpringApplication.run(Consult4meApplication.class, args);
	}

}
