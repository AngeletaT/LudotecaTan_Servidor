package com.ccsw.tutorial.service.author;

import java.util.List;

import org.springframework.data.domain.Page;

import com.ccsw.tutorial.dto.author.AuthorDto;
import com.ccsw.tutorial.dto.author.AuthorSearchDto;
import com.ccsw.tutorial.entities.Author;

/**
 * @author ccsw
 *
 */
public interface AuthorService {

    /**
     * Recupera un {@link Author} a través de su ID
     *
     * @param id PK de la entidad
     * @return {@link Author}
     */
    Author get(Long id);

    /**
     * Recupera un listado de autores {@link Author}
     *
     * @return {@link List} de {@link Author}
     */
    List<Author> findAll();

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
     * Método para eliminar un {@link Author}
     *
     * @param id PK de la entidad
     */
    void delete(Long id) throws Exception;

}