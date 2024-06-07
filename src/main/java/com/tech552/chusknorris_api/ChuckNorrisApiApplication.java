package com.tech552.chusknorris_api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.client.RestTemplate;

import java.util.Timer;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@SpringBootApplication
@EnableScheduling
public class ChuckNorrisApiApplication {
	private static final Logger log = LoggerFactory.getLogger(ChuckNorrisApiApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(ChuckNorrisApiApplication.class, args);
	}

	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder builder) {
		return builder.build();
	}

	@Bean
	@Profile("!test")
	public CommandLineRunner run(RestTemplate restTemplate) throws Exception {
		ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1); //creating a schedule executor service Provides tools to set our times
		scheduler.scheduleAtFixedRate(() -> {
			Joke quote = restTemplate.getForObject("https://api.chucknorris.io/jokes/random", Joke.class); //fetch joke
			log.info(quote.toString()); //log out fetched joke
		}, 0, 5000, TimeUnit.MILLISECONDS); // no delay but a period of 5 and set the unit of time to miliseconds
		return args -> {};
	}

//	@Bean
//	@Profile("!test")
//	@Scheduled(fixedRate = 5000)
//	public void fetchChuckNorrisJoke(RestTemplate restTemplate) {
//		Joke joke = restTemplate.getForObject("https://api.chucknorris.io/jokes/random", Joke.class);
//		log.info(joke.getValue());
//	}



}
