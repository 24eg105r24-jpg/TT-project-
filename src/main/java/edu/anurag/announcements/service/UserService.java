package edu.anurag.announcements.service;

import edu.anurag.announcements.model.Department;
import edu.anurag.announcements.model.Role;
import edu.anurag.announcements.model.User;
import edu.anurag.announcements.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmailIgnoreCase(email);
    }

    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public List<User> getActiveUsers() {
        return userRepository.findByActiveTrue();
    }

    public List<User> getMatchingRecipients(Role role, Department department, Integer year, String section) {
        return userRepository.findMatchingRecipients(role, department, year, section);
    }

    @Transactional
    public User registerUser(User user) {
        if (userRepository.existsByEmailIgnoreCase(user.getEmail())) {
            throw new IllegalArgumentException("User with email " + user.getEmail() + " already exists");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    @Transactional
    public User updateUser(User user) {
        return userRepository.save(user);
    }

    public long countTotalUsers() {
        return userRepository.count();
    }

    public long countByRole(Role role) {
        return userRepository.countByRole(role);
    }
}
