package com.ccsw.tutorial.service.author;

import org.springframework.data.domain.Page;

import com.ccsw.tutorial.dto.AuthorDto;
import com.ccsw.tutorial.dto.AuthorSearchDto;
import com.ccsw.tutorial.entities.Author;

/**
 * @author ccsw
 *
 */
public interface AuthorService {

    /**
     * Método para recuperar un listado paginado de {@link Author}
     *
     * @param dto dto de búsqueda
     * @return {@link Page} de {@link Author}
     */
    Page<Author> findPage(AuthorSearchDto dto);

    /**
     * Método para crear o actualizar un {@link Author}
     *
     * @param id  PK de la entidad
     * @param dto datos de la entidad
     */
    void save(Long id, AuthorDto dto);

    /**
     * Método para crear o actualizar un {@link Author}
     *
     * @param id PK de la entidad
     */
    void delete(Long id) throws Exception;

}