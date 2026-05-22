package com.example.sdhzbozi.common.repositories;

import com.example.sdhzbozi.common.model.Department;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartmentRepository extends JpaRepository<Department, Integer> {
}
