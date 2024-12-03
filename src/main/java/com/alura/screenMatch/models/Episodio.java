package com.alura.screenMatch.models;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

@Entity
@Table(name = "episodios")
public class Episodio {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long Id;
                                                            private Integer numeroTemporada;
                                                            private String titulo;
                                                            private Integer numeroEpisodio;
                                                            private Double evaluacion;
                                                            private LocalDate fechaDeLanzamiento;
    @ManyToOne @JoinColumn(name = "serie_id")               private Serie serie;

    public Episodio(){

    }
    public Episodio(int numero, RequestEpisodio d) {
        this.numeroTemporada = numero;
        this.titulo = d.titulo();
        this.numeroEpisodio = d.numeroEpisodio();
        try{
            this.evaluacion = Double.valueOf(d.evaluacion());
        } catch (NumberFormatException e) {
            this.evaluacion = 0.0;
        }

        try {
            this.fechaDeLanzamiento = LocalDate.parse(d.fechaLanzamiento());
        }catch (DateTimeParseException e){
            this.fechaDeLanzamiento = LocalDate.now();//arreglo temporal
        }
    }

    public LocalDate getFechaDeLanzamiento() {
        return fechaDeLanzamiento;
    }

    public void setFechaDeLanzamiento(LocalDate fechaDeLanzamiento) {
        this.fechaDeLanzamiento = fechaDeLanzamiento;
    }

    public Double getEvaluacion() {
        return evaluacion;
    }

    public void setEvaluacion(Double evaluacion) {
        this.evaluacion = evaluacion;
    }

    public Integer getNumeroEpisodio() {
        return numeroEpisodio;
    }

    public void setNumeroEpisodio(Integer numeroEpisodio) {
        this.numeroEpisodio = numeroEpisodio;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Integer getNumeroTemporada() {
        return numeroTemporada;
    }

    public void setNumeroTemporada(Integer numeroTemporada) {
        this.numeroTemporada = numeroTemporada;
    }

    public Serie getSerie() {
        return serie;
    }

    public void setSerie(Serie serie) {
        this.serie = serie;
    }

    @Override
    public String toString() {
        return " numeroTemporada=" + numeroTemporada +
                ", titulo='" + titulo + '\'' +
                ", numeroEpisodio=" + numeroEpisodio +
                ", evaluacion=" + evaluacion +
                ", fechaDeLanzamiento=" + fechaDeLanzamiento +" ";
    }
}
