package com.love.service.category;

import java.util.List;
import java.util.Optional;

import com.love.exceptions.ResourceAlreadyExistsException;
import org.springframework.stereotype.Service;

import com.love.exceptions.ResourceNotFoundException;
import com.love.model.Category;
import com.love.repository.CategoryRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CategoryService implements ICategoryService{
	
	private CategoryRepository categoryRepository;

	@Override
	public Category getCategoryById(Long id) {
		return categoryRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Category not available with given id : " + id));
	}

	@Override
	public Category getCategoryByName(String name) {
		return Optional.ofNullable(categoryRepository.findByName(name))
				.orElseThrow(() -> new ResourceNotFoundException("Category not available with given name : " + name));
	}

	@Override
	public List<Category> getAllCategories() {
		return categoryRepository.findAll();
	}

	@Override
	public Category addCategory(Category category) {
		return Optional.of(category).filter(c -> !categoryRepository.existsByName(c.getName()))
				.map(categoryRepository :: save).orElseThrow(() -> new ResourceAlreadyExistsException("Category name already exists"));
	}

	@Override
	public Category updateCategory(Category category, Long id) {

		// We have to return the updated category but following way is not way
		/* categoryRepository.findById(id).ifPresentOrElse(
				existingCategory -> {
					existingCategory.setName(category.getName());
					return categoryRepository.save(existingCategory);
				},
				() -> {
					throw new ResourceNotFoundException("Category not found with given id " + id);
				}
		); */
		return Optional.ofNullable(getCategoryById(id)).map(oldCategory -> {
			oldCategory.setName(category.getName());
			return categoryRepository.save(oldCategory);
		}).orElseThrow(() -> new ResourceNotFoundException("Category not found with given id"));
	}

	@Override
	public void deleteCategoryById(Long id) {
		categoryRepository.findById(id)
						  .ifPresentOrElse(categoryRepository :: delete, () -> {
							  throw new ResourceNotFoundException("Category not available with given id : " + id);
						  });
	}
}
