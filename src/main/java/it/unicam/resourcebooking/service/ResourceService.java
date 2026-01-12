package it.unicam.resourcebooking.service;

import it.unicam.resourcebooking.exception.ApiException;
import it.unicam.resourcebooking.model.Resource;
import it.unicam.resourcebooking.repo.ResourceRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ResourceService {

    private final ResourceRepository resourceRepository;

    public ResourceService(ResourceRepository resourceRepository) {
        this.resourceRepository = resourceRepository;
    }

    @Transactional(readOnly = true)
    public List<Resource> list() {
        return resourceRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Resource get(Long id) {
        return resourceRepository.findById(id)
                .orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND, "Resource not found: " + id));
    }

    @Transactional
    public Resource create(Resource incoming) {
        // Evita che un client possa forzare un ID e causare update non voluti.
        Resource r = new Resource();
        r.setName(incoming.getName());
        r.setType(incoming.getType());
        r.setLocation(incoming.getLocation());
        r.setDescription(incoming.getDescription());
        r.setActive(incoming.isActive());
        return resourceRepository.save(r);
    }

    @Transactional
    public Resource update(Long id, Resource updated) {
        Resource existing = get(id);
        existing.setName(updated.getName());
        existing.setType(updated.getType());
        existing.setLocation(updated.getLocation());
        existing.setDescription(updated.getDescription());
        existing.setActive(updated.isActive());
        return resourceRepository.save(existing);
    }

    @Transactional
    public void delete(Long id) {
        if (!resourceRepository.existsById(id)) {
            throw new ApiException(HttpStatus.NOT_FOUND, "Resource not found: " + id);
        }
        resourceRepository.deleteById(id);
    }
}
