package com.example.sdhzbozi.common.repositories;

import com.example.sdhzbozi.common.model.News;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NewsRepository extends JpaRepository<News, Integer> {
}
