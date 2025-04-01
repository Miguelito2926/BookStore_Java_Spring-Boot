package com.ednaldo.BookStore.controller;

import com.ednaldo.BookStore.api.GenericApi;
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
public class LivroController implements GenericApi {

    private final LivroService livroService;

    @PostMapping
    public ResponseEntity<LivroResponseDTO> insertLivro(@RequestBody @Valid LivroRequestDTO requestDTO) {
        LivroResponseDTO dto = livroService.createLivro(requestDTO);
        URI location = gerarHearderLocation(dto.id());

        return ResponseEntity.created(location).build();
    }

    @GetMapping
    public ResponseEntity<List<LivroResponseDTO>> listAllLivros() {
        return ResponseEntity.ok(livroService.findAllLivros());
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<LivroResponseDTO> getLivroDetail(@PathVariable UUID id) {
        return ResponseEntity.ok(livroService.getLivro(id));
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable UUID id) {
        livroService.deleteLivro(id);

        return ResponseEntity.noContent().build();
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<Void> updateLivro(@PathVariable UUID id, @RequestBody LivroRequestDTO requestDTO) {
        livroService.updateLivro(id, requestDTO);

        return ResponseEntity.noContent().build();
    }
}
