package ru.practicum.main.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class EventResponseShortDto {
    private Long id;
    private String title;
    private String annotation;
    private Long confirmedRequests;
    private Long views;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime eventDate;
    private UserResponseShortDto initiator;
    private Boolean paid;
    private CategoryResponseDto category;
}
