package com.ednaldo.BookStore.controller;

import com.ednaldo.BookStore.dto.UsuarioDTO;
import com.ednaldo.BookStore.services.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void save(@RequestBody UsuarioDTO usuario) {
        usuarioService.save(usuario);
    }
}
