package org.example.app.repository.userImpl;

import org.example.app.entity.Product;
import org.example.app.repository.BaseRepository;
import org.example.app.request.RequestProductDTO;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends BaseRepository<Product, RequestProductDTO> {
    void save(RequestProductDTO request);

    Optional<List<Product>> getAll();

    Optional<Product> getById(Long id);

    void update(Long id, RequestProductDTO request);

    boolean deleteById(Long id);

    Optional<Product> getLastEntity();
}
