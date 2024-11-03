package com.alura.screenMatch;

import com.alura.screenMatch.models.RequestSerie;
import com.alura.screenMatch.service.ConvertData;
import com.alura.screenMatch.service.RequestApi;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ScreenMatchApplication implements CommandLineRunner  {

	public static void main(String[] args) {
		SpringApplication.run(ScreenMatchApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		var consumoApi = new RequestApi();
		var json = consumoApi.obtenerDatos("http://www.omdbapi.com/?t=godzilla&apikey=36e4bf1a");
		ConvertData convertir = new ConvertData();
		var datos = convertir.obtenerDatos(json, RequestSerie.class);
		System.out.println(datos);
	}
}
