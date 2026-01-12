package it.unicam.resourcebooking.service;

import it.unicam.resourcebooking.dto.CreateUserRequest;
import it.unicam.resourcebooking.exception.ApiException;
import it.unicam.resourcebooking.model.User;
import it.unicam.resourcebooking.repo.UserRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional(readOnly = true)
    public List<User> list() {
        return userRepository.findAll();
    }

    @Transactional
    public User create(CreateUserRequest req) {
        // Pre-check (messaggio chiaro). Il vincolo UNIQUE sul DB rimane comunque la safety net.
        if (userRepository.findByUsername(req.username()).isPresent()) {
            throw new ApiException(HttpStatus.CONFLICT, "Username già esistente: " + req.username());
        }
        try {
            User u = new User();
            u.setUsername(req.username());
            return userRepository.save(u);
        } catch (DataIntegrityViolationException e) {
            // In caso di race condition o DB già sporco.
            throw new ApiException(HttpStatus.CONFLICT, "Username già esistente: " + req.username());
        }
    }
}
