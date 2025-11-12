package ru.practicum.dto;

import lombok.*;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EndpointHitDto {
    @NotBlank(message = "App не может быть пустым")
    private String app;
    @NotBlank(message = "Uri не может быть пустым")
    private String uri;
    @NotBlank(message = "IP не может быть пустым")
    private String ip;
    @NotNull(message = "Timestamp обязателен")
    private String timestamp;
}