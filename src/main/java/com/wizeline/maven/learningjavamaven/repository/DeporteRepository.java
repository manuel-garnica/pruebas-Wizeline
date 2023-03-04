package com.wizeline.maven.learningjavamaven.repository;

import com.wizeline.maven.learningjavamaven.model.DeporteDTO;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface DeporteRepository extends MongoRepository<DeporteDTO, String> {
}
