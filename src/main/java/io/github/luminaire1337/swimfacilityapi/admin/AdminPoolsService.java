package io.github.luminaire1337.swimfacilityapi.admin;

import io.github.luminaire1337.swimfacilityapi.models.pool.Pool;
import io.github.luminaire1337.swimfacilityapi.models.pool.PoolRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AdminPoolsService {
    private final PoolRepository poolRepository;

    public Iterable<Pool> getPools() {
        return poolRepository.findAll();
    }

    public Pool createPool(PoolDTO poolDTO) {
        Pool pool = Pool.builder()
                .maxCapacity(poolDTO.getMaxCapacity())
                .currentCapacity(poolDTO.getCurrentCapacity())
                .location(poolDTO.getLocation())
                .build();

        return poolRepository.save(pool);
    }

    public Pool getPool(String id) {
        return poolRepository.findById(id).orElseThrow(() -> new RuntimeException("Pool not found"));
    }

    public Pool updatePool(String id, PoolDTO poolDTO) {
        Pool pool = getPool(id);
        pool.setMaxCapacity(poolDTO.getMaxCapacity());
        pool.setCurrentCapacity(poolDTO.getCurrentCapacity());
        pool.setLocation(poolDTO.getLocation());
        return poolRepository.save(pool);
    }

    public Pool deletePool(String id) {
        Pool pool = getPool(id);
        poolRepository.deleteById(id);
        return pool;
    }
}
