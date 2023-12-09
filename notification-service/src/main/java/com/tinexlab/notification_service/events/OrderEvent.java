package com.tinexlab.notification_service.events;

import com.tinexlab.notification_service.model.enums.OrderStatus;

public record OrderEvent(String orderNumber, int itemsCount, OrderStatus orderStatus) {
}
