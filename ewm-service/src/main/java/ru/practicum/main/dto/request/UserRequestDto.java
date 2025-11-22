package ru.practicum.main.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserRequestDto {
    @Email(message = "Введите корректный email")
    @NotBlank(message = "Email не может быть пустым")
    @Size(min = 6, max = 254, message = "Минимум 6 и максимум 254 символов для email")
    private String email;

    @NotBlank(message = "Name не может быть пустым")
    @Size(min = 2, max = 250, message = "Минимум 2 символов и максимум 250 для имени")
    private String name;
}
