package com.julpalma.was_orders;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//“Data is persisted in PostgreSQL using Docker volumes, so it survives when application restarts.”
//docker compose down -v => -v removes the volume (deletes DB data) => data on DB are lost

@SpringBootApplication
public class WasOrdersApplication {

	public static void main(String[] args) {

        SpringApplication.run(WasOrdersApplication.class, args);
	}

}
