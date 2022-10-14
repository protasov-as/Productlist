package com.example.productlist.Repository;

import com.example.productlist.models.Product;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProductMongoRepository extends MongoRepository<Product, Long> {
}
