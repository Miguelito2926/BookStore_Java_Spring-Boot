package com.ednaldo.BookStore.dtos;

import com.ednaldo.BookStore.entities.Autor;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record AutorRequestDto(

        @NotBlank(message = "Campo é Obrigatório")
        String nome,

        @NotNull(message = "Campo não pode ser nulo")
        @JsonProperty(value = "data_nascimento")
        LocalDate dataNascimento,

        @NotBlank(message = "Campo é Obrigatório")
        String nacionalidade) {

    public Autor mapearAutor() {
        Autor autor = new Autor();
        autor.setNome(this.nome);
        autor.setDataNascimento(this.dataNascimento);
        autor.setNacionalidade(this.nacionalidade);

        return autor;

    }
}


