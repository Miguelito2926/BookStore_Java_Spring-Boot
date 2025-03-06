package com.ednaldo.BookStore.repositories;

import com.ednaldo.BookStore.entities.Autor;
import com.ednaldo.BookStore.entities.Livro;
import com.ednaldo.BookStore.enums.GeneroLivro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import java.util.UUID;

public interface LivroRepository extends JpaRepository<Livro, UUID> {


    //Query Method spring data jpa
    //select * from livro where id_autor =id
    List<Livro> findByAutor(Autor autor);

    //select * from livro where titulo = titulo
    List<Livro> findByTitulo(String livro);

    //select * from livro where isbn = isbn
    List<Livro> findByIsbn(String isbn);

    //select * from livro where titulo = ? and preco = ?
    List<Livro> findByTituloAndPreco(String titulo, BigDecimal preco);

    //select * from livro where titulo = ? or preco = ?
    List<Livro> findByTituloOrPreco(String titulo, BigDecimal preco);

    //select * from livro where data_publicaao between ? and ?
    List<Livro> findByDataPublicacaoBetween(LocalDate inicio, LocalDate fim);

    //select * from livro where titulo like'%O vento levou%'

    //JPQL -> referencia as entidades e as propriedades
    @Query("select livro  from Livro as livro order by livro.titulo, livro.preco")
    List<Livro> listarTodosOrdenadoPorTituloAndPreco();

    /**
     * select a.*
     * from livro l
     * join autor a on a.id = l.id_autor
     */
    @Query("select aut from Livro livro join livro.autor aut")
    List<Autor> listaAutoresDosLivros();

    /**
     * select distinct l.* from livro l
     */
    @Query("select distinct livro.titulo from Livro livro")
    List<String> listarNomesDiferentesLivros();

    /**
     * select distinct l.genero
     * from livro l
     * join autor a on a.id = l.id_autor
     * where a.nacionalidade = 'Brasileira'
     * order by l.genero
     */
    @Query("""
            select distinct livro.genero
            from Livro livro
            join livro.autor aut
            where aut.nacionalidade = 'Americano'
            order by livro.genero
            """)
    List<String> listarGeneroAutoresBrasileiros();

    //named parameters -> parametros nomeados
    @Query("select livro from Livro livro where livro.genero = :genero order by :paramOrdenacao")
    List<Livro> findByGenero(@Param("genero") GeneroLivro generoLivro, @Param("paramOrdenacao") String nomePropriedade);

    //positional parameters
    @Query("select livro from Livro livro where livro.genero = ?1 order by ?2")
    List<Livro> findByGeneroPositionalParameters(GeneroLivro generoLivro, String nomePropriedade);


    //Operações de Escrita apenas delete e update
    @Modifying
    @Transactional
    @Query("delete from Livro where genero = ?1")
    void deleteByGenero(GeneroLivro generoLivro);

    @Modifying
    @Transactional
    @Query("update Livro l set l.dataPublicacao = ?1 where l.id = ?2")
    void updateDataPublicacao(LocalDate novaData, UUID id);

    boolean existsByAutor(Autor autor);
}
