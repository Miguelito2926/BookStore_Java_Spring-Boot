package com.ednaldo.BookStore.security;

import com.ednaldo.BookStore.entities.Usuario;
import com.ednaldo.BookStore.services.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private  final UsuarioService usuarioService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Usuario usuario = usuarioService.getByLogin(username);

        if (usuario == null) {
            throw new UsernameNotFoundException("User not found");
        }
        return User.builder()
                .username(usuario.getLogin())
                .password(usuario.getPassword())
                .roles(usuario.getRoles().toArray(new String[usuario.getRoles().size()]))
                .build();
    }
}
