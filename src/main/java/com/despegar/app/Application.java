package com.despegar.app;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application implements ApplicationRunner {

	Logger log = org.slf4j.LoggerFactory.getLogger(Application.class);

	@Value("${lines.max}")
	Integer rows;

	public static void main(String[] args) {
		SpringApplication sapp = new SpringApplication(Application.class);
		sapp.setBannerMode(Banner.Mode.OFF);

		if (args.length != 2) {

			System.out.println("Error en invocacion: Indicar parametros (1. archivo entrada) (2. archivo de salida)");
		} else {
			System.setProperty("log.name", args[1]);
			sapp.run(args);
		}
	}

	@Override
	public void run(ApplicationArguments arg0) {

		int processors = Runtime.getRuntime().availableProcessors();

		LogProcessor logP = new LogProcessor(arg0.getSourceArgs()[0], rows);

		ForkJoinPool fjp = new ForkJoinPool(processors);
		fjp.execute(logP);
		do {
			log.info("Parallelism: " + fjp.getParallelism());
			log.info("Active thread: " + fjp.getActiveThreadCount());
			log.info("Queued task: " + fjp.getQueuedTaskCount());
			log.info("Steal " + fjp.getStealCount());
			try {
				TimeUnit.MILLISECONDS.sleep(500);

			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		} while (!logP.isDone() || !logP.isCancelled() || !logP.isCompletedNormally());
		fjp.shutdown();

	}

}