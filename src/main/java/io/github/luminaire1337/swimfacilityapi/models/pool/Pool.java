package io.github.luminaire1337.swimfacilityapi.models.pool;

import io.github.luminaire1337.swimfacilityapi.models.log.Log;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "Pools")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Pool {
    @Id
    private String id;
    private Integer maxCapacity;
    private Integer currentCapacity;
    private String location;

    @DBRef
    private List<Log> logs;
}
