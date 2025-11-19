package ru.practicum.main.controller.adminC;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.main.dto.request.CompilationRequestDto;
import ru.practicum.main.dto.response.CompilationResponseDto;
import ru.practicum.main.dto.update.CompilationUpdateDto;
import ru.practicum.main.facade.CompilationFacade;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/compilations")
public class AdminCompilationController {

    private final CompilationFacade facade;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CompilationResponseDto create(@Valid @RequestBody CompilationRequestDto dto) {
        return facade.createCompilation(dto);
    }

    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public CompilationResponseDto update(@PathVariable("id") Long id,
                                         @Valid @RequestBody CompilationUpdateDto dto) {
        return facade.updateCompilation(id, dto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") Long id) {
        facade.deleteCompilation(id);
    }
}
