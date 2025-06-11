package org.example.app.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletInputStream;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.app.entity.Product;
import org.example.app.repository.userImpl.ProductRepositoryImpl;
import org.example.app.request.RequestProductDTO;
import org.example.app.service.product.ProductService;
import org.example.app.service.product.ProductServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Collections;
import java.util.List;


@WebServlet("/api1/v1/products/*")
public class ProductController extends HttpServlet {
    private static  final Logger logger = LoggerFactory.getLogger(ProductController.class);
    private ProductService service;
    private ObjectMapper objectMapper;
    private static final String CONTENT_TYPE = "application/json";

    @Override
    public void init(ServletConfig config) throws ServletException {
        this.service = new ProductServiceImpl();
        objectMapper = new ObjectMapper();
        super.init(config);
    }
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        logHttpMethods("POST");

        try (ServletInputStream in = req.getInputStream()) {
            RequestProductDTO productDtoRequest =
                    this.objectMapper.readValue(in, RequestProductDTO.class);
            Product product = this.service.create(productDtoRequest);
            String json;
            resp.setStatus(HttpServletResponse.SC_OK);
            resp.setContentType(CONTENT_TYPE);
            if (product != null) {
                json = this.objectMapper.writeValueAsString(
                        "created"
                );
                resp.setContentLength(json.length());
                try (ServletOutputStream out = resp.getOutputStream()) {
                    out.println(json);
                    out.flush();
                }
            } else {
                json = this.objectMapper.writeValueAsString(
                        "Not created"
                );
                resp.setContentLength(json.length());
                try (ServletOutputStream out = resp.getOutputStream()) {
                    out.println(json);
                    out.flush();
                }
            }
        }
    }
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        logHttpMethods("GET");
        String uri = req.getRequestURI();
        String[] uriParts = uri.split("/");
        String pathValue = uriParts[uriParts.length - 1];


        if (pathValue != null && !pathValue.isEmpty() && !pathValue.equals("products")) {
            Product product= this.service.getById(Long.parseLong(pathValue));
            String json;
            resp.setStatus(HttpServletResponse.SC_OK);
            resp.setContentType(CONTENT_TYPE);
            if (product != null) {
                json = this.objectMapper.writeValueAsString(product);
                resp.setContentLength(json.length());
                try (ServletOutputStream out = resp.getOutputStream()) {
                    out.println(json);
                    out.flush();
                }
            } else {
                json = this.objectMapper.writeValueAsString(
                        "Product is null");
                resp.setContentLength(json.length());
                try (ServletOutputStream out = resp.getOutputStream()) {
                    out.println(json);
                    out.flush();
                }
            }
        }

        // Отримання всіх даних
        if (pathValue != null && pathValue.equals("products")) {
            List<Product> list = this.service.getAll();
            String json;
            resp.setStatus(HttpServletResponse.SC_OK);
            resp.setContentType(CONTENT_TYPE);
            if (list.isEmpty()) {
                json = this.objectMapper.writeValueAsString(
                                Collections.emptyList()
                );
                resp.setContentLength(json.length());
                try (ServletOutputStream out = resp.getOutputStream()) {
                    out.println(json);
                    out.flush();
                }
            } else {
                json = this.objectMapper.writeValueAsString(
                                list
                );
                resp.setContentLength(json.length());
                try (ServletOutputStream out = resp.getOutputStream()) {
                    out.println(json);
                    out.flush();
                }
            }
        }
    }
    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        logHttpMethods("PUT");
        String uri = req.getRequestURI();
        String[] uriParts = uri.split("/");
        String pathValue = uriParts[uriParts.length - 1];
        if (pathValue != null && !pathValue.isEmpty()) {
            try (ServletInputStream in = req.getInputStream()) {
                Long productIdToUpdate = Long.parseLong(pathValue);
                RequestProductDTO userDtoRequest =
                        this.objectMapper.readValue(in, RequestProductDTO.class);
                Product userToUpdate = service.getById(productIdToUpdate);
                if (userToUpdate != null) {
                    Product userUpdated = this.service.update(productIdToUpdate,
                            userDtoRequest);
                    String json = this.objectMapper.writeValueAsString(
                            userUpdated
                    );
                    resp.setStatus(HttpServletResponse.SC_OK);
                    resp.setContentType(CONTENT_TYPE);
                    resp.setContentLength(json.length());
                    try (ServletOutputStream out = resp.getOutputStream()) {
                        out.println(json);
                        out.flush();
                    }
                } else {
                    String json = this.objectMapper.writeValueAsString(null);
                    resp.setStatus(HttpServletResponse.SC_OK);
                    resp.setContentType(CONTENT_TYPE);
                    resp.setContentLength(json.length());
                    try (ServletOutputStream out = resp.getOutputStream()) {
                        out.println(json);
                        out.flush();
                    }
                }
            }
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        logHttpMethods("DELETE");
        String uri = req.getRequestURI();
        String[] uriParts = uri.split("/");
        String pathValue = uriParts[uriParts.length - 1];
        if (pathValue != null && !pathValue.isEmpty()) {
            Long userDeleteId = Long.parseLong(pathValue);
            boolean isUserDeleted =
                    this.service.deleteById(userDeleteId);
            String json;
            resp.setStatus(HttpServletResponse.SC_OK);
            resp.setContentType(CONTENT_TYPE);
            if (isUserDeleted) {
                json = this.objectMapper.writeValueAsString(true);
                resp.setContentLength(json.length());
                try (ServletOutputStream out = resp.getOutputStream()) {
                    out.println(json);
                    out.flush();
                }
            } else {
                json = this.objectMapper.writeValueAsString(false);
                resp.setContentLength(json.length());
                try (ServletOutputStream out = resp.getOutputStream()) {
                    out.println(json);
                    out.flush();
                }
            }
        }
    }

    private static void logHttpMethods(String methodName){
        logger.info("{}-method status", methodName);
    }
}
