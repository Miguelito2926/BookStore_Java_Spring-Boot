package com.ednaldo.BookStore.repositories;

import com.ednaldo.BookStore.entities.Autor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AutorRepository extends JpaRepository<Autor, UUID> {

}
