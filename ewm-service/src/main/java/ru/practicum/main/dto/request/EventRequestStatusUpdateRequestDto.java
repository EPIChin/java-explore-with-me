package ru.practicum.main.dto.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import ru.practicum.main.common.Status;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EventRequestStatusUpdateRequestDto {
    @NotEmpty
    private List<Long> requestIds;
    @NotNull
    private Status status;
}
