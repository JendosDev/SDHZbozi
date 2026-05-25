package com.example.sdhzbozi.admin.controller;

import com.example.sdhzbozi.admin.service.AdminService;
import com.example.sdhzbozi.common.dto.DepartmentDTO;
import com.example.sdhzbozi.common.dto.request.DepartmentDTOForm;
import com.example.sdhzbozi.common.repositories.DepartmentRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class DepartmentController {

    private final AdminService adminService;

    public DepartmentController(AdminService adminService) {
        this.adminService = adminService;
    }

    @GetMapping("/departments")
    public List<DepartmentDTO> getDepartments () {
        return adminService.getDepartments();
    }

    @PostMapping("/departments")
    public ResponseEntity<DepartmentDTO> postDepartment (
            @RequestBody DepartmentDTOForm dto
    ) {
        return ResponseEntity.ok(adminService.postDepartment(dto));
    }

}
