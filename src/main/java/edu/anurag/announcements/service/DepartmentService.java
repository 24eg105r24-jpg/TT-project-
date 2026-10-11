package edu.anurag.announcements.service;

import edu.anurag.announcements.model.Department;
import edu.anurag.announcements.repository.DepartmentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class DepartmentService {

    private final DepartmentRepository departmentRepository;

    public DepartmentService(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }

    public List<Department> getAllActiveDepartments() {
        return departmentRepository.findByActiveTrueOrderByNameAsc();
    }

    public List<Department> getAllDepartments() {
        return departmentRepository.findAll();
    }

    public Optional<Department> getDepartmentById(Long id) {
        return departmentRepository.findById(id);
    }

    public Optional<Department> getDepartmentByCode(String code) {
        return departmentRepository.findByCodeIgnoreCase(code);
    }

    @Transactional
    public Department saveDepartment(Department department) {
        return departmentRepository.save(department);
    }
}
