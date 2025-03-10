package com.ccsw.tutorial.service.game;

import java.util.List;

import com.ccsw.tutorial.dto.game.GameDto;
import com.ccsw.tutorial.entities.Game;

/**
 * @author ccsw
 *
 */
public interface GameService {

    /**
     * Recupera los juegos filtrando opcionalmente por título y/o categoría
     *
     * @param title      título del juego
     * @param idCategory PK de la categoría
     * @return {@link List} de {@link Game}
     */
    List<Game> find(String title, Long idCategory);

    /**
     * Guarda o modifica un juego, dependiendo de si el identificador está o no
     * informado
     *
     * @param id  PK de la entidad
     * @param dto datos de la entidad
     */
    void save(Long id, GameDto dto);

}