package com.alura.screenMatch.repository;

import com.alura.screenMatch.models.Serie;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SerieRepository extends JpaRepository<Serie,Long> {
}
