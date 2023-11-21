package com.example.projectcourse4.service;

import com.example.projectcourse4.entity.User;
import com.example.projectcourse4.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class UserService {
    private final Logger logger = LoggerFactory.getLogger(UserService.class);
    private final UserRepository userRepository;

    @Autowired // constructor inject
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional(readOnly = true)
    public Optional<User> findByUsername(String username) {
        return userRepository.findUserByUsername(username);
    }

    @Transactional
    public Iterable<User> getAll() {
        return userRepository.findAll();
    }

    @Transactional
    public User save(User user) {
        return userRepository.save(user);
    }

    @Transactional
    public User update(User user) {
        return userRepository.save(user);
    }

    @Transactional
    public void delete(User user) {
        try {
            if (userRepository.existsByUsername(user.getUsername())) {
                userRepository.delete(user);
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }

    @Transactional
    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }

}
