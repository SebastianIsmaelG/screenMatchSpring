package com.alura.screenMatch.service;

import com.alura.screenMatch.dto.EpisodioDTO;
import com.alura.screenMatch.dto.SerieDTO;
import com.alura.screenMatch.models.Categoria;
import com.alura.screenMatch.models.Serie;
import com.alura.screenMatch.repository.SerieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SerieService {
    @Autowired
    private SerieRepository repository;

    public List<SerieDTO> obtenerTodasLasSeries(){
        return convierteDatos(repository.findAll());
    }
    public List<SerieDTO> obtenerLanzamientosMasRecientes(){
        return convierteDatos(repository.lanzamientoMasRecientes());
    }
    public List<SerieDTO> obtenerTop5() {
        return convierteDatos(repository.findTop5ByOrderByEvaluacionDesc());
    }
    public List<SerieDTO> convierteDatos(List<Serie> serie){
        return serie.stream().map(s -> new SerieDTO(s.getId(),s.getTitulo(),s.getTotalDeTemporadas(),s.getEvaluacion(),
                s.getPoster(),s.getGenero(),s.getActores(),s.getSinopsis())).collect(Collectors.toList());
    }

    public SerieDTO obtenerPorId(Long id) {
        Optional<Serie> serie = repository.findAllById(id);
        if (serie.isPresent()){
            Serie s = serie.get();
            return new SerieDTO(s.getId(),s.getTitulo(),s.getTotalDeTemporadas(),s.getEvaluacion(),
                    s.getPoster(),s.getGenero(),s.getActores(),s.getSinopsis());
        }
        return null;
    }

    public List<EpisodioDTO> obtenerTemporadas(Long id) {
        Optional<Serie> serie = repository.findAllById(id);
        if (serie.isPresent()){
            Serie s = serie.get();
            return s.getEpisodios().stream().map(e-> new EpisodioDTO(e.getNumeroTemporada(), e.getTitulo(),e.getNumeroEpisodio())).collect(Collectors.toList());
        }
        return null;
    }

    public List<EpisodioDTO> obtenerTemporadasPorNumero(Long id, Long temporada) {
        return repository.obtenerTemporadasPorNumero(id,temporada).stream().map(
                e -> new EpisodioDTO(e.getNumeroTemporada(), e.getTitulo(),e.getNumeroEpisodio())).collect(Collectors.toList());
    }

    public List<SerieDTO> obtenerSeriesPorCategoria(String nombreGenero) {
        Categoria categoria = Categoria.fromESP(nombreGenero);
        return convierteDatos(repository.findByGenero(categoria));
    }
}
