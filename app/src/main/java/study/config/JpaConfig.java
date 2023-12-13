package study.config;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.web.client.RestTemplate;

import springfox.documentation.swagger2.annotations.EnableSwagger2;
import study.common.CustomAuditorAware;

@Configuration
@EnableJpaAuditing(auditorAwareRef = "auditorAware")
@EnableSwagger2
@PropertySource(value = "classpath:application.properties")
public class JpaConfig {
	
	@Bean
	public AuditorAware<String> auditorAware() {
		return new CustomAuditorAware();
	}

	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder builder) {
		return builder.build();
	}
	
	
}
