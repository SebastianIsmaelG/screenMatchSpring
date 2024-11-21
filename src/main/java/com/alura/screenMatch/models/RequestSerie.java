package com.alura.screenMatch.models;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record RequestSerie(
        @JsonAlias("Title") String titulo,
        @JsonAlias("totalSeasons") int totalDeTemporadas,
        @JsonAlias("imdbRating") String evaluacion,
        @JsonAlias("Poster")String poster,
        @JsonAlias("Genre") String genero,
        @JsonAlias("Actors")String actores,
        @JsonAlias("Plot")String sinopsis


) {

    @Override
    public String toString() {
        return "Datos de la serie "+titulo+ " : Sinopsis = "+sinopsis+"| Total de temporadas = "+totalDeTemporadas+"| Evaluacion = "+evaluacion+
                "| Genero = "+genero+"| Actores = "+actores+"| Poster = "+poster;
    }
}
