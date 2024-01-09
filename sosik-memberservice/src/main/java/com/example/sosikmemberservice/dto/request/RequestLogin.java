package com.example.sosikmemberservice.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record RequestLogin(@NotNull(message = "Please enter in email format")
                           String email,
                           @NotNull
                           @Size(min =8,max = 16,
                                 message = "Password must be at least 8 characters and not more than 16 characters.")
                           String password) {
}
