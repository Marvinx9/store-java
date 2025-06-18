package com.marvin.store.services;

import com.marvin.store.entities.User;
import com.marvin.store.repositories.UserRepository;
import com.marvin.store.services.exceptions.DataBaseException;
import com.marvin.store.services.exceptions.ResourceNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.NonTransientDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public List<User> findAll(){
        return userRepository.findAll();
    }

    public User findById(Long id){
        Optional<User> user = userRepository.findById(id);
        return user.orElseThrow(() -> new ResourceNotFoundException(id));
    }

    public User insert(User obj){
        return userRepository.save(obj);
    }

    public User update(Long id, User obj){
        try {
            User user = userRepository.getReferenceById(id);
            updateData(user, obj);
            return userRepository.save(user);
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException(id);
        }
    }

    private void updateData(User user, User obj) {
        user.setName(obj.getName());
        user.setEmail(obj.getEmail());
        user.setPhone(obj.getPhone());
    }

    public void delete(Long id) {
        try {
            User user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(id));
            userRepository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new DataBaseException(e.getMessage());
        }
    }
}
