package io.github.luminaire1337.swimfacilityapi.admin;

import io.github.luminaire1337.swimfacilityapi.models.log.Log;
import io.github.luminaire1337.swimfacilityapi.models.log.LogRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class AdminService {
    private final LogRepository logRepository;

    public String generateReportCSV() {
        List<Log> logs = logRepository.findAll();
        StringBuilder csv = new StringBuilder();
        csv.append("id,timestamp,type,pool\n");
        for (Log log : logs) {
            csv.append(log.getId()).append(",");
            csv.append(log.getTimestamp()).append(",");
            csv.append(log.getType()).append(",");
            csv.append(log.getPool().getId()).append("\n");
        }
        return csv.toString();
    }
}
