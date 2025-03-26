package com.love.service.product;

import java.util.List;
import java.util.Optional;

import com.love.dto.ImageDto;
import com.love.dto.ProductDto;
import com.love.model.Image;
import com.love.repository.ImageRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.love.exceptions.ResourceNotFoundException;
import com.love.model.Category;
import com.love.model.Product;
import com.love.repository.CategoryRepository;
import com.love.repository.ProductRepository;
import com.love.request.AddProductRequest;
import com.love.request.UpdateProductRequest;

@RequiredArgsConstructor
@Service
public class ProductService implements IProductService {
	
	private final ProductRepository productRepository;
	private final CategoryRepository categoryRepository;
	private final ImageRepository imageRepository;
	private final ModelMapper modelMapper;
	
	@Override
	public Product addProduct(AddProductRequest request) {
		
		/*
		 	We have to save the product with some category
		 	Check if the category found in the DB
		 	If yes, then set the category for the new product
		 	If no, then create new category using request.getCategory() 
		 	Then save the product with this newly created category
		*/
		
		Category category = Optional.ofNullable(
				categoryRepository.findByName(request.getCategory().getName()))
									.orElseGet(() -> {
				Category newCategory = new Category(request.getCategory().getName());
				return categoryRepository.save(newCategory);
		});
		
		request.setCategory(category);
		return productRepository.save(createProduct(request, category));
	}
	
	// Helper method for addProduct method
	private Product createProduct(AddProductRequest request, Category category) {
		return new Product(
				request.getName(),
				request.getBrand(),
				request.getPrice(),
				request.getInventory(),
				request.getDescription(),
				category
				);
	}

	@Override
	public Product getProductById(Long id) {
		return productRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("product not available with given id" + id));
	}

	@Override
	public void deleteProductById(Long id) {
		productRepository.findById(id)
			.ifPresentOrElse(productRepository :: delete, () -> {
				throw new ResourceNotFoundException("product not available with given id" + id);
			});
		
	}

	@Override
	public Product updateProductById(UpdateProductRequest request, Long productId) {
		
		return productRepository.findById(productId)
								.map(existingProduct -> updateExistingProduct(existingProduct, request))
								.map(productRepository :: save)
								.orElseThrow(() -> new ResourceNotFoundException("product not available with given id" + productId));
	}
	
	// Helper method for updateProductById() method
	private Product updateExistingProduct(Product existingProduct, UpdateProductRequest request) {
		
		existingProduct.setName(request.getName());
		existingProduct.setBrand(request.getBrand());
		existingProduct.setPrice(request.getPrice());
		existingProduct.setInventory(request.getInventory());
		existingProduct.setDescription(request.getDescription());
		Category category = categoryRepository.findByName(request.getCategory().getName());
		existingProduct.setCategory(category);
		return existingProduct;
	}

	@Override
	public List<Product> getAllProducts() {
		return productRepository.findAll();
	}

	@Override
	public List<Product> getProductsByCategory(String category) {
		return productRepository.findByCategoryName(category);
	}

	@Override
	public List<Product> getProductsByBrand(String brand) {
		return productRepository.findByBrand(brand);
	}

	@Override
	public List<Product> getProductsByCategoryAndBrand(String category, String brand) {
		return productRepository.findByCategoryNameAndBrand(category, brand);
	}

	@Override
	public List<Product> getProductsByName(String name) {
		return productRepository.findByName(name);
	}

	@Override
	public List<Product> getProductsByBrandAndName(String brand, String name) {
		return productRepository.findByBrandAndName(brand, name);
	}

	@Override
	public Long countProductsByBrandAndName(String brand, String name) {
		return productRepository.countByBrandAndName(brand, name);
	}

	@Override
	public List<ProductDto> getConvertedProducts(List<Product> products) {
		return products.stream().map(this::convertToProductDto).toList();
	}

	@Override
	public ProductDto convertToProductDto(Product product) {
		ProductDto productDto = modelMapper.map(product, ProductDto.class);
		List<Image> images = imageRepository.findByProductId(product.getId());
		List<ImageDto> imageDtos = images.stream()
				.map(image -> modelMapper.map(image, ImageDto.class))
				.toList();
		productDto.setImages(imageDtos);
		return productDto;
	}
}
