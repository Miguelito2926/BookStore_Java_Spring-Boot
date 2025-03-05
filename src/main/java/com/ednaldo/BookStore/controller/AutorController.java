package com.ednaldo.BookStore.controller;

import com.ednaldo.BookStore.dtos.AutorRequestDto;
import com.ednaldo.BookStore.dtos.AutorResponseDTO;
import com.ednaldo.BookStore.dtos.AutorSuccessResponseDTO;
import com.ednaldo.BookStore.entities.Autor;
import com.ednaldo.BookStore.services.AutorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.UUID;

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
        AutorSuccessResponseDTO autorResponseDTO = autorService.createAutor(autorEntidade);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(autorEntidade.getId())
                .toUri();
        return ResponseEntity.created(location).build();
    }

//    @GetMapping
//    public ResponseEntity<List<Autor>> findAllAutores() {
//        List<Autor> listaAutores = autorService.listarAutores();
//        return ResponseEntity.ok(listaAutores);
//    }

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
    public ResponseEntity<List<AutorResponseDTO>> pesquisar(
            @RequestParam(value = "nome", required = false) String nome,
            @RequestParam(value = "nacionalidade", required = false) String nacionalidade) {

        List<AutorResponseDTO> dto = autorService.pesquisarAutor(nome, nacionalidade);
        return ResponseEntity.ok(dto);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<Void> atualizarAutor(@PathVariable String id, @RequestBody AutorRequestDto requestDto) {
        autorService.update(id, requestDto);
        return ResponseEntity.ok().build();
    }
}
