/*
 * Copyright 2017 the original author or authors.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *          http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package io.spring.cfschedulerdemo;

import io.pivotal.reactor.scheduler.ReactorSchedulerClient;
import org.cloudfoundry.reactor.DefaultConnectionContext;
import org.cloudfoundry.reactor.tokenprovider.PasswordGrantTokenProvider;
import reactor.core.publisher.Mono;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CFServiceConfiguration {

	@Bean
	public DefaultConnectionContext getContext() {
		return DefaultConnectionContext.builder()
				.apiHost("myhost")
				.skipSslValidation(true)
				.build();
	}

	@Bean
	public PasswordGrantTokenProvider passwordGrantTokenProvider() {
		return PasswordGrantTokenProvider.builder()
				.password("password")
				.username("admin")
				.build();
	}

	@Bean
	public ReactorSchedulerClient reactorSchedulerClient(DefaultConnectionContext context, PasswordGrantTokenProvider passwordGrantTokenProvider) {
		return ReactorSchedulerClient.builder()
				.connectionContext(context)
				.tokenProvider(passwordGrantTokenProvider)
				.root(Mono.just("myhost"))
				.build();
	}


}
