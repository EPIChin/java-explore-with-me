package ru.practicum.statsdto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EndpointHitRequestDto {
    private Long id;
    @NotBlank(message = "Идентификатор сервиса не может быть пустым")
    private String app;
    @NotBlank(message = "URI не может быть пустым")
    private String uri;
    @NotBlank(message = "IP-адрес пользователя не может быть пустым")
    private String ip;
    @NotBlank(message = "Дата и время не могут быть пустыми")
    private String timestamp;
}
