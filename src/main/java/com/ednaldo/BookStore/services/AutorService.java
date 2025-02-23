package com.ednaldo.BookStore.services;


import com.ednaldo.BookStore.dtos.AutorResponseDTO;
import com.ednaldo.BookStore.entities.Autor;
import com.ednaldo.BookStore.repositories.AutorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AutorService {

    private final AutorRepository autorRepository;

    @Autowired
    public AutorService(AutorRepository autorRepository) {
        this.autorRepository = autorRepository;
    }

    public AutorResponseDTO createAutor(Autor requestDto) {
        autorRepository.save(requestDto);
        return new AutorResponseDTO(true);
    }
}
