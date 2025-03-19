package com.ednaldo.BookStore.controller;

import com.ednaldo.BookStore.dto.LivroRequestDTO;
import com.ednaldo.BookStore.entities.Livro;
import com.ednaldo.BookStore.mapper.LivroMapper;
import com.ednaldo.BookStore.services.LivroService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping(value = "/livros")
@RequiredArgsConstructor
public class LivroController {

    private final LivroService livroService;
    private final LivroMapper livroMapper;

    @PostMapping
    public ResponseEntity<Object> insertLivro(@RequestBody @Valid LivroRequestDTO requestDTO){

        Livro livro = livroMapper.toEntity(requestDTO);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(livro.getId())
                .toUri();
        return ResponseEntity.created(location).build();
    }
}
