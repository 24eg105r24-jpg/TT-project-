package edu.anurag.announcements.repository;

import edu.anurag.announcements.model.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Long> {
    Optional<Department> findByCodeIgnoreCase(String code);
    List<Department> findByActiveTrueOrderByNameAsc();
    boolean existsByCodeIgnoreCase(String code);
}
