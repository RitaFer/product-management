package com.rita.product_management.entrypoint.api.config;

import java.time.LocalDateTime;

public record ErrorMessage(
        Integer status,
        String message,
        LocalDateTime timestamp
) {
}
