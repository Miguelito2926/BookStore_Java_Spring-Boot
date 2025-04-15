package com.ednaldo.BookStore.entities;

import com.ednaldo.BookStore.enums.GeneroLivro;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "livro")
@ToString(exclude = "autor")
@EntityListeners(AuditingEntityListener.class)
public class Livro {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(length = 20, nullable = false, unique = true)
    private String isbn;

    @Column(length = 150, nullable = false)
    private String titulo;

    @Column(name = "data_publicacao")
    private LocalDate dataPublicacao;

    @Enumerated(EnumType.STRING)
    @Column(length = 30, nullable = false)
    private GeneroLivro genero;

    @Column(precision = 18, scale = 2)
    private BigDecimal preco;

    @ManyToOne (
            // cascade = CascadeType.ALL
            fetch = FetchType.LAZY
    )
    @JoinColumn(name = "id_autor")
    private Autor autor;

    @Column(name = "data_cadastro")
    @CreatedDate
    private LocalDateTime dataCadastro;

    @Column(name = "data_atualizacao")
    @LastModifiedDate
    private LocalDateTime dataAtualizacao;

    @ManyToOne
    @JoinColumn(name = "id_usuario")
    private Usuario usuario;

}