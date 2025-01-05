package io.github.luminaire1337.swimfacilityapi.pools;

import io.github.luminaire1337.swimfacilityapi.models.log.Log;
import io.github.luminaire1337.swimfacilityapi.models.log.LogRepository;
import io.github.luminaire1337.swimfacilityapi.models.log.LogType;
import io.github.luminaire1337.swimfacilityapi.models.pool.Pool;
import io.github.luminaire1337.swimfacilityapi.models.pool.PoolRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class PoolsService {
    private final PoolRepository poolRepository;
    private final RedisTemplate<String, Object> redisTemplate;
    private final LogRepository logRepository;

    public Iterable<Pool> getPools() {
        return poolRepository.findAll();
    }

    public Pool getPool(String id) {
        return poolRepository.findById(id).orElseThrow(() -> new RuntimeException("Pool not found"));
    }

    public Integer getPoolOccupancy(String id) {
        return redisTemplate.opsForValue().get(id + ":occupancy") == null ? Integer.valueOf(0) : (Integer) redisTemplate.opsForValue().get(id + ":occupancy");
    }

    public void setPoolOccupancy(String id, Integer occupancy) {
        redisTemplate.opsForValue().set(id + ":occupancy", occupancy);
    }

    public Integer registerEntry(String id) {
        Pool pool = this.getPool(id);
        Integer occupancy = this.getPoolOccupancy(id);

        // Check if the pool is full
        if (occupancy >= pool.getMaxCapacity()) {
            throw new FullPoolException();
        }

        // Set the new occupancy
        this.setPoolOccupancy(id, occupancy + 1);

        // Log the entry
        Log log = Log.builder()
                .timestamp(LocalDateTime.now())
                .type(LogType.ENTRY)
                .pool(pool)
                .build();
        logRepository.save(log);

        // Return the new occupancy
        return occupancy + 1;
    }

    public Integer registerExit(String id) {
        Pool pool = this.getPool(id);
        Integer occupancy = this.getPoolOccupancy(id);

        // Check if the pool is empty
        if (occupancy <= 0) {
            throw new EmptyPoolException();
        }

        // Set the new occupancy
        this.setPoolOccupancy(id, occupancy - 1);

        // Log the exit
        Log log = Log.builder()
                .timestamp(LocalDateTime.now())
                .type(LogType.EXIT)
                .pool(pool)
                .build();
        logRepository.save(log);

        // Return the new occupancy
        return occupancy - 1;
    }

    public boolean canEnter(String id) {
        Pool pool = this.getPool(id);
        Integer occupancy = this.getPoolOccupancy(id);
        if (occupancy >= pool.getMaxCapacity()) {
            throw new FullPoolException();
        }
        return true;
    }
}
