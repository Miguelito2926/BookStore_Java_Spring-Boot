package com.ednaldo.BookStore.dto;

import com.ednaldo.BookStore.entities.Autor;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record AutorRequestDto(

        @NotBlank(message = "Campo é Obrigatório")
        @Size(min = 2, max = 100, message = "Campo fora do tamanho padrão")
        String nome,

        @NotNull(message = "Campo não pode ser nulo")
        @Past(message = "Não é permitodo uma data futura")
        @JsonProperty(value = "data_nascimento")
        LocalDate dataNascimento,

        @NotBlank(message = "Campo é Obrigatório")
        @Size(min = 2, max = 50, message = "Campo fora do tamanho padrão")
        String nacionalidade) {}


