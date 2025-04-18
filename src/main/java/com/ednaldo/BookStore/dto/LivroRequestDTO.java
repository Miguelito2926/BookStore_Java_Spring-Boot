package com.ednaldo.BookStore.dto;

import com.ednaldo.BookStore.enums.GeneroLivro;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import org.hibernate.validator.constraints.ISBN;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public record LivroRequestDTO(

        @NotBlank(message = "Campo Obrigatório")
        @ISBN
        String isbn,

        @NotBlank(message = "Campo Obrigatório")
        String titulo,

        @NotNull(message = "Campo não pode ser nulo")
        @Past(message = "Não é permitido data futura")
        @Column(name = "data_publicacao")
        LocalDate dataPublicacao,

        GeneroLivro genero,
        
        BigDecimal preco,

        @NotNull(message = "Campo não pode ser nulo")
        UUID idAutor) { }
