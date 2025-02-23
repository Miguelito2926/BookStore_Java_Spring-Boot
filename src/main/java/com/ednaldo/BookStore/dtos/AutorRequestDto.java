package com.ednaldo.BookStore.dtos;

import com.ednaldo.BookStore.entities.Autor;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;

public record AutorRequestDto(
        String nome,
        @JsonProperty(value = "data_nascimento") LocalDate dataNascimento,
        String nacionalidade) {

    public Autor mapearAutor() {
        Autor autor = new Autor();
        autor.setNome(this.nome);
        autor.setDataNascimento(this.dataNascimento);
        autor.setNacionalidade(this.nacionalidade);

        return autor;

    }
}


