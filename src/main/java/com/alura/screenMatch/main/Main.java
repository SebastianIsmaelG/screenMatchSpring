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
    private List<Serie> series;
    private Optional<Serie> serieBuscada;

    public Main(SerieRepository repository) {
        this.repositorio = repository;
    }

    public void menu(){


        var response = true;
        String option;

        while (response){
            System.out.println("""
                    -----------------------------------------
                    1 - Busqueda y registro de series Online
                    2 - Mostrar top 5 de serie registradas
                    3 - Mostrar todas las series registradas
                    4 - Busqueda de serie registradas
                    5 - Busqueda de episodio registrados
                    6 - Busqueda de serie por categoria
                    7 - Filtrar series por temporadas y evaluacion
                    8 - Mostrar top 5 episodios por serie
                    
                    
                    0- Salir
                    -----------------------------------------
                    """);
            option = write.nextLine();
            //write.nextLine();

            if (option.length() == 1 && Character.isDigit(option.charAt(0))){
                //numeroIngresado = Integer.parseInt(option);

                switch (option){
                    case "1":
                        try {
                            buscarSerie();
                        }catch (NullPointerException e){
                            break;
                        }
                        break;
                    case "3":
                        mostrarSeries();
                        break;
                    case "4":
                        buscarSeriePorTitulo();
                        break;
                    case "5":
                        buscarEpisodio();
                        break;
                    case "2":
                        buscarTop5();
                        break;
                    case "6":
                        buscarPorCategoria();
                        break;
                    case "7":
                        filtrarSeriePorTemporadaYEvaluacion();
                        break;
                    case "8":
                        buscarTop5episodios();
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

    private void buscarTop5episodios() {
        buscarSeriePorTitulo();
        if (serieBuscada.isPresent()){
            Serie serie = serieBuscada.get();
            List<Episodio> topEpisodios = repositorio.top5episodios(serie);
            topEpisodios.forEach(e ->
                    System.out.printf("Episodio: %s | Temporada: %s | Titulo %s " + " | Evaluacion %s \n",
                            e.getNumeroEpisodio(),e.getNumeroTemporada(),e.getTitulo(),e.getEvaluacion())

        );}
    }

    private void filtrarSeriePorTemporadaYEvaluacion() {
        boolean cacheo;
        int totalTemporadas;
        double evaluacionTemporadas;

        do {
            try {
                cacheo = false;

                System.out.println("Numero de temporadas: ");
                totalTemporadas = write.nextInt();
                //write.nextLine();

                System.out.println("Evaluacion : ");
                evaluacionTemporadas = write.nextDouble();
                //write.nextLine();

                List<Serie> filtroSeries = repositorio.seriesPorTemporadaYEvaluacion(totalTemporadas,evaluacionTemporadas);
                System.out.println("Resultados del filtro: ");
                filtroSeries.forEach(s -> System.out.println(s.getTitulo()+ " - evaluacion: "+s.getEvaluacion()));
                write.nextLine();

            } catch (InputMismatchException e) {
                System.out.println("Formato incorrecto,reintente (Ejemplo: 7,0)");
                write.next();
                cacheo = true;

            }
        }while (cacheo); //true

    }

    private void buscarPorCategoria() {
        System.out.println("Que categoria buscas: ");
        var genero = write.nextLine();
        var categoria = Categoria.fromESP(genero);
        List<Serie> seriesPorCategoria = repositorio.findByGenero(categoria);
        System.out.println("Series de la categoria '"+genero+"':");
        seriesPorCategoria.stream().map(t -> t.getTitulo()).forEach(System.out::println);

    }

    private void buscarTop5() {
        List<Serie> topSeries = repositorio.findTop5ByOrderByEvaluacionDesc();
        topSeries.forEach(s-> System.out.println("Serie: " +s.getTitulo()+ " - Evaluacion: " +s.getEvaluacion()));
    }

    private void buscarSeriePorTitulo() {
        System.out.println("Ingresa el nombre de la serie que deseas buscar en la base de datos");
        var nombreSerie = write.nextLine();
        serieBuscada = repositorio.findByTituloContainsIgnoreCase(nombreSerie);
        if (serieBuscada.isPresent()){
            System.out.println("Resultado de la busqueda de "+ serieBuscada.get());
        }else {
            System.out.println("La busqueda de " + nombreSerie + " no obtuvo resultados, revisa los datos ingresados");
        }
    }

    private RequestSerie DatosSerie() {
        System.out.println("Escriba el nombre de la serie");
        var nombreSerie = write.nextLine();
        var json = consumoApi.obtenerDatos(URL+nombreSerie.replace(" ", "+")+API);
        RequestSerie datos = convertir.obtenerDatos(json, RequestSerie.class);

            if (datos.titulo() != null){
                System.out.println("Entrada datos : " +datos);
            }else {
                System.out.println("La búsqueda de "+ nombreSerie+" no obtuvo resultados, revisa los datos ingresados");
            }

        return datos;

    }
    private void buscarEpisodio(){
        //RequestSerie datosSerie = DatosSerie();
        mostrarSeries();
        System.out.println("Que serie desea ver");
        var ingresoSerie = write.nextLine();

        Optional<Serie> serie = series.stream().filter(s -> s.getTitulo().toLowerCase().contains(ingresoSerie.toLowerCase()))
                        .findFirst();
        if (serie.isPresent()){
            var serienEncontrada = serie.get();
            List<RequestTemporada> temporadas = new ArrayList<>();

            for (int i = 1; i <= serienEncontrada.getTotalDeTemporadas(); i++) {
                var json = consumoApi.obtenerDatos( URL+ serienEncontrada.getTitulo().replace(" ", "+") + "&season=" + i + API);
                RequestTemporada datosTemporada = convertir.obtenerDatos(json, RequestTemporada.class);
                temporadas.add(datosTemporada);
            }
            temporadas.forEach(System.out::println);

            List<Episodio> episodios = temporadas.stream()
                    .flatMap(d -> d.episodios().stream().map(e -> new Episodio(d.numero(),e)))
                    .collect(Collectors.toList());

            serienEncontrada.setEpisodios(episodios);
            repositorio.save(serienEncontrada);

        }else {
            System.out.println("La búsqueda de "+ ingresoSerie+" no obtuvo resultados, revisa los datos ingresados");
        }



    }
    private void buscarSerie() {
        RequestSerie datos = DatosSerie();
        Serie serie = new Serie(datos);
        repositorio.save(serie);
        //datosSeries.add(datos);
        System.out.println(datos);
    }
    private void mostrarSeries() {
        /* datosSeries.forEach(System.out::println);
        List<Serie> series = new ArrayList<>();
        series = datosSeries.stream().map(Serie::new).toList();
        series.stream().sorted(Comparator.comparing(Serie::getGenero)).forEach(System.out::println); */
        series = repositorio.findAll(); // entity class jpa
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
