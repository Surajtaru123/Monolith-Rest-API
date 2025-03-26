package com.love.controller;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;

import java.util.List;

import com.love.dto.ProductDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.love.exceptions.ResourceNotFoundException;
import com.love.model.Product;
import com.love.request.AddProductRequest;
import com.love.request.UpdateProductRequest;
import com.love.response.ApiResponse;
import com.love.service.product.IProductService;


@RestController
@RequestMapping("${api.prefix}/products")
public class ProductController {

    private IProductService iProductService;

    @Autowired
    public IProductService setIProductService(IProductService iProductService) {
        this.iProductService = iProductService;
        return this.iProductService;
    }

    @GetMapping("/all")
    public ResponseEntity<ApiResponse> getAllProducts() {
        List<Product> allProducts = iProductService.getAllProducts();
        List<ProductDto> convertedProducts = iProductService.getConvertedProducts(allProducts);
        return ResponseEntity.ok(new ApiResponse("Success!", convertedProducts));
    }

    @GetMapping("/get-product-by-id/{productId}")
    public ResponseEntity<ApiResponse> getAProductById(@PathVariable Long productId) {
        try {
            Product product = iProductService.getProductById(productId);
            ProductDto productDto = iProductService.convertToProductDto(product);
            return ResponseEntity.ok(new ApiResponse("Success!", productDto));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @PostMapping("/add")
    public ResponseEntity<ApiResponse> addProduct(@RequestBody AddProductRequest product) {
        try {
            Product savedProduct = iProductService.addProduct(product);
            ProductDto productDto = iProductService.convertToProductDto(savedProduct);
            return ResponseEntity.ok(new ApiResponse("product added!", productDto));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @PutMapping("/update/{productId}")
    public ResponseEntity<ApiResponse> updateAProduct(@RequestBody UpdateProductRequest updateProductRequest, @PathVariable Long productId) {
        try {
            Product updatedProduct = iProductService.updateProductById(updateProductRequest, productId);
            ProductDto productDto = iProductService.convertToProductDto(updatedProduct);
            return ResponseEntity.ok(new ApiResponse("product update success!", productDto));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @DeleteMapping("/delete/{productId}")
    public ResponseEntity<ApiResponse> deleteAProductById(@PathVariable Long productId) {
        try {
            iProductService.deleteProductById(productId);
            return ResponseEntity.ok(new ApiResponse("Product Delete success", null));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @GetMapping("/get-products-by-brand-and-name/{productBrand}/{productName}")
    public ResponseEntity<ApiResponse> getAProductByBrandAndName(@PathVariable String productBrand, @PathVariable String productName) {
        try {
            List<Product> products = iProductService.getProductsByBrandAndName(productBrand, productName);
            List<ProductDto> convertedProducts = iProductService.getConvertedProducts(products);
            if (convertedProducts.isEmpty()) {
                return ResponseEntity.status(NOT_FOUND).body(new ApiResponse("No Products Found", null));
            }
            return ResponseEntity.ok(new ApiResponse("Success", convertedProducts));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @GetMapping("/get-products-by-category-and-brand/{productCategory}/{productBrand}")
    public ResponseEntity<ApiResponse> getAllProductsByCategoryAndBrand(@PathVariable String productCategory, @PathVariable String productBrand) {
        try {
            List<Product> products = iProductService.getProductsByCategoryAndBrand(productCategory, productBrand);

            if (products.isEmpty()) {
                return ResponseEntity.status(NOT_FOUND).body(new ApiResponse("No Products Found", null));
            }

            List<ProductDto> convertedProducts = iProductService.getConvertedProducts(products);

            return ResponseEntity.ok(new ApiResponse("Success", convertedProducts));

        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @GetMapping("/get-products-by-brand/{productBrand}")
    public ResponseEntity<ApiResponse> getAllProductsByBrand(@PathVariable String productBrand) {
        try {
            List<Product> products = iProductService.getProductsByBrand(productBrand);

            if (products.isEmpty()) {
                return ResponseEntity.status(NOT_FOUND).body(new ApiResponse("No Products Found", null));
            }
            return ResponseEntity.ok(new ApiResponse("Success", products));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @GetMapping("/get-products-by-name/{productName}")
    public ResponseEntity<ApiResponse> getAllProductsByName(@PathVariable String productName) {
        try {
            List<Product> products = iProductService.getProductsByName(productName);

            if (products.isEmpty()) {
                return ResponseEntity.status(NOT_FOUND).body(new ApiResponse("No Products Found", null));
            }
            List<ProductDto> convertedProducts = iProductService.getConvertedProducts(products);
            return ResponseEntity.ok(new ApiResponse("Success", convertedProducts));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @GetMapping("/get-products-by-category/{productCategory}")
    public ResponseEntity<ApiResponse> getAllProductsByCategory(@PathVariable String productCategory) {
        try {
            List<Product> products = iProductService.getProductsByCategory(productCategory);

            if (products.isEmpty()) {
                return ResponseEntity.status(NOT_FOUND).body(new ApiResponse("No Products Found", null));
            }
            List<ProductDto> convertedProducts = iProductService.getConvertedProducts(products);
            return ResponseEntity.ok(new ApiResponse("Success", convertedProducts));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @GetMapping("/count-products-by-brand-and-name/{productBrand}/{productName}")
    public ResponseEntity<ApiResponse> countProductsByBrandAndName(@PathVariable String productBrand, @PathVariable String productName) {
        try {
            Long productCount = iProductService.countProductsByBrandAndName(productBrand, productName);
            return ResponseEntity.ok(new ApiResponse("Product count!", productCount));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(), null));
        }
    }
}
