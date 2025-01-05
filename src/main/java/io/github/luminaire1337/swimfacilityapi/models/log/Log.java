package io.github.luminaire1337.swimfacilityapi.models.log;

import io.github.luminaire1337.swimfacilityapi.models.pool.Pool;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "Logs")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Log {
    @Id
    private String id;
    private LocalDateTime timestamp;
    private LogType type;

    @DBRef
    private Pool pool;
}
