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
import io.pivotal.scheduler.v1.jobs.CreateJobRequest;
import io.pivotal.scheduler.v1.jobs.CreateJobResponse;
import io.pivotal.scheduler.v1.jobs.GetJobRequest;
import io.pivotal.scheduler.v1.jobs.GetJobResponse;
import io.pivotal.scheduler.v1.jobs.ListJobsRequest;
import io.pivotal.scheduler.v1.jobs.ListJobsResponse;
import org.cloudfoundry.reactor.DefaultConnectionContext;
import org.cloudfoundry.reactor.tokenprovider.PasswordGrantTokenProvider;
import reactor.core.publisher.Mono;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/scheduler")
public class SchedulerController {

	@Autowired
	ReactorSchedulerClient client;

	@GetMapping(path = "testList")
	public Mono<ListJobsResponse> list() {
		return client.
				jobs().
				list(ListJobsRequest.builder().
						page(0).
						perPage(2).
						spaceId("e1ae83e3-319e-47b4-aa60-a1f5dfc2dc2c").
						build());
	}

	@GetMapping(path = "testCreate/{jobName}")
	public Mono<CreateJobResponse> create(@PathVariable("jobName") String jobName) {
		return client.jobs().create(CreateJobRequest.builder()
				.applicationId("taskrunme")
				.command("test-command")
				.name(jobName)
				.build());
	}

	@GetMapping(path = "testGet/{jobName}")
	public Mono<GetJobResponse> getJob(@PathVariable("jobName") String jobName) {
		System.out.println(jobName);
		return client.jobs().
				get(GetJobRequest.
						builder().
						jobId(jobName).
						build()).
				log();
	}
}
