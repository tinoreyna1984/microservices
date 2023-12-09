package com.tinexlab.orders_service.events;

import com.tinexlab.orders_service.model.enums.OrderStatus;

public record OrderEvent(String orderNumber, int itemsCount, OrderStatus orderStatus) {
}
