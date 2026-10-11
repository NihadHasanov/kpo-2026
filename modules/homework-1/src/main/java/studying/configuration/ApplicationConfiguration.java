package studying.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import studying.fleet.Fleet;
import studying.inspection.DefaultServiceCenter;
import studying.inspection.ServiceCenter;

/** Spring wiring of the fleet application. */
@Configuration(proxyBeanMethods = false)
public final class ApplicationConfiguration {

    @Bean
    ServiceCenter serviceCenter() {
        return new DefaultServiceCenter();
    }

    @Bean
    Fleet fleet(final ServiceCenter serviceCenter) {
        return new Fleet(serviceCenter);
    }
}
