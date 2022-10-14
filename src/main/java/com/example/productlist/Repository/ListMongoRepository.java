package com.example.productlist.Repository;

import com.example.productlist.models.List;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ListMongoRepository extends MongoRepository<List, Long> {
}
