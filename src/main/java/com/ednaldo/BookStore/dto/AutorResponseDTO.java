package com.ednaldo.BookStore.dto;

import com.ednaldo.BookStore.entities.Autor;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;
import java.util.UUID;

public record AutorResponseDTO(UUID id, String nome, @JsonProperty(value = "data_nascimento") LocalDate dataNascimento, String nacionalidade) {

    public Autor mapearAutor() {
        Autor autor = new Autor();
        autor.setId(this.id);
        autor.setNome(this.nome);
        autor.setDataNascimento(this.dataNascimento);
        autor.setNacionalidade(this.nacionalidade);

        return autor;
    }
}
