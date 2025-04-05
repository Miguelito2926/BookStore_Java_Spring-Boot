package com.ednaldo.BookStore.util;

import com.ednaldo.BookStore.entities.Livro;
import com.ednaldo.BookStore.enums.GeneroLivro;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import jakarta.persistence.criteria.Predicate;

import java.util.ArrayList;
import java.util.List;

    public class LivroSpecification {
        public static Specification<Livro> filterBy(String isbn, String titulo, String nomeAutor, GeneroLivro genero, Integer anoPublicacao) {
            return (root, query, criteriaBuilder) -> {
                List<Predicate> predicates = new ArrayList<>();

                if (isbn != null) {
                    predicates.add(criteriaBuilder.equal(root.get("isbn"), isbn));
                }

                if (titulo != null) {
                    predicates.add(criteriaBuilder.like(
                            criteriaBuilder.lower(root.get("titulo")),
                            "%" + titulo.toLowerCase() + "%"
                    ));
                }

                if (nomeAutor != null) {
                    predicates.add(criteriaBuilder.like(
                            criteriaBuilder.lower(root.get("autor").get("nome")),
                            "%" + nomeAutor.toLowerCase() + "%"
                    ));
                }
                if (genero != null) {
                    predicates.add(criteriaBuilder.equal(root.get("genero"), genero.toString().toUpperCase()));
                }
                if (anoPublicacao != null) {
                    predicates.add(criteriaBuilder.equal(
                                    criteriaBuilder.function("to_char", String.class,
                            root.get("dataPublicacao"), criteriaBuilder.literal("YYYY")
                    ),
                            anoPublicacao.toString()
                    ));
                }

                return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
            };
        }
    }

