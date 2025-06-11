package org.example.app.service.product;



import org.example.app.entity.Product;
import org.example.app.request.RequestProductDTO;
import org.example.app.service.BaseService;

import java.util.List;

public interface ProductService extends BaseService<Product, RequestProductDTO> {
    // Створення нового запису
    Product create(RequestProductDTO request);
    // Отримання всіх записів
    List<Product> getAll();

    // ---- Path Params ----------------------

    // Отримання запису за id
    Product getById(Long id);
    // Оновлення запису за id
    Product update(Long id, RequestProductDTO request);
    // Видалення запису за id
    boolean deleteById(Long id);
}
