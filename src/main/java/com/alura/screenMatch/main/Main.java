package com.alura.screenMatch.main;

import com.alura.screenMatch.models.RequestEpisodio;
import com.alura.screenMatch.models.RequestSerie;
import com.alura.screenMatch.models.RequestTemporada;
import com.alura.screenMatch.service.ConvertData;
import com.alura.screenMatch.service.RequestApi;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    Scanner leer = new Scanner(System.in);
    RequestApi consumoApi = new RequestApi();
    ConvertData convertir = new ConvertData();
    List<RequestTemporada> temporadas = new ArrayList<>();

    public void menu(){
        var consumoApi = new RequestApi();

        System.out.println("Escriba el nombre de la serie");
        var nombreSerie = leer.nextLine();
        var json = consumoApi.obtenerDatos("http://www.omdbapi.com/?t="+nombreSerie.replace(" ", "+")+"&apikey=36e4bf1a");
        var datos = convertir.obtenerDatos(json, RequestSerie.class);
        //System.out.println(datos);

        for (int i = 1; i <= datos.totalDeTemporadas() ; i++) {
            json = consumoApi.obtenerDatos("http://www.omdbapi.com/?t="+nombreSerie.replace(" ", "+")+"&Season="+i+"&apikey=36e4bf1a");

            var datosTemporadas = convertir.obtenerDatos(json, RequestTemporada.class);
            temporadas.add(datosTemporadas);

        }
        //temporadas.forEach(System.out::println);

        for (int i = 0; i < datos.totalDeTemporadas() ; i++) {
            List<RequestEpisodio> episodiosTemporada = temporadas.get(i).episodios();
            for (int j = 0; j < episodiosTemporada.size() ; j++) {
                System.out.println(episodiosTemporada.get(j).titulo());
            }
        }

    }
}
