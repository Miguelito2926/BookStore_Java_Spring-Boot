package com.ednaldo.BookStore.dto;

import java.util.List;

public record UsuarioDTO(String login, String password, List<String> roles) {
}
