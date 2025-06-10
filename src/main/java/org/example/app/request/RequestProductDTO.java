package org.example.app.request;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record RequestProductDTO(Integer id, String name, Integer cost, Integer order_id) {
}
