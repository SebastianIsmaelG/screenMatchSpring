package com.alura.screenMatch.main;

import com.alura.screenMatch.models.Episodio;
import com.alura.screenMatch.models.RequestSerie;
import com.alura.screenMatch.models.RequestTemporada;
import com.alura.screenMatch.service.ConvertData;
import com.alura.screenMatch.service.RequestApi;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class BusquedaTiempo {
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

        for (int i = 1; i <= datos.totalDeTemporadas() ; i++) {
            json = consumoApi.obtenerDatos("http://www.omdbapi.com/?t="+nombreSerie.replace(" ", "+")+"&Season="+i+"&apikey=36e4bf1a");

            var datosTemporadas = convertir.obtenerDatos(json, RequestTemporada.class);
            temporadas.add(datosTemporadas);

        }
        List<Episodio> episodios = temporadas.stream().flatMap(t -> t.episodios().stream().map(d -> new Episodio(t.numero(),d))).collect(Collectors.toList());
        //tenemos los datos de las temporadas ahora preguntamos por el tiempo
        System.out.println("Dime desde que año deseas ver los datos de las temporadas");
        var fecha = leer.nextInt();
        leer.nextLine();
        LocalDate fechaBusqueda = LocalDate.of(fecha, 1,1);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        episodios.stream().filter(e -> e.getFechaDeLanzamiento().isAfter(fechaBusqueda)).forEach(e-> System.out.println(
                "Temporada: " + e.getNumeroTemporada()+", Cantidad de Episodios: "+e.getNumeroEpisodio()+", Fecha de lanzamiento: "+e.getFechaDeLanzamiento().format(formatter)
        ));

    }
}
