package com.ednaldo.BookStore.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;
import java.util.UUID;

public record AutorResponseDTO(

        UUID id,

        String nome,

        @JsonProperty(value = "data_nascimento")
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
        LocalDate dataNascimento,

        String nacionalidade) {}