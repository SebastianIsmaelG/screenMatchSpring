package com.alura.screenMatch;


import com.alura.screenMatch.main.BusquedaTiempo;
import com.alura.screenMatch.main.EjemploStreams;
import com.alura.screenMatch.main.Main;
import com.alura.screenMatch.repository.SerieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class ScreenMatchApplication implements CommandLineRunner  {
	//inyeccion
	@Autowired private SerieRepository repository;

	public static void main(String[] args) {
		SpringApplication.run(ScreenMatchApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		Main main = new Main(repository);
		main.menu();

		//BusquedaTiempo main = new BusquedaTiempo();
		//main.menu();
		//EjemploStreams ejemploStreams = new EjemploStreams();
		//ejemploStreams.muestraEjemplo();



	}
}
