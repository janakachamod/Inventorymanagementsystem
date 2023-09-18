package com.programmingtechie.inventoryservice.controller;

import com.programmingtechie.inventoryservice.dto.InventoryRequest;
import com.programmingtechie.inventoryservice.dto.InventoryResponse;
import com.programmingtechie.inventoryservice.model.Inventory;
import com.programmingtechie.inventoryservice.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inventory")
@RequiredArgsConstructor
public class InventoryController {

    private final InventoryService inventoryService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createProduct(@RequestBody InventoryRequest inventoryRequest) {
        inventoryService.createProduct(inventoryRequest);
    }

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public List<InventoryResponse> isInStock(@RequestParam List<String> skuCode) {
        return inventoryService.isInStock(skuCode);
    }
    @GetMapping("/products")
    public List<Inventory> findAllProducts() {
        return inventoryService.getProducts();
    }
    @PutMapping("/update")
    public Inventory updateProduct(@RequestBody Inventory inventory) {
        return inventoryService.updateProduct(inventory);
    }

    @DeleteMapping("/delete/{id}")
    public String deleteProduct(@PathVariable long id) {
        return inventoryService.deleteProduct(id);
    }

}
