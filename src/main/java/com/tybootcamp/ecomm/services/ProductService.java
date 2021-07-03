package com.tybootcamp.ecomm.services;

import com.tybootcamp.ecomm.entities.Product;
import com.tybootcamp.ecomm.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final SellerService sellerService;

    @Autowired
    public ProductService(ProductRepository productRepository, SellerService sellerService) {
        this.productRepository = productRepository;
        this.sellerService = sellerService;
    }

    private void checkProductConstraints(Product product) {
        //Check the constraints
        if (product.getName() == null || product.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("Name field cannot be empty");
        }
        if (product.getImages() == null || product.getImages().size() == 0) {
            throw new IllegalArgumentException("Product entity should contain at least one image url");
        }

        try {
            Long sellerId = product.getSeller().getId();
            sellerService.getSellerById(sellerId);
        } catch (EntityNotFoundException e) {
            throw new IllegalArgumentException("Seller of the specified product could not be found");
        }

        if (product.getFallIntoCategories().isEmpty()) {
            throw new IllegalArgumentException("No category is associated with the product");
        }
    }

    public Product getProductById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(EntityNotFoundException::new);
    }

    public Product addProduct(Product product) {
        checkProductConstraints(product);
        return productRepository.save(product);
    }

    public void deleteProduct(Long productId) {
        Product product = getProductById(productId);
        productRepository.delete(product);
    }

    public Product updateProduct(Product newProduct) {
        checkProductConstraints(newProduct);
        Long productId = newProduct.getId();
        Optional<Product> currentProduct = productRepository.findById(productId);
        if (currentProduct.isEmpty())
            throw new EntityNotFoundException("Specified product does not exist hence cannot be updated");

        return productRepository.save(newProduct);
    }

    public Iterable<Product> getAllProducts() {
        return productRepository.findAll();
    }


}
