package com.example.sdhzbozi.common.repositories;

import com.example.sdhzbozi.common.model.Event;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventRepository extends JpaRepository<Event, Integer> {
}
