package io.github.luminaire1337.swimfacilityapi.models.pool;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PoolRepository extends MongoRepository<Pool, String> {
}
