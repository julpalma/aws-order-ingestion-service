package com.julpalma.aws.orders;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

//“Data is persisted in PostgreSQL using Docker volumes, so it survives when application restarts.”
//docker compose down -v => -v removes the volume (deletes DB data) => data on DB are lost

//Adding annotation EnableScheduling for the sake of this small project
//Turns on scheduled/background tasks. Scans for @Scheduled methods. Runs them automatically on a timer

@EnableScheduling
@SpringBootApplication
public class AwsOrdersApplication {

	public static void main(String[] args) {

        SpringApplication.run(AwsOrdersApplication.class, args);
	}

}
