package com.ednaldo.BookStore.controller;

import com.ednaldo.BookStore.dtos.AutorRequestDto;
import com.ednaldo.BookStore.dtos.AutorResponseDTO;
import com.ednaldo.BookStore.entities.Autor;
import com.ednaldo.BookStore.services.AutorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping(value = "/autores")
public class AutorController {

    private final AutorService autorService;

    @Autowired
    public AutorController(AutorService autorService) {
        this.autorService = autorService;
    }

    @PostMapping
    public ResponseEntity<Void> criarAutor(@RequestBody AutorRequestDto requestDto) {

        Autor autorEntidade = requestDto.mapearAutor();
        AutorResponseDTO autorResponseDTO = autorService.createAutor(autorEntidade);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(autorEntidade.getId())
                .toUri();
        return ResponseEntity.created(location).build();
    }
}
