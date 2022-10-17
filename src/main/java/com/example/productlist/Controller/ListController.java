package com.example.productlist.Controller;

import com.example.productlist.models.List;
import com.example.productlist.models.ListResponse;
import com.example.productlist.models.Product;
import com.example.productlist.Repository.ListMongoRepository;
import com.example.productlist.Repository.ProductMongoRepository;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Optional;

@RestController
public class ListController {

    private ListMongoRepository listMongoRepository;
    private ProductMongoRepository productMongoRepository;

    @Autowired
    public ListController(ListMongoRepository listMongoRepository, ProductMongoRepository productMongoRepository) {
        this.productMongoRepository = productMongoRepository;
        this.listMongoRepository = listMongoRepository;
    }

    @ApiOperation(value = "Get all lists", notes = "Get all lists of the collection")
    @GetMapping("/lists")
    public java.util.List<List> getLists() {
        return listMongoRepository.findAll();
    }

    @ApiOperation(value = "Get a list by id", notes = "Returns a list with the specified id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved"),
            @ApiResponse(code = 404, message = "Not found - The list was not found")
    })
    @GetMapping(value = "/lists/{id}")
    public ResponseEntity<?> getList(@PathVariable("id") Long id){
        Optional<List> optionalList = listMongoRepository.findById(id);
        if(!optionalList.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return new ResponseEntity<>(new ListResponse(optionalList.get(),
                optionalList.get().getProductList().stream().mapToInt(Product::getKcal).sum()),
                HttpStatus.OK);
    }

    @ApiOperation(value = "Add a list", notes = "Saves a list with the specified values")
    @PostMapping("/lists")
    public ResponseEntity<Object> add(List list) {
        if(list.getName().isEmpty()){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        if(list.getId()==0) {
            list.setId(list.hashCode());
        }
        listMongoRepository.save(list);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @ApiOperation(value = "Add a product to the specified list", notes = "Saves a product into the specified list and returns the list with total calories")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully saved"),
            @ApiResponse(code = 404, message = "Not found - The list was not found")
    })
    @PostMapping("/lists/addProduct")
    public ResponseEntity<?> add(long productId, long listId) {
        Optional<Product> optionalProduct = productMongoRepository.findById(productId);
        Optional<List> optionalList = listMongoRepository.findById(listId);
        if(!optionalList.isPresent() || !optionalProduct.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        ArrayList<Product> productList = (ArrayList<Product>) optionalList.get().getProductList();
        productList.add(optionalProduct.get());
        optionalList.get().setProductList(productList);
        listMongoRepository.save(optionalList.get());
        return new ResponseEntity<>(new ListResponse(optionalList.get(), productList.stream().mapToInt(Product::getKcal).sum()), HttpStatus.OK);
    }

}
