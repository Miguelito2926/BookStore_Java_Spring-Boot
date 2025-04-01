package com.ednaldo.BookStore.services;



import com.ednaldo.BookStore.dto.AutorRequestDTO;
import com.ednaldo.BookStore.dto.AutorResponseDTO;
import com.ednaldo.BookStore.dto.AutorSuccessResponseDTO;
import com.ednaldo.BookStore.entities.Autor;
import com.ednaldo.BookStore.exceptions.NotFoundException;
import com.ednaldo.BookStore.exceptions.OperationNotAllowedException;
import com.ednaldo.BookStore.mapper.AutorMapper;
import com.ednaldo.BookStore.repositories.AutorRepository;
import com.ednaldo.BookStore.repositories.LivroRepository;
import com.ednaldo.BookStore.validator.AutorValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class AutorService {

    private final AutorRepository autorRepository;
    private final AutorValidator autorValidator;
    private final LivroRepository livroRepository;
    private final AutorMapper autorMapper;

    public AutorSuccessResponseDTO createAutor(Autor requestDto) {

        autorValidator.validateAutor(requestDto);
        autorRepository.save(requestDto);
        return new AutorSuccessResponseDTO(true);
    }

    public AutorResponseDTO getAutor(String id) {

        Optional<Autor> autorOptional = autorRepository.findById(UUID.fromString(id));
        if (autorOptional.isPresent()) {
            Autor autor = autorOptional.get();
            return autorMapper.toDTO(autor);
        }
        throw new NotFoundException("Autor com o ID " + id + " não encontrado.");
    }


    /*Por que essa abordagem no delete?
✅ Evita a consulta completa (findById carrega o objeto inteiro, existsById só verifica a existência).
✅ Não precisa capturar exceção.
✅ Código mais claro e direto.

Se o ID existir, ele exclui. Se não, lança a exceção antes de tentar excluir.*/
    public void deleteAutor(String id) {

        UUID uuid = UUID.fromString(id);

        Autor autor = autorRepository.findById(uuid).orElseThrow(() -> new NotFoundException("Autor com o ID " + id + " não encontrado."));

        if (hasLivro(autor)){
            throw new OperationNotAllowedException("Não é possivel excluir um autor que possui livros cadastrado!");
        }

        autorRepository.deleteById(uuid);
    }

    public Optional<Page<Autor>> searchByExample(String nome, String nacionalidade, Pageable pageable) {

        if (nome == null && nacionalidade == null) {
            return Optional.empty();
        }

        var autor = new Autor();
        autor.setNome(nome);
        autor.setNacionalidade(nacionalidade);

        ExampleMatcher matcher = ExampleMatcher
                .matching()
                .withIgnoreNullValues()
                .withIgnoreCase()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
        Example<Autor> autorExample = Example.of(autor, matcher);

        Page<Autor> result = autorRepository.findAll(autorExample, pageable);

        return result.isEmpty() ? Optional.empty() : Optional.of(result);
    }

    public void update(String id, AutorRequestDTO requestDto) {

        // Busca o autor pelo ID, lançando exceção se não encontrar
       Autor autor =  autorRepository.findById(UUID.fromString(id))
               .orElseThrow(() -> new NotFoundException("Autor com o ID" + id + "não encontrado."));

        // Atualiza os dados do autor existente
        autor.setNome(requestDto.nome());
        autor.setDataNascimento(requestDto.dataNascimento());
        autor.setNacionalidade(requestDto.nacionalidade());

        // Valida se os dados podem ser atualizados sem criar duplicatas
        autorValidator.validateAutor(autor);

        // Salva a atualização no banco de dados
        autorRepository.save(autor);
    }

    //Metodo auxiliar para verificação se autor possui livros cadastrados
    public boolean hasLivro(Autor autor) {
        return livroRepository.existsByAutor(autor);
    }
}