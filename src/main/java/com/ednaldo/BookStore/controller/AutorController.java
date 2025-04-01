package com.ednaldo.BookStore.controller;


import com.ednaldo.BookStore.api.GenericApi;
import com.ednaldo.BookStore.dto.AutorRequestDTO;
import com.ednaldo.BookStore.dto.AutorResponseDTO;
import com.ednaldo.BookStore.dto.AutorSuccessResponseDTO;
import com.ednaldo.BookStore.entities.Autor;
import com.ednaldo.BookStore.mapper.AutorMapper;
import com.ednaldo.BookStore.services.AutorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/autores")
public class AutorController implements GenericApi {

    private final AutorService autorService;
    private final AutorMapper autorMapper;

    @PostMapping
    public ResponseEntity<Void> createAutor(@RequestBody @Valid AutorRequestDTO requestDto) {

        Autor autor = autorMapper.toEntity(requestDto);
        AutorSuccessResponseDTO autorResponseDTO = autorService.createAutor(autor);

        URI location = gerarHearderLocation(autor.getId());
        return ResponseEntity.created(location).build();
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<AutorResponseDTO> getAutorDetails(@PathVariable String id) throws Exception {
        return ResponseEntity.ok(autorService.getAutor(id));
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> removeAutor(@PathVariable String id) {
        autorService.deleteAutor(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<Page<AutorResponseDTO>> search(
            @RequestParam(value = "nome", required = false) String nome,
            @RequestParam(value = "nacionalidade", required = false) String nacionalidade,
            @PageableDefault(size = 20, sort = "nome", direction = Sort.Direction.ASC) Pageable pageable) {

            return autorService.searchByExample(nome, nacionalidade, pageable)
                    .map(autores -> autores.map(autorMapper::toDTO))
                    .map(ResponseEntity::ok)
                    .orElseGet(() -> ResponseEntity.noContent().build());
    }


    @PutMapping(value = "/{id}")
    public ResponseEntity<Void> updateAutor(@PathVariable String id, @RequestBody @Valid AutorRequestDTO requestDto) {
        autorService.update(id, requestDto);
        return ResponseEntity.ok().build();
    }
}
