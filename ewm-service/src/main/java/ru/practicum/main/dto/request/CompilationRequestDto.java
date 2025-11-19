package ru.practicum.main.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CompilationRequestDto {
    @Size(min = 1, max = 50)
    @NotBlank
    private String title;
    private Boolean pinned;
    private List<Long> events;
}
