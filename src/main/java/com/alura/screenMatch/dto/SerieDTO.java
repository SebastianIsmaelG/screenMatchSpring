package com.alura.screenMatch.dto;

import com.alura.screenMatch.models.Categoria;

public record SerieDTO(
        Long id,
        String titulo,
        int totalDeTemporadas,
        String evaluacion,
        String poster,
        Categoria genero,
        String actores,
        String sinopsis
) {
}
