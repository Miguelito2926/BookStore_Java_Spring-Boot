package com.ednaldo.BookStore.services;


import com.ednaldo.BookStore.dtos.AutorResponseDTO;
import com.ednaldo.BookStore.dtos.AutorSuccessResponseDTO;
import com.ednaldo.BookStore.entities.Autor;
import com.ednaldo.BookStore.exceptions.AutorNotFoundException;
import com.ednaldo.BookStore.repositories.AutorRepository;
import org.hibernate.sql.results.internal.StandardRowReader;
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

    public AutorResponseDTO getAutor(String id) throws Exception {

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


    /*Por que essa abordagem no delete?
✅ Evita a consulta completa (findById carrega o objeto inteiro, existsById só verifica a existência).
✅ Não precisa capturar exceção.
✅ Código mais claro e direto.

Se o ID existir, ele exclui. Se não, lança a exceção antes de tentar excluir.*/
    public void deleteAutor(String id) {
        UUID uuid = UUID.fromString(id);

        if (!autorRepository.existsById(uuid)) {
            throw new AutorNotFoundException("Autor com o ID " + id + " não encontrado.");
        }
        autorRepository.deleteById(uuid);
    }
}
