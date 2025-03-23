package com.ednaldo.BookStore.services;

import com.ednaldo.BookStore.dto.LivroRequestDTO;
import com.ednaldo.BookStore.dto.LivroResponseDTO;
import com.ednaldo.BookStore.entities.Autor;
import com.ednaldo.BookStore.entities.Livro;
import com.ednaldo.BookStore.exceptions.AutorNotFoundException;
import com.ednaldo.BookStore.mapper.LivroMapper;
import com.ednaldo.BookStore.repositories.AutorRepository;
import com.ednaldo.BookStore.repositories.LivroRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class LivroService {

    private final LivroRepository livroRepository;
    private final LivroMapper livroMapper;
    private final AutorRepository autorRepository;

    public LivroResponseDTO cadastrarLivro(LivroRequestDTO request) {
        // Verifica se o autor existe sem carregá-lo inteiro
        if (!autorRepository.existsById(request.idAutor())) {
            throw new AutorNotFoundException("Autor não encontrado!");
        }

        // Converte o DTO para entidade
        Livro livro = livroMapper.toEntity(request);

        // Cria apenas a referência do Autor com o ID
        Autor autor = new Autor();
        autor.setId(request.idAutor()); // Setando apenas o ID sem buscar no banco

        // Associa o autor ao livro
        livro.setAutor(autor);

        // Salva o livro
        livroRepository.save(livro);

        return livroMapper.toDTO(livro);
    }

    public List<LivroResponseDTO> listarLivros() {
        List<Livro> allLivros = livroRepository.findAll();
        return livroMapper.listToDto(allLivros);
    }
}
