package com.ednaldo.BookStore.services;

import com.ednaldo.BookStore.entities.Livro;
import com.ednaldo.BookStore.repositories.LivroRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
@RequiredArgsConstructor
@Service
public class LivroService {

    private final LivroRepository livroRepository;

    public Object cadastrarLivro(Livro livro) {
        return null;
    }
}
