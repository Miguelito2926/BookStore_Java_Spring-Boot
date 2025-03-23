package com.ednaldo.BookStore.controller;

import com.ednaldo.BookStore.dto.AutorRequestDto;
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
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/autores")
public class AutorController {

    private final AutorService autorService;
    private final AutorMapper autorMapper;

    @PostMapping
    public ResponseEntity<Void> criarAutor(@RequestBody @Valid AutorRequestDto requestDto) {

        Autor autor = autorMapper.toEntity(requestDto);
        AutorSuccessResponseDTO autorResponseDTO = autorService.createAutor(autor);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(autor.getId())
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<AutorResponseDTO> obterAutorDetails(@PathVariable String id) throws Exception {

        return ResponseEntity.ok(autorService.getAutor(id));
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> removeAutor(@PathVariable String id) {
        autorService.deleteAutor(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<Page<AutorResponseDTO>> pesquisar(
            @RequestParam(value = "nome", required = false) String nome,
            @RequestParam(value = "nacionalidade", required = false) String nacionalidade,
            @PageableDefault(size = 20, sort = "nome", direction = Sort.Direction.ASC) Pageable pageable) {

            return autorService.pesquisaByExample(nome, nacionalidade, pageable)
                    .map(autores -> autores.map(autorMapper::toDTO))
                    .map(ResponseEntity::ok)
                    .orElseGet(() -> ResponseEntity.noContent().build());
    }


    @PutMapping(value = "/{id}")
    public ResponseEntity<Void> atualizarAutor(@PathVariable String id, @RequestBody @Valid AutorRequestDto requestDto) {
        autorService.update(id, requestDto);
        return ResponseEntity.ok().build();
    }
}
