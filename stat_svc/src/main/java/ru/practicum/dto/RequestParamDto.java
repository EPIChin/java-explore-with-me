package ru.practicum.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.Objects;

@Getter
@AllArgsConstructor
public class RequestParamDto {
    private final String start;
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