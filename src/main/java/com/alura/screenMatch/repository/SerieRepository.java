package com.alura.screenMatch.repository;

import com.alura.screenMatch.dto.EpisodioDTO;
import com.alura.screenMatch.models.Categoria;
import com.alura.screenMatch.models.Episodio;
import com.alura.screenMatch.models.Serie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface SerieRepository extends JpaRepository<Serie,Long> {

   Optional<Serie> findByTituloContainsIgnoreCase(String nombreSerie);//esto era para el titulo null parece SQL xde

   List<Serie> findTop5ByOrderByEvaluacionDesc();

   List<Serie> findByGenero(Categoria categoria);
   //sql native
   @Query (value = "SELECT * FROM series WHERE total_de_temporadas <= ? AND evaluacion >= '?'", nativeQuery = true)
   List<Serie> seriesPorTemporadaYEvaluacion(Integer totalTemporadas, Double evaluacionTemporadas);

   @Query (value = "SELECT e FROM Serie s JOIN s.episodios e WHERE s = :serie ORDER BY e.evaluacion DESC LIMIT 5")
   List<Episodio> top5episodios(Serie serie);

   @Query(value = "SELECT s FROM Serie s "+" JOIN s.episodios e "+"GROUP BY s" + " ORDER BY MAX (e.fechaDeLanzamiento) DESC LIMIT 5")
   List<Serie> lanzamientoMasRecientes();

   Optional<Serie> findAllById(Long id);

   @Query("SELECT e FROM Serie s JOIN s.episodios e WHERE s.id = :id AND e.numeroTemporada= :temporada")
   List<Episodio> obtenerTemporadasPorNumero(Long id, Long temporada);
}
