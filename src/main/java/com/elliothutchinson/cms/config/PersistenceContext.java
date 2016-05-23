package com.elliothutchinson.cms.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.auditing.DateTimeProvider;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import com.elliothutchinson.cms.service.AuditingDateTimeProvider;
import com.elliothutchinson.cms.service.DateTimeService;

@Configuration
@EnableJpaAuditing(dateTimeProviderRef="dateTimeProvider")
public class PersistenceContext {
	
	@Bean
	DateTimeProvider dateTimeProvider(DateTimeService dateTimeService) {
		return new AuditingDateTimeProvider(dateTimeService);
	}
}
