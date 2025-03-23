package com.ednaldo.BookStore.mapper;

import com.ednaldo.BookStore.dto.LivroRequestDTO;
import com.ednaldo.BookStore.dto.LivroResponseDTO;
import com.ednaldo.BookStore.entities.Livro;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface LivroMapper {

    Livro toEntity(LivroRequestDTO dto);

    LivroResponseDTO toDTO(Livro livro);

    List<LivroResponseDTO> listToDto(List<Livro> allLivros);
}
