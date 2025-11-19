package ru.practicum.main.controller.publicC;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.practicum.main.dto.response.CategoryResponseDto;
import ru.practicum.main.facade.CategoryFacade;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/categories")
public class PublicCategoryController {

    private final CategoryFacade facade;

    @GetMapping
    public List<CategoryResponseDto> getAll(@RequestParam(defaultValue = "0") int from,
                                            @RequestParam(defaultValue = "10") int size) {
        return facade.getAll(from, size);
    }

    @GetMapping("/{catId}")
    public CategoryResponseDto getOne(@PathVariable Long catId) {
        return facade.getOne(catId);
    }
}
