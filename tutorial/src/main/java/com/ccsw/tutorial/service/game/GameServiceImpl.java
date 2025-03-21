package com.ccsw.tutorial.service.game;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.ccsw.tutorial.common.criteria.SearchCriteria;
import com.ccsw.tutorial.dto.game.GameDto;
import com.ccsw.tutorial.entities.Game;
import com.ccsw.tutorial.exceptions.game.GameNotFoundException;
import com.ccsw.tutorial.exceptions.game.InvalidGameException;
import com.ccsw.tutorial.infrastructure.specifications.GameSpecification;
import com.ccsw.tutorial.repository.GameRepository;
import com.ccsw.tutorial.service.author.AuthorService;
import com.ccsw.tutorial.service.category.CategoryService;

import jakarta.transaction.Transactional;

/**
 * {@inheritDoc}
 */
@Service
@Transactional
public class GameServiceImpl implements GameService {

    @Autowired
    GameRepository gameRepository;

    @Autowired
    AuthorService authorService;

    @Autowired
    CategoryService categoryService;

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Game> find(String title, Long idCategory) {
        GameSpecification titleSpec = new GameSpecification(new SearchCriteria("title", ":", title));
        GameSpecification categorySpec = new GameSpecification(new SearchCriteria("category.id", ":", idCategory));

        Specification<Game> spec = Specification.where(titleSpec).and(categorySpec);

        return this.gameRepository.findAll(spec);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void save(Long id, GameDto dto) {
        if (dto.getTitle() == null || dto.getTitle().isEmpty()) {
            throw new InvalidGameException("El título del juego es obligatorio.");
        }

        Game game;

        if (id == null) {
            game = new Game();
        } else {
            game = this.gameRepository.findById(id)
                    .orElseThrow(() -> new GameNotFoundException("Juego no encontrado."));
        }

        BeanUtils.copyProperties(dto, game, "id", "author", "category");

        game.setAuthor(authorService.get(dto.getAuthor().getId()));
        game.setCategory(categoryService.get(dto.getCategory().getId()));

        this.gameRepository.save(game);
    }

}