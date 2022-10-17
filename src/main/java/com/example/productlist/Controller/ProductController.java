package com.example.productlist.Controller;

import com.example.productlist.models.Product;
import com.example.productlist.Repository.ProductMongoRepository;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ProductController {

    private ProductMongoRepository productMongoRepository;

    @Autowired
    public ProductController(ProductMongoRepository productMongoRepository) {
        this.productMongoRepository = productMongoRepository;
    }

    @ApiOperation(value = "Get all products", notes = "Retrieves all products from the collection")
    @GetMapping("/products")
    public List<Product> getProducts(){
        return productMongoRepository.findAll();
    }

    @ApiOperation(value = "Add a product", notes = "Saves a product with the specified values")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully saved"),
            @ApiResponse(code = 400, message = "Empty fields found!")
    })
    @PostMapping("/products")
    public ResponseEntity<Object> add(Product product) {
        if(product.getName().isEmpty()){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        if(product.getId()==0) {
            product.setId(product.hashCode());
        }
        productMongoRepository.save(product);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

}
