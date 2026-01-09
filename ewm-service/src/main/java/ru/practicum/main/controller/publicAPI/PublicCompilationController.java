package ru.practicum.main.controller.publicAPI;

import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.practicum.main.dto.response.CompilationResponseDto;
import ru.practicum.main.facade.CompilationFacade;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/compilations")
public class PublicCompilationController {

    private final CompilationFacade facade;

    @GetMapping
    public List<CompilationResponseDto> getAll(@RequestParam(required = false) Boolean pinned,
                                               @RequestParam(defaultValue = "0") @PositiveOrZero int from,
                                               @RequestParam(defaultValue = "10") @Positive int size) {
        return facade.getCompilations(pinned, from, size);
    }

    @GetMapping("/{compId}")
    public CompilationResponseDto getOne(@PathVariable Long compId) {
        return facade.getCompilation(compId);
    }
}
