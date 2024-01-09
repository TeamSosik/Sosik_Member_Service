package com.example.sosikmemberservice.model;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class Mail {
    private String address;
    private String title;
    private String message;
}
