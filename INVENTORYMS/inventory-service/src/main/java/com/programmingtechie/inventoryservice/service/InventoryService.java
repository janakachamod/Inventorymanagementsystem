package com.programmingtechie.inventoryservice.service;

import com.programmingtechie.inventoryservice.dto.InventoryRequest;
import com.programmingtechie.inventoryservice.dto.InventoryResponse;
import com.programmingtechie.inventoryservice.model.Inventory;
import com.programmingtechie.inventoryservice.repository.InventoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static jdk.nashorn.internal.runtime.regexp.joni.Config.log;

@Service
@RequiredArgsConstructor
@Slf4j
public class InventoryService {

    private final InventoryRepository inventoryRepository;

    @Transactional(readOnly = true)
    @SneakyThrows
    public List<InventoryResponse> isInStock(List<String> skuCode) {
        log.info("wait started");
       // Thread.sleep(10000);
        //log.info("wait Ended");
        return inventoryRepository.findBySkuCodeIn(skuCode).stream()
                .map(inventory ->
                    InventoryResponse.builder()
                            .skuCode(inventory.getSkuCode())
                            .isInStock(inventory.getQuantity()>0)
                            .build()

                )
                .collect(Collectors.toList());
    }

    public void createProduct(InventoryRequest inventoryRequest)
    {
        Inventory inventory = Inventory.builder()
                .id(inventoryRequest.getId())
                .skuCode(inventoryRequest.getSkuCode())
                .quantity(inventoryRequest.getQuantity())
                .build();

        inventoryRepository.save(inventory);
    }

    public List<Inventory> getProducts()
    {
        return inventoryRepository.findAll();
    }

    public Inventory updateProduct(Inventory inventory)
    {
        Inventory existingProduct = inventoryRepository.findById(inventory.getId()).orElse(null);
        existingProduct.setId(inventory.getId());
        existingProduct.setSkuCode(inventory.getSkuCode());
        existingProduct.setQuantity(inventory.getQuantity());
        return inventoryRepository.save(existingProduct);
    }

    public String deleteProduct(long id)
    {
        inventoryRepository.deleteById(id);
        return "product removed !! " + id;
    }
}
