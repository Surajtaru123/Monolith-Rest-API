package com.love.service.product;

import java.util.List;

import com.love.dto.ProductDto;
import com.love.model.Product;
import com.love.request.AddProductRequest;
import com.love.request.UpdateProductRequest;

public interface IProductService {
	
	Product addProduct(AddProductRequest request);
	
	Product getProductById(Long id);
	
	void deleteProductById(Long id);
	
	Product updateProductById(UpdateProductRequest product, Long productId);
	
	List<Product> getAllProducts();
	
	List<Product> getProductsByCategory(String category);
	
	List<Product> getProductsByBrand(String brand);
	
	List<Product> getProductsByCategoryAndBrand(String category, String brand);
	
	List<Product> getProductsByName(String name);

	List<Product> getProductsByBrandAndName(String brand, String name);

	Long countProductsByBrandAndName(String brand, String name);

	List<ProductDto> getConvertedProducts(List<Product> products);

	ProductDto convertToProductDto(Product product);
}
