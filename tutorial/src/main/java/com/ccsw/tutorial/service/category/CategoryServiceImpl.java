package com.ccsw.tutorial.service.category;

import com.ccsw.tutorial.entities.Category;
import com.ccsw.tutorial.dto.CategoryDto;

import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import com.ccsw.tutorial.repository.CategoryRepository;

/**
 * @author ccsw
 *
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
    public List<Category> findAll() {

        return (List<Category>) this.categoryRepository.findAll();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void save(Long id, CategoryDto dto) {

        Category category;

        if (id == null) {
            category = new Category();
        } else {
            category = this.categoryRepository.findById(id).orElseThrow(() -> new RuntimeException("Category not found"));
        }

        category.setName(dto.getName());

        this.categoryRepository.save(category);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void delete(Long id) throws Exception {

        if(this.categoryRepository.findById(id).orElse(null) == null) {
            throw new Exception("Category not found");
        } else {
            this.categoryRepository.deleteById(id);
        }

    }

}