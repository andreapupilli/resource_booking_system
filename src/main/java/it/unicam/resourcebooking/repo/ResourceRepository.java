package it.unicam.resourcebooking.repo;

import it.unicam.resourcebooking.model.Resource;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ResourceRepository extends JpaRepository<Resource, Long> {
}
