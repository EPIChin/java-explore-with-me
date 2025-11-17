package ru.practicum.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Arrays;
import java.util.Objects;

@Data
@AllArgsConstructor
public class RequestParamDto {
    @NotNull(message = "Параметр start обязателен")
    @Pattern(regexp = "\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2}",
            message = "Формат start: yyyy-MM-dd HH:mm:ss")
    private final String start;
    @NotNull(message = "Параметр end обязателен")
    @Pattern(regexp = "\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2}",
            message = "Формат end: yyyy-MM-dd HH:mm:ss")
    private final String end;
    private final String[] uris;
    private final boolean unique;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RequestParamDto that = (RequestParamDto) o;
        return unique == that.unique &&
                Objects.equals(start, that.start) &&
                Objects.equals(end, that.end) &&
                Arrays.equals(uris, that.uris);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(start, end, unique);
        result = 31 * result + Arrays.hashCode(uris);
        return result;
    }
}