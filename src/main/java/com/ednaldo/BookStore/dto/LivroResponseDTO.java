package com.ednaldo.BookStore.dto;

import com.ednaldo.BookStore.enums.GeneroLivro;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public record LivroResponseDTO(
        UUID id,
        String isbn,
        String titulo,
        LocalDate dataPublicao,
        GeneroLivro genero,
        BigDecimal preco,
        AutorResponseDTO autor) {}
