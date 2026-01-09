package ru.practicum.main.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CommentRequestDto {

    @NotBlank
    @Size(min = 1, max = 2000)
    private String text;
}
