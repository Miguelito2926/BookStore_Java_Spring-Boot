package com.ednaldo.BookStore.controller;

import com.ednaldo.BookStore.dto.LivroRequestDTO;
import com.ednaldo.BookStore.dto.LivroResponseDTO;
import com.ednaldo.BookStore.services.LivroService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(value = "/livros")
@RequiredArgsConstructor
public class LivroController {

    private final LivroService livroService;

    @PostMapping
    public ResponseEntity<LivroResponseDTO> insertLivro(@RequestBody @Valid LivroRequestDTO requestDTO) {

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

    @GetMapping(value = "/{id}")
    public ResponseEntity<LivroResponseDTO> listLivros(@PathVariable UUID id) {

        return ResponseEntity.ok(livroService.obterLivro(id));
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deletarLivros(@PathVariable UUID id) {

        livroService.deletarLivro(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<Void> listLivros(@PathVariable UUID id, @RequestBody LivroRequestDTO requestDTO) {

        livroService.atualizarLivro(id, requestDTO);
        return ResponseEntity.noContent().build();
    }
}
