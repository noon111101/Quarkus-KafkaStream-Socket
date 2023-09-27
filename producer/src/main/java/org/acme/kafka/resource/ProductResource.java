package org.acme.kafka.resource;

import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.acme.kafka.entity.Product;
import org.acme.kafka.service.ProductService;


import java.util.List;
import java.util.Optional;

@Path("/product")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ProductResource {

    @Inject
    ProductService productService;

    // Lấy tất cả sản phẩm
    @GET
    public List<Product> getAllProducts() {
        return productService.getAllProducts();
    }

    // Lấy sản phẩm theo ID
    @GET
    @Path("/{id}")
    public Product getProductById(@PathParam("id") Long id) {
        Optional<Product> product = productService.getProductById(id);
        if (product.isEmpty()) {
            throw new NotFoundException("Sản phẩm không tồn tại");
        }
        return product.get();
    }

    // Tạo mới sản phẩm
    @POST
    public Product createProduct(@Valid Product product) {
        return productService.createProduct(product);
    }

    // Cập nhật thông tin sản phẩm
    @PUT
    @Path("/{id}")
    public Product updateProduct(@PathParam("id") Long id, @Valid Product updatedProduct) {
        Optional<Product> product = productService.getProductById(id);
        if (product.isEmpty()) {
            throw new NotFoundException("Sản phẩm không tồn tại");
        }
        return productService.updateProduct(id, updatedProduct);
    }

    // Xóa sản phẩm theo ID
    @DELETE
    @Path("/{id}")
    public boolean deleteProduct(@PathParam("id") Long id) {
        Optional<Product> product = productService.getProductById(id);
        if (product.isEmpty()) {
            throw new NotFoundException("Sản phẩm không tồn tại");
        }
        return productService.deleteProduct(id);
    }
}
