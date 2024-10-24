package com.employee.advatixAPI.repository.Lpn;

import com.employee.advatixAPI.entity.manifest.ManifestMapping;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ManifestRepository extends MongoRepository<ManifestMapping, Integer> {
}
