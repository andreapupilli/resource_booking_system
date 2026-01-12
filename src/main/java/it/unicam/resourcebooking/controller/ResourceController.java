package it.unicam.resourcebooking.controller;

import it.unicam.resourcebooking.model.Resource;
import it.unicam.resourcebooking.service.ResourceService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import java.util.List;

@RestController
@RequestMapping("/api/resources")
public class ResourceController {

    private final ResourceService resourceService;

    public ResourceController(ResourceService resourceService) {
        this.resourceService = resourceService;
    }

    @GetMapping
    public List<Resource> list() {
        return resourceService.list();
    }

    @GetMapping("/{id}")
    public Resource get(@PathVariable Long id) {
        return resourceService.get(id);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public Resource create(@Valid @RequestBody Resource resource) {
        return resourceService.create(resource);
    }

    @PutMapping("/{id}")
    public Resource update(@PathVariable Long id, @Valid @RequestBody Resource updated) {
        return resourceService.update(id, updated);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        resourceService.delete(id);
    }
}
