package ru.practicum.main.controller.publicC;

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
                                               @RequestParam(defaultValue = "0") int from,
                                               @RequestParam(defaultValue = "10") int size) {
        return facade.getCompilations(pinned, from, size);
    }

    @GetMapping("/{compId}")
    public CompilationResponseDto getOne(@PathVariable Long compId) {
        return facade.getCompilation(compId);
    }
}
