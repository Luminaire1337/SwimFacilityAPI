package io.github.luminaire1337.swimfacilityapi.models.pool;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "Pools")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Pool {
    @Id
    private String id;
    private Integer maxCapacity;
    private String location;
}
