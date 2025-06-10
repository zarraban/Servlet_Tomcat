package org.example.app.service.user;

import org.example.app.dto.user.UserDtoRequest;
import org.example.app.entity.User;
import org.example.app.repository.user.UserRepository;
import org.example.app.repository.user.UserRepositoryImpl;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class UserServiceImpl implements UserService {

    private UserRepository repository;

    @Override
    public User create(UserDtoRequest request) {
        Objects.requireNonNull(request,
                "Parameter [request] must not be null!");
        repository = new UserRepositoryImpl();
        repository.save(request);
        return repository.getLastEntity()
                .orElse(null);
    }

    @Override
    public List<User> getAll() {
        repository = new UserRepositoryImpl();
        return repository.getAll()
                .orElse(Collections.emptyList());
    }

    // ---- Path Params ----------------------

    @Override
    public User getById(Long id) {
        Objects.requireNonNull(id,
                "Parameter [id] must not be null!");
        repository = new UserRepositoryImpl();
        return repository.getById(id).orElse(null);
    }

    @Override
    public User update(Long id, UserDtoRequest request) {
        Objects.requireNonNull(request,
                "Parameter [request] must not be null!");
        repository = new UserRepositoryImpl();
        if (id == null) {
            throw new IllegalArgumentException("Id must be provided!");
        }
        if (repository.getById(id).isPresent()) {
            repository.update(id, request);
        }
        return repository.getById(id).orElse(null);
    }

    @Override
    public boolean deleteById(Long id) {
        Objects.requireNonNull(id,
                "Parameter [id] must not be null!");
        repository = new UserRepositoryImpl();
        if (repository.getById(id).isPresent()) {
            repository.deleteById(id);
            return true;
        }
        return false;
    }
}