package com.ednaldo.BookStore.services;

import com.ednaldo.BookStore.dto.LivroRequestDTO;
import com.ednaldo.BookStore.dto.LivroResponseDTO;
import com.ednaldo.BookStore.entities.Autor;
import com.ednaldo.BookStore.entities.Livro;
import com.ednaldo.BookStore.entities.Usuario;
import com.ednaldo.BookStore.enums.GeneroLivro;
import com.ednaldo.BookStore.exceptions.NotFoundException;
import com.ednaldo.BookStore.mapper.LivroMapper;
import com.ednaldo.BookStore.repositories.AutorRepository;
import com.ednaldo.BookStore.repositories.LivroRepository;
import com.ednaldo.BookStore.security.SecurityService;
import com.ednaldo.BookStore.util.LivroSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class LivroService {

    private final LivroRepository livroRepository;
    private final LivroMapper livroMapper;
    private final AutorRepository autorRepository;
    private final SecurityService securityService;

    public LivroResponseDTO createLivro(LivroRequestDTO request) {
        // Verifica se o autor existe sem carregá-lo inteiro
        if (!autorRepository.existsById(request.idAutor())) {
            throw new NotFoundException("Não é possivel cadastrar  um livro com Autor inválido ou não existente.");
        }

        Usuario usuario = securityService.usuarioAutenticado();
        Livro livro = livroMapper.toEntity(request);
        livro.setUsuario(usuario);
        livroRepository.save(livro);

        return livroMapper.toDTO(livro);
    }

    public List<LivroResponseDTO> findAllLivros() {
        List<Livro> allLivros = livroRepository.findAll();
        return livroMapper.listToDto(allLivros);
    }

    public Page<LivroResponseDTO> searchLivros(String isbn, String titulo, String nomeAutor, GeneroLivro genero, Integer anoPublicacao, Pageable pageable) {

        Specification<Livro> specification = LivroSpecification.filterBy(isbn, titulo, nomeAutor, genero, anoPublicacao);
        Page<Livro> page = livroRepository.findAll(specification, pageable);
        return page.map(livroMapper::toDTO);
    }

    public LivroResponseDTO getLivro(UUID id) {
        Livro livro = livroRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Livro não encontrado!"));

        return livroMapper.toDTO(livro);
    }

    public void deleteLivro(UUID id) {

        if (!livroRepository.existsById(id)) {
            throw new NotFoundException("Livro não encontrado!");
        }
        livroRepository.deleteById(id);
    }

    public void updateLivro(UUID id, LivroRequestDTO requestDto) {
        var livro = livroRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Livro não encontrado!"));

        BeanUtils.copyProperties(requestDto, livro, "id", "autor");

        Autor autor = new Autor();
        autor.setId(requestDto.idAutor());
        livro.setAutor(autor);

        livroRepository.save(livro);
    }
}
