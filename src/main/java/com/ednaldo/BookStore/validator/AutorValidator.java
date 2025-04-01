package com.ednaldo.BookStore.validator;

import com.ednaldo.BookStore.entities.Autor;
import com.ednaldo.BookStore.exceptions.AutorAlreadyRegisteredException;
import com.ednaldo.BookStore.repositories.AutorRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Optional;
@RequiredArgsConstructor
@Component
public class AutorValidator {

    private static final Logger logger = LoggerFactory.getLogger(AutorValidator.class);
    private final AutorRepository autorRepository;

    /**
     * Valida se um autor pode ser cadastrado ou atualizado sem repetir dados.
     * @param autor Objeto do autor a ser validado.
     * @throws AutorAlreadyRegisteredException se um autor com os mesmos dados já existir.
     */
    public void validateAutor(Autor autor) {
        if (existAutorCreated(autor)) {
            logger.warn("Tentativa de cadastrar autor já existente: {}", autor.getNome());
            throw new AutorAlreadyRegisteredException("Autor já está cadastrado!");
        }
    }

    /**
     * Verifica se já existe um autor com o mesmo nome, data de nascimento e nacionalidade no banco.
     * @param autor Objeto do autor que está sendo validado.
     * @return true se um autor com os mesmos dados já existir (exceto se for o mesmo ID na atualização).
     */
    private boolean existAutorCreated(Autor autor) {
        Optional<Autor> autorEncontrado = autorRepository.findByNomeAndDataNascimentoAndNacionalidade(
                autor.getNome(),
                autor.getDataNascimento(),
                autor.getNacionalidade()
        );

        // Retorna true se encontrar um autor com os mesmos dados, mas com um ID diferente
        return autorEncontrado
                .map(a -> autor.getId() == null || !autor.getId().equals(a.getId()))
                .orElse(false);
    }
}


