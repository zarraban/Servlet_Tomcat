package org.example.app.service.product;

import org.example.app.entity.Product;

import org.example.app.repository.userImpl.ProductRepository;
import org.example.app.repository.userImpl.ProductRepositoryImpl;
import org.example.app.request.RequestProductDTO;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class ProductServiceImpl implements ProductService {

    private ProductRepository repository;

    @Override
    public Product create(RequestProductDTO request) {
        Objects.requireNonNull(request,
                "Parameter [request] must not be null!");
        repository = new ProductRepositoryImpl();
        repository.save(request);
        return repository.getLastEntity()
                .orElse(null);
    }

    @Override
    public List<Product> getAll() {
        repository = new ProductRepositoryImpl();
        return repository.getAll()
                .orElse(Collections.emptyList());
    }



    @Override
    public Product getById(Long id) {
        Objects.requireNonNull(id,
                "Parameter [id] must not be null!");
        repository = new ProductRepositoryImpl();
        return repository.getById(id).orElse(null);
    }

    @Override
    public Product update(Long id, RequestProductDTO request) {
        Objects.requireNonNull(request,
                "Parameter [request] must not be null!");
        repository = new ProductRepositoryImpl();
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
        repository = new ProductRepositoryImpl();
        if (repository.getById(id).isPresent()) {
            repository.deleteById(id);
            return true;
        }
        return false;
    }
}