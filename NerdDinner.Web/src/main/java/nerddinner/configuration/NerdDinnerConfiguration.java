package nerddinner.configuration;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties({NerdDinnerConfigurationProperties.class})
public class NerdDinnerConfiguration {

}
