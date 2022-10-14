package com.example.productlist.Controller;

import com.example.productlist.models.List;
import com.example.productlist.models.ListResponse;
import com.example.productlist.models.Product;
import com.example.productlist.Repository.ListMongoRepository;
import com.example.productlist.Repository.ProductMongoRepository;
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
    private ListResponse listResponse;

    @Autowired
    public ListController(ListMongoRepository listMongoRepository, ProductMongoRepository productMongoRepository, ListResponse listResponse) {
        this.productMongoRepository = productMongoRepository;
        this.listMongoRepository = listMongoRepository;
        this.listResponse = listResponse;
    }

    @RequestMapping("/lists")
    public java.util.List<List> getLists() {
        return listMongoRepository.findAll();
    }

    @RequestMapping("/lists/{id}")
    public ResponseEntity<?> getList(@PathVariable("id") Long id){
        Optional<List> optionalList = listMongoRepository.findById(id);
        if(!optionalList.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        listResponse.setProductList(optionalList.get());
        listResponse.setTotalKcal(optionalList.get().getProductList().stream().mapToInt(Product::getKcal).sum());
        return new ResponseEntity<>(listResponse, HttpStatus.OK);
    }

    @PostMapping("/lists")
    public void add(List list) {
        if(list.getId()==0) {
            list.setId(list.hashCode());
        }
        listMongoRepository.save(list);
    }

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
        listResponse.setProductList(optionalList.get());
        listResponse.setTotalKcal(productList.stream().mapToInt(Product::getKcal).sum());
        return new ResponseEntity<>(listResponse, HttpStatus.OK);
    }

}
