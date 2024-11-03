package com.alura.screenMatch.models;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record RequestSerie(
        @JsonAlias("Title") String titulo,
        @JsonAlias("TotalSeasons") int totalDeTemporadas,
        @JsonAlias("imdbRating") String evaluacion) {
}
