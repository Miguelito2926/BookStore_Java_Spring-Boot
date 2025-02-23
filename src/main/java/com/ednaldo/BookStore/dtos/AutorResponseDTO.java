package com.ednaldo.BookStore.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AutorResponseDTO implements Serializable {
    private static final long serialVersion = 1L;

    private boolean sucesso;
}
