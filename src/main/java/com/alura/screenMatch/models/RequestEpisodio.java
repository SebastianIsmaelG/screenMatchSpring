package com.alura.screenMatch.models;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record RequestEpisodio(@JsonAlias("Title") String titulo,
                              @JsonAlias("Episode")int numeroEpisodio,
                              @JsonAlias("imdbRating")String evaluacion,
                              @JsonAlias("Released")String fechaLanzamiento

) {

    @Override
    public String toString() {
        return " Episodio " +numeroEpisodio+
                " | Titulo = " +titulo+
                " | Evaluacion =" + evaluacion+
                " | Lanzamiento =" + fechaLanzamiento +"\n";
    }
}
