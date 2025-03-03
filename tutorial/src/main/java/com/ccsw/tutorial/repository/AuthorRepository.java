package com.ccsw.tutorial.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import com.ccsw.tutorial.entities.Author;

/**
 * @author ccsw
 *
 */
public interface AuthorRepository extends CrudRepository<Author, Long> {

    /**
     * Método para recuperar un listado paginado de {@link Author}
     *
     * @param pageable pageable
     * @return {@link Page} de {@link Author}
     */
    Page<Author> findAll(Pageable pageable);

}