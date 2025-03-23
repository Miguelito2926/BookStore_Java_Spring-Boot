package com.ednaldo.BookStore.dto;

import com.ednaldo.BookStore.enums.GeneroLivro;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public record LivroResponseDTO(

        UUID id,

        String isbn,

        String titulo,

        @JsonProperty(value = "data_publicacao")
        LocalDate dataPublicacao,

        GeneroLivro genero,

        BigDecimal preco,

        AutorResponseDTO autor) {}
