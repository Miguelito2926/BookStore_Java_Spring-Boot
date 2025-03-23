package com.ednaldo.BookStore.controller;

import com.ednaldo.BookStore.dto.LivroRequestDTO;
import com.ednaldo.BookStore.dto.LivroResponseDTO;
import com.ednaldo.BookStore.entities.Livro;
import com.ednaldo.BookStore.mapper.LivroMapper;
import com.ednaldo.BookStore.services.LivroService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "/livros")
@RequiredArgsConstructor
public class LivroController {

    private final LivroService livroService;
    private final LivroMapper livroMapper;

    @PostMapping
    public ResponseEntity<LivroResponseDTO> insertLivro(@RequestBody @Valid LivroRequestDTO requestDTO){

       // Livro livro = livroMapper.toEntity(requestDTO);
        LivroResponseDTO dto = livroService.cadastrarLivro(requestDTO);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(dto.id())
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @GetMapping
    public ResponseEntity<List<LivroResponseDTO>> listLivros() {
        return ResponseEntity.ok(livroService.listarLivros());
    }
}
