package ru.practicum.main.dto.response;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class CompilationResponseDto {
    private Long id;
    private String title;
    private Boolean pinned;
    private List<EventResponseShortDto> events;
}
