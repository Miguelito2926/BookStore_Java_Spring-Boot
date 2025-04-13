package com.ednaldo.BookStore.services;

import com.ednaldo.BookStore.dto.UsuarioDTO;
import com.ednaldo.BookStore.entities.Usuario;
import com.ednaldo.BookStore.mapper.UsuarioMapper;
import com.ednaldo.BookStore.repositories.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final UsuarioMapper usuarioMapper;

    public void save(UsuarioDTO usuario) {

        Usuario usuarioEntity = usuarioMapper.toEntity(usuario);
        var password = usuarioEntity.getPassword();
        usuarioEntity.setPassword(passwordEncoder.encode(password));

        usuarioRepository.save(usuarioEntity);
    }

    public Usuario getByLogin(String login) {
        return usuarioRepository.findByLogin(login);
    }
}
