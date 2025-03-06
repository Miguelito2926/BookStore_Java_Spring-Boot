package com.ednaldo.BookStore;


import com.ednaldo.BookStore.entities.Autor;
import com.ednaldo.BookStore.entities.Livro;
import com.ednaldo.BookStore.enums.GeneroLivro;
import com.ednaldo.BookStore.repositories.AutorRepository;
import com.ednaldo.BookStore.repositories.LivroRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@SpringBootTest
class LivroRepositoryTest {

    @Autowired
    LivroRepository livroRepository;

    @Autowired
    AutorRepository autorRepository;

    @Test
    void salvarSemCascade() {

        Livro livro = new Livro();
        Autor autor = autorRepository.findById(UUID.fromString("152a2501-b376-4faa-8752-f36480db25f9")).orElse(null);

        livro.setIsbn("1213-5156");
        livro.setPreco(BigDecimal.valueOf(100));
        livro.setGenero(GeneroLivro.FICCAO);
        livro.setDataPublicacao(LocalDate.of(1990, 1, 2));
        livro.setTitulo("Lula Livre");
        livro.setAutor(autor);

        livroRepository.save(livro);
    }

//    @Test
//    void salvarComCascade() {
//
//        Livro livro = new Livro();
//        Autor autor = new Autor(null, "Ednaldo", LocalDate.of(1995, 12, 20), "Brasileira", null);
//
//        livro.setIsbn("1213-5156");
//        livro.setPreco(BigDecimal.valueOf(100));
//        livro.setGenero(GeneroLivro.FICCAO);
//        livro.setDataPublicacao(LocalDate.of(1990, 1, 2));
//        livro.setTitulo("Lula Preso");
//        livro.setAutor(autor);
//
//        livroRepository.save(livro);
//    }

    @Test
    void atualizarAutorLivro() {
        UUID id = UUID.fromString("8d93952b-181f-4c79-8dd9-ba080d43e912");
        var livroParaAtualizar = livroRepository.findById(id).orElse(null);

        UUID idAutor = UUID.fromString("643cd73c-0c7d-4eb2-bc9d-dee24f233745");
        Autor miguel = autorRepository.findById(idAutor).orElse(null);
        livroParaAtualizar.setAutor(miguel);

        livroRepository.save(livroParaAtualizar);
    }

    @Test
    void deletar() {
        UUID id = UUID.fromString("8d93952b-181f-4c79-8dd9-ba080d43e912");
        livroRepository.deleteById(id);
    }

    @Test
    @Transactional
    void buscarLivro() {
        UUID id = UUID.fromString("8d93952b-181f-4c79-8dd9-ba080d43e912");
        Livro  livro = livroRepository.findById(id).orElse(null);

        System.out.println("Livro: " + livro.getTitulo());
        System.out.println("Autor: " + livro.getAutor().getNome());
    }

    @Test
    void listarLivrosComJPQL() {
        var resultado = livroRepository.listarTodosOrdenadoPorTituloAndPreco();
        resultado.forEach(System.out::println);
    }

    @Test
    void listarAutoresDosLivrosComJPQL() {
        var resultado = livroRepository.listaAutoresDosLivros();
        resultado.forEach(System.out::println);
    }

    @Test
    void listarTitulosNaoRepetidosLivrosComJPQL() {
        var resultado = livroRepository.listarNomesDiferentesLivros();
        resultado.forEach(System.out::println);
    }

    @Test
    void listarGenerosLivrosDeAutorsBrasileirosComJPQL() {
        var resultado = livroRepository.listarGeneroAutoresBrasileiros();
        resultado.forEach(System.out::println);
    }

    @Test
    void listarPorGeneroQueryParamTes() {
        var resultado = livroRepository.findByGenero(GeneroLivro.FICCAO, "preco");
        resultado.forEach(System.out::println);
    }

    @Test
    void listarPorGeneroPositionalParamQueryParamTes() {
        var resultado = livroRepository.findByGeneroPositionalParameters(GeneroLivro.FICCAO, "preco");
        resultado.forEach(System.out::println);
    }

    @Test
    void deletPorGenero() {
        livroRepository.deleteByGenero(GeneroLivro.CIENCIA);
    }

    @Test
    void updateDataPublicacaoTest() {
        livroRepository.updateDataPublicacao(LocalDate.of(2020, 10, 22), UUID.fromString("0111933f-6830-49a5-b569-d647611eb416"));
    }
}