package com.ednaldo.BookStore.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AutorSuccessResponseDTO implements Serializable {
    private static final long serialVersion = 1L;

    private boolean sucesso;
}
