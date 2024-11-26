package com.alura.screenMatch.main;

import com.alura.screenMatch.models.*;
import com.alura.screenMatch.repository.SerieRepository;
import com.alura.screenMatch.service.ConvertData;
import com.alura.screenMatch.service.RequestApi;

import java.util.*;
import java.util.stream.Collectors;

public class Main {
    //VARIABLES API
    private final String URL = "http://www.omdbapi.com/?t=";
    private final String API = "&apikey=36e4bf1a";
    Scanner write = new Scanner(System.in);
    RequestApi consumoApi = new RequestApi();
    ConvertData convertir = new ConvertData();

    private List<RequestTemporada> temporadas = new ArrayList<>();
    private List<RequestSerie> datosSeries = new ArrayList<>();
    private SerieRepository repositorio;

    public Main(SerieRepository repository) {
        this.repositorio = repository;
    }

    public void menu(){


        var response = true;
        var numeroIngresado = 0;
        String option;

        while (response){
            System.out.println("""
                    -----------------------------------------
                    1 - Busqueda de series
                    2 - Busqueda de episodios
                    3 - Mostrar series buscadas
                    
                    0- Salir
                    -----------------------------------------
                    """);
            option = write.nextLine();
            //write.nextLine();

            if (option.length() == 1 && Character.isDigit(option.charAt(0))){
                numeroIngresado = Integer.parseInt(option);

                switch (option){
                    case "1":
                        buscarSerie();
                        break;
                    case "2":
                        buscarEpisodio();
                        break;
                    case "3":
                        mostrarSeries();
                        break;
                    case "0":
                        System.out.println("Saliendo de la aplicacion");
                        response = false;
                        break;
                    default:
                        System.out.println("Opcion invalida");
                        break;
                }
            }else {
                System.out.println("Ingrese una opcion valida");

            }



        }
        write.close();


    }

    private RequestSerie DatosSerie() {
        System.out.println("Escriba el nombre de la serie");
        var nombreSerie = write.nextLine();
        var json = consumoApi.obtenerDatos(URL+nombreSerie.replace(" ", "+")+API);
        RequestSerie datos = convertir.obtenerDatos(json, RequestSerie.class);
        return datos;
    }
    private void buscarEpisodio(){
        RequestSerie datosSerie = DatosSerie();
        List<RequestTemporada> temporadas = new ArrayList<>();

        for (int i = 1; i <= datosSerie.totalDeTemporadas(); i++) {
            var json = consumoApi.obtenerDatos( URL+ datosSerie.titulo().replace(" ", "+") + "&season=" + i + API);
            RequestTemporada datosTemporada = convertir.obtenerDatos(json, RequestTemporada.class);
            temporadas.add(datosTemporada);
        }
        temporadas.forEach(System.out::println);

    }
    private void buscarSerie() {
        RequestSerie datos = DatosSerie();
        Serie serie = new Serie(datos);
        repositorio.save(serie);
        //datosSeries.add(datos);
        System.out.println(datos);
    }
    private void mostrarSeries() {
        //datosSeries.forEach(System.out::println);
        /*List<Serie> series = new ArrayList<>();
        series = datosSeries.stream().map(Serie::new).toList();
        series.stream().sorted(Comparator.comparing(Serie::getGenero)).forEach(System.out::println); */
        List<Serie> series = repositorio.findAll(); // entity class jpa
        series.stream().sorted(Comparator.comparing(Serie::getGenero)).forEach(System.out::println);
    }

   /*
        List<RequestEpisodio> datosEpisodios = temporadas.stream().flatMap(t -> t.episodios().stream()).collect(Collectors.toList());

        System.out.println("5 mejores episodios");
        datosEpisodios.stream().filter(e -> !e.evaluacion().equalsIgnoreCase("N/A")).sorted(Comparator.comparing(RequestEpisodio::evaluacion).reversed()).limit(5).forEach(System.out::println);

        List<Episodio> episodios = temporadas.stream().flatMap(t -> t.episodios().stream().map(d -> new Episodio(t.numero(),d))).collect(Collectors.toList());
        //episodios.forEach(System.out::println);

        //Mapeado y collectors.groupingBy
        Map<Integer, Double> evalPorTemporada = episodios.stream().filter(e -> e.getEvaluacion() > 0.0).collect(Collectors.groupingBy(
                Episodio::getNumeroTemporada,Collectors.averagingDouble(Episodio::getEvaluacion)
        ));
        System.out.println(evalPorTemporada);
        //Datos estadisticos del total del stream
        DoubleSummaryStatistics est = episodios.stream().filter(e -> e.getEvaluacion() > 0.0).collect(Collectors.summarizingDouble(Episodio::getEvaluacion));
        System.out.println("Media evaluaciones: "+est.getAverage());
        System.out.println("Episodio peor evaluado: "+est.getMin());
        System.out.println("Episodio mejor evaluado: "+est.getMax());
        System.out.println("Suma evaluaciones: "+est.getSum());
        */

}
