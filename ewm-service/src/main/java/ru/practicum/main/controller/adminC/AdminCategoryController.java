package ru.practicum.main.controller.adminC;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.main.dto.request.CategoryRequestDto;
import ru.practicum.main.dto.response.CategoryResponseDto;
import ru.practicum.main.dto.update.CategoryUpdateDto;
import ru.practicum.main.facade.CategoryFacade;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/categories")
public class AdminCategoryController {

    private final CategoryFacade facade;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CategoryResponseDto create(@Valid @RequestBody CategoryRequestDto dto) {
        return facade.create(dto);
    }

    @PatchMapping("/{catId}")
    @ResponseStatus(HttpStatus.OK)
    public CategoryResponseDto update(@PathVariable Long catId,
                                      @Valid @RequestBody CategoryUpdateDto dto) {
        return facade.update(catId, dto);
    }

    @DeleteMapping("/{catId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long catId) {
        facade.delete(catId);
    }
}
