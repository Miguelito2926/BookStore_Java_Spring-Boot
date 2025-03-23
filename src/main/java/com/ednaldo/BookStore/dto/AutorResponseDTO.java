package com.ednaldo.BookStore.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;
import java.util.UUID;

public record AutorResponseDTO(

        UUID id,

        String nome,

        @JsonProperty(value = "data_nascimento")

        LocalDate dataNascimento,

        String nacionalidade) {}