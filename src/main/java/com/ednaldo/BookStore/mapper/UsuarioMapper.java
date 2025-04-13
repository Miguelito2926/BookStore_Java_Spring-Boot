package com.ednaldo.BookStore.mapper;

import com.ednaldo.BookStore.dto.UsuarioDTO;
import com.ednaldo.BookStore.entities.Usuario;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UsuarioMapper {

    Usuario toEntity(UsuarioDTO dto);

    //AutorResponseDTO toDTO(Usuario usuario);
}
