package com.ccsw.tutorial.repository;

import org.springframework.data.repository.CrudRepository;

import com.ccsw.tutorial.entities.Category;

/**
 * @author ccsw
 *
 */
public interface CategoryRepository extends CrudRepository<Category, Long> {

}

