package org.acme.kafka.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.acme.kafka.entity.Product;
import org.acme.kafka.repository.ProductRepository;

import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class ProductService {

    @Inject
    ProductRepository productRepository;

    // Tìm tất cả sản phẩm
    public List<Product> getAllProducts() {
        return productRepository.listAll();
    }

    // Tìm sản phẩm theo ID
    public Optional<Product> getProductById(Long id) {
        return productRepository.findByIdOptional(id);
    }

    // Tạo một sản phẩm mới
    @Transactional
    public Product createProduct(Product product) {
        productRepository.persist(product);
        return product;
    }

    // Cập nhật thông tin sản phẩm
    @Transactional
    public Product updateProduct(Long id, @Valid Product updatedProduct) {
        Optional<Product> optionalProduct = productRepository.findByIdOptional(id);
        if (optionalProduct.isEmpty()) {
            throw new IllegalArgumentException("Sản phẩm không tồn tại");
        }

        Product product = optionalProduct.get();
        product.symbol = updatedProduct.symbol;
        product.companyName = updatedProduct.companyName;
        product.currentPrice = updatedProduct.currentPrice;
        product.marketCap = updatedProduct.marketCap;
        product.peRatio = updatedProduct.peRatio;
        product.economic = updatedProduct.economic;
        productRepository.persist(product);
        return product;
    }

    // Xóa sản phẩm theo ID
    @Transactional
    public boolean deleteProduct(Long id) {
        Optional<Product> optionalProduct = productRepository.findByIdOptional(id);
        if (optionalProduct.isEmpty()) {
            throw new IllegalArgumentException("Sản phẩm không tồn tại");
        }
        productRepository.delete(optionalProduct.get());
        return true;
    }
}
