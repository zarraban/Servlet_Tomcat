package org.example.app.service.user;


import org.example.app.dto.user.UserDtoRequest;
import org.example.app.entity.User;
import org.example.app.service.BaseService;

import java.util.List;

public interface UserService extends BaseService<User, UserDtoRequest> {
    // Створення нового запису
    User create(UserDtoRequest request);
    // Отримання всіх записів
    List<User> getAll();

    // ---- Path Params ----------------------

    // Отримання запису за id
    User getById(Long id);
    // Оновлення запису за id
    User update(Long id, UserDtoRequest request);
    // Видалення запису за id
    boolean deleteById(Long id);
}
