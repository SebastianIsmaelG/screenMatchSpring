package com.alura.screenMatch.models;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;
@JsonIgnoreProperties(ignoreUnknown = true)
public record RequestTemporada(
        @JsonAlias("Season") int numero,
        @JsonAlias("Episodes")List<RequestEpisodio> episodios


        ) {
        @Override
        public String toString() {
                return "Temporada " + numero +
                        " : Lista de Episodios " +"\n"+ episodios;
        }
}
