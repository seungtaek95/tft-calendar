package com.calendar.tft;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class TftCalendarSchedulerApplication {
	public static void main(String[] args) {
		SpringApplication.run(TftCalendarSchedulerApplication.class, args);
	}
}
