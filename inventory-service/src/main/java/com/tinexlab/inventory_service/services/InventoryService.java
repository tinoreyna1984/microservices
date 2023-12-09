package com.tinexlab.inventory_service.services;

import com.tinexlab.inventory_service.model.dtos.BaseResponse;
import com.tinexlab.inventory_service.model.dtos.OrderItemRequest;
import com.tinexlab.inventory_service.model.entities.Inventory;
import com.tinexlab.inventory_service.repositories.InventoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class InventoryService {

    private final InventoryRepository inventoryRepository;

    public boolean isInStock(String sku) {
        var inventory = inventoryRepository.findBySku(sku);

        return inventory.filter(value -> value.getQuantity() > 0).isPresent();
    }

    public BaseResponse areInStock(List<OrderItemRequest> orderItems) {

        var errorList = new ArrayList<String>();

        List<String> skus = orderItems.stream().map(OrderItemRequest::getSku).toList();

        List<Inventory> inventoryList = inventoryRepository.findBySkuIn(skus);

        orderItems.forEach(orderItem -> {
            var inventory = inventoryList.stream().filter(value -> value.getSku().equals(orderItem.getSku())).findFirst();
            if (inventory.isEmpty()) {
                errorList.add("El producto con sku " + orderItem.getSku() + " no existe");
            } else if (inventory.get().getQuantity() < orderItem.getQuantity()) {
                errorList.add("El producto con sku " + orderItem.getSku() + " tiene cantidad insuficiente");
            }
        });

        return errorList.size() > 0 ? new BaseResponse(errorList.toArray(new String[0])) : new BaseResponse(null);
    }
}
