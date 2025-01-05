package io.github.luminaire1337.swimfacilityapi.admin;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PoolDTO {
    private Integer maxCapacity;
    private Integer currentCapacity;
    private String location;
}
