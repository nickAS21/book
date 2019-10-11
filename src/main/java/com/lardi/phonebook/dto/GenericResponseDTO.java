package com.lardi.phonebook.dto;

import lombok.Data;

@Data
public class GenericResponseDTO<T> {
    private String message;
    private T body;
}
