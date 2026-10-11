package edu.anurag.announcements.repository;

import edu.anurag.announcements.model.Department;
import edu.anurag.announcements.model.Role;
import edu.anurag.announcements.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmailIgnoreCase(String email);
    boolean existsByEmailIgnoreCase(String email);
    List<User> findByRole(Role role);
    List<User> findByActiveTrue();

    @Query("SELECT u FROM User u WHERE u.active = true " +
           "AND (:role IS NULL OR u.role = :role) " +
           "AND (:dept IS NULL OR u.department = :dept) " +
           "AND (:year IS NULL OR u.academicYear = :year) " +
           "AND (:sec IS NULL OR u.section = :sec)")
    List<User> findMatchingRecipients(
            @Param("role") Role role,
            @Param("dept") Department dept,
            @Param("year") Integer year,
            @Param("sec") String sec
    );

    long countByRole(Role role);
}
