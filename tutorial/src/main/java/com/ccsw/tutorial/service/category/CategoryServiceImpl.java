package com.ccsw.tutorial.service.category;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ccsw.tutorial.dto.category.CategoryDto;
import com.ccsw.tutorial.entities.Category;
import com.ccsw.tutorial.exceptions.category.CategoryNotFoundException;
import com.ccsw.tutorial.exceptions.category.InvalidCategoryException;
import com.ccsw.tutorial.repository.CategoryRepository;

import jakarta.transaction.Transactional;

/**
 * {@inheritDoc}
 */
@Service
@Transactional
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    CategoryRepository categoryRepository;

    /**
     * {@inheritDoc}
     */
    @Override
    public Category get(Long id) {
        return this.categoryRepository.findById(id)
                .orElseThrow(() -> new CategoryNotFoundException("Categoría no encontrada."));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Category> findAll() {
        return (List<Category>) this.categoryRepository.findAll();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void save(Long id, CategoryDto dto) {
        if (dto.getName() == null || dto.getName().isEmpty()) {
            throw new InvalidCategoryException("El nombre de la categoría es obligatorio.");
        }

        Category category;

        if (id == null) {
            category = new Category();
        } else {
            category = this.get(id);
        }

        category.setName(dto.getName());

        this.categoryRepository.save(category);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void delete(Long id) {
        if (!this.categoryRepository.existsById(id)) {
            throw new CategoryNotFoundException("Categoría no encontrada.");
        }

        this.categoryRepository.deleteById(id);
    }

}