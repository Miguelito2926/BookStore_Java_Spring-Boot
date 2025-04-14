package com.ednaldo.BookStore.controller;

import com.ednaldo.BookStore.api.GenericApi;
import com.ednaldo.BookStore.dto.LivroRequestDTO;
import com.ednaldo.BookStore.dto.LivroResponseDTO;
import com.ednaldo.BookStore.enums.GeneroLivro;
import com.ednaldo.BookStore.services.LivroService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(value = "/livros")
@RequiredArgsConstructor
public class LivroController implements GenericApi {

    private final LivroService livroService;

    @PostMapping
    @PreAuthorize("hasAnyRole('OPERADOR', 'GERENTE')")
    public ResponseEntity<LivroResponseDTO> insertLivro(@RequestBody @Valid LivroRequestDTO requestDTO) {
        LivroResponseDTO dto = livroService.createLivro(requestDTO);
        URI location = gerarHearderLocation(dto.id());

        return ResponseEntity.created(location).build();
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('OPERADOR', 'GERENTE')")
    public ResponseEntity<List<LivroResponseDTO>> listAllLivros() {
        return ResponseEntity.ok(livroService.findAllLivros());
    }

    @GetMapping(value = "/search")
    @PreAuthorize("hasAnyRole('OPERADOR', 'GERENTE')")
    public ResponseEntity<Page<LivroResponseDTO>> searchLivros(

            @RequestParam(required = false) String isbn,
            @RequestParam(required = false) String titulo,
            @RequestParam(required = false) String nomeAutor,
            @RequestParam(required = false) GeneroLivro genero,
            @RequestParam(required = false) Integer anoPublicacao,
            @PageableDefault(size = 10, sort = "titulo", direction = Sort.Direction.ASC) Pageable pageable) {

        Page<LivroResponseDTO> page = livroService.searchLivros(isbn, titulo, nomeAutor, genero, anoPublicacao, pageable);
        return ResponseEntity.ok(page);

    }

    @GetMapping(value = "/{id}")
    @PreAuthorize("hasAnyRole('OPERADOR', 'GERENTE')")
    public ResponseEntity<LivroResponseDTO> getLivroDetail(@PathVariable UUID id) {
        return ResponseEntity.ok(livroService.getLivro(id));
    }

    @DeleteMapping(value = "/{id}")
    @PreAuthorize("hasAnyRole('OPERADOR', 'GERENTE')")
    public ResponseEntity<Void> deleteById(@PathVariable UUID id) {
        livroService.deleteLivro(id);

        return ResponseEntity.noContent().build();
    }

    @PutMapping(value = "/{id}")
    @PreAuthorize("hasAnyRole('OPERADOR', 'GERENTE')")
    public ResponseEntity<Void> updateLivro(@PathVariable UUID id, @RequestBody LivroRequestDTO requestDTO) {
        livroService.updateLivro(id, requestDTO);

        return ResponseEntity.noContent().build();
    }
}
