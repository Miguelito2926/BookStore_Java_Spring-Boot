package com.ednaldo.BookStore.services;


import com.ednaldo.BookStore.dtos.AutorResponseDTO;
import com.ednaldo.BookStore.dtos.AutorSuccessResponseDTO;
import com.ednaldo.BookStore.entities.Autor;
import com.ednaldo.BookStore.exceptions.AutorNotFoundException;
import com.ednaldo.BookStore.repositories.AutorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class AutorService {

    private final AutorRepository autorRepository;

    @Autowired
    public AutorService(AutorRepository autorRepository) {
        this.autorRepository = autorRepository;
    }

    public AutorSuccessResponseDTO createAutor(Autor requestDto) {
        autorRepository.save(requestDto);
        return new AutorSuccessResponseDTO(true);
    }

    public List<Autor> listarAutores() {
        List<Autor> autorList = autorRepository.findAll();
        return autorList;
    }

    public AutorResponseDTO getAutor(String  id) throws Exception {

        Optional<Autor> autorOptional = autorRepository.findById(UUID.fromString(id));
        if (autorOptional.isPresent()) {
            Autor autor = autorOptional.get();
            AutorResponseDTO dto = new AutorResponseDTO(
                    autor.getId(),
                    autor.getNome(),
                    autor.getDataNascimento(),
                    autor.getNacionalidade()
            );
            return dto;
        }
        throw new AutorNotFoundException("Autor com o ID " + id + " não encontrado.");
    }
}
