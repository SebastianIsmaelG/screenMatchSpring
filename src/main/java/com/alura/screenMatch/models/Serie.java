package com.alura.screenMatch.models;

import jakarta.persistence.*;

import java.lang.reflect.Type;
import java.util.List;
import java.util.OptionalDouble;

@Entity
@Table(name = "Series")
public class Serie {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long Id;
    @Column(unique = true)                                  private String titulo;
                                                            private int totalDeTemporadas;
                                                            private String evaluacion;
                                                            private String poster;
    @Enumerated(EnumType.STRING)                            private Categoria genero;
                                                            private String actores;
                                                            private String sinopsis;
    @Transient                                              private List<Episodio> episodios;
    //constructor predeterminado jpa
    public Serie(){}

    public Serie(RequestSerie requestSerie){
        this.titulo = requestSerie.titulo();
        this.totalDeTemporadas = requestSerie.totalDeTemporadas();
        this.evaluacion = String.valueOf(OptionalDouble.of(Double.valueOf(requestSerie.evaluacion())).orElse(0));
        this.poster = requestSerie.poster();
        this.genero = Categoria.fromString(requestSerie.genero().split(",")[0].trim());
        this.actores = requestSerie.actores();
        this.sinopsis = requestSerie.sinopsis();
    }

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public String getSinopsis() {
        return sinopsis;
    }

    public void setSinopsis(String sinopsis) {
        this.sinopsis = sinopsis;
    }

    public String getActores() {
        return actores;
    }

    public void setActores(String actores) {
        this.actores = actores;
    }

    public Categoria getGenero() {
        return genero;
    }

    public void setGenero(Categoria genero) {
        this.genero = genero;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public String getEvaluacion() {
        return evaluacion;
    }

    public void setEvaluacion(String evaluacion) {
        this.evaluacion = evaluacion;
    }

    public int getTotalDeTemporadas() {
        return totalDeTemporadas;
    }

    public void setTotalDeTemporadas(int totalDeTemporadas) {
        this.totalDeTemporadas = totalDeTemporadas;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    @Override
    public String toString() {
        return "Datos de la serie "+titulo+ " : Sinopsis = "+sinopsis+"| Total de temporadas = "+totalDeTemporadas+"| Evaluacion = "+evaluacion+
                "| Genero = "+genero+"| Actores = "+actores+"| Poster = "+poster;
    }
}

