package com.ednaldo.BookStore.services;

import com.ednaldo.BookStore.dto.LivroRequestDTO;
import com.ednaldo.BookStore.dto.LivroResponseDTO;
import com.ednaldo.BookStore.entities.Autor;
import com.ednaldo.BookStore.entities.Livro;
import com.ednaldo.BookStore.exceptions.NotFoundException;
import com.ednaldo.BookStore.mapper.LivroMapper;
import com.ednaldo.BookStore.repositories.AutorRepository;
import com.ednaldo.BookStore.repositories.LivroRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
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
            throw new NotFoundException("Autor não encontrado!");
        }

        // Converte o DTO para entidade
        Livro livro = livroMapper.toEntity(request);

        // Cria apenas a referência do Autor com o ID
        Autor autor = new Autor();

        // Setando apenas o ID sem buscar no banco
        autor.setId(request.idAutor());

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

    public LivroResponseDTO obterLivro(UUID id) {
        Livro livro = livroRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Livro não encontrado!"));

        return livroMapper.toDTO(livro);
    }

    public void deletarLivro(UUID id) {

        if (!livroRepository.existsById(id)) {
            throw new NotFoundException("Autor não encontrado!");
        }
        livroRepository.deleteById(id);
    }

    public void atualizarLivro(UUID id, LivroRequestDTO requestDto) {
        var livro = livroRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Livro não encontrado!"));

        BeanUtils.copyProperties(requestDto, livro, "id", "autor");

        Autor autor = new Autor();
        autor.setId(requestDto.idAutor());
        livro.setAutor(autor);

        livroRepository.save(livro);
    }
}
