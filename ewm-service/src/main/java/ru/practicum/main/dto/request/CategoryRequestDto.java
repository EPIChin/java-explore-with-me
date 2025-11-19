package ru.practicum.main.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CategoryRequestDto {
    @NotBlank(message = "Name не может быть пустым")
    @Size(max = 50, message = "Символов максимум 50 для имени")
    private String name;
}
