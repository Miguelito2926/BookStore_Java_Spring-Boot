package com.ednaldo.BookStore.mapper;

import com.ednaldo.BookStore.dto.LivroRequestDTO;
import com.ednaldo.BookStore.dto.LivroResponseDTO;
import com.ednaldo.BookStore.entities.Livro;
import com.ednaldo.BookStore.repositories.AutorRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Mapper(componentModel = "spring"/*uses = AutorMapper.class*/)
public abstract class LivroMapper {

    @Autowired
    AutorRepository autorRepository;

    @Mapping(target = "autor", expression = "java(autorRepository.findById(dto.idAutor()).orElse(null))")
    public abstract Livro toEntity(LivroRequestDTO dto);

    public abstract LivroResponseDTO toDTO(Livro livro);

    public abstract List<LivroResponseDTO> listToDto(List<Livro> allLivros);
}
