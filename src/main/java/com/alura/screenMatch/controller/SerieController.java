package com.alura.screenMatch.controller;

import com.alura.screenMatch.dto.EpisodioDTO;
import com.alura.screenMatch.dto.SerieDTO;
import com.alura.screenMatch.service.SerieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/series")
public class SerieController {

    @Autowired
    private SerieService servicio;

    @GetMapping()
    public List<SerieDTO> obtenerTodasLasSeries(){
        return servicio.obtenerTodasLasSeries();
    }
    @GetMapping("/top5")
    public List<SerieDTO> obtenerTop5(){
        return servicio.obtenerTop5();
    }
    @GetMapping("/lanzamientos")
    public List<SerieDTO> obtenerLanzamientosMasRecientes(){
        return servicio.obtenerLanzamientosMasRecientes();
    }
    @GetMapping("/{id}")
    public SerieDTO obtenerId(@PathVariable Long id){
        return servicio.obtenerPorId(id);
    }
    @GetMapping("/{id}/temporadas/todas")
        public List<EpisodioDTO> obtenerTemporadas(@PathVariable Long id){
            return servicio.obtenerTemporadas(id);
    }

    @GetMapping("/{id}/temporadas/{temporada}")//check
    public List<EpisodioDTO> obtenerTemporadasPorNumero(@PathVariable Long id, @PathVariable Long temporada){
        return servicio.obtenerTemporadasPorNumero(id,temporada);
    }

    @GetMapping("/categoria/{nombreGenero}")
    public List<SerieDTO> obtenerSeriesPorCategoria(@PathVariable String nombreGenero){
        return servicio.obtenerSeriesPorCategoria(nombreGenero);
    }


}


