package com.ednaldo.BookStore.mapper;

import com.ednaldo.BookStore.dto.AutorRequestDTO;
import com.ednaldo.BookStore.dto.AutorResponseDTO;
import com.ednaldo.BookStore.entities.Autor;
import org.mapstruct.Mapper;
import org.springframework.data.domain.Page;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AutorMapper {

    Autor toEntity(AutorRequestDTO dto);

    List<Autor> listToEntity(List<AutorRequestDTO> dtoList);

    List<AutorResponseDTO> listToDTO(List<Autor> autorList);

    AutorResponseDTO toDTO(Autor autor);
}
