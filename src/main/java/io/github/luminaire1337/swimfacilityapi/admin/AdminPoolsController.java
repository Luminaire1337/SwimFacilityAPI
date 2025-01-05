package io.github.luminaire1337.swimfacilityapi.admin;

import io.github.luminaire1337.swimfacilityapi.models.pool.Pool;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/pools")
@AllArgsConstructor
public class AdminPoolsController {
    private final AdminPoolsService adminPoolsService;

    @GetMapping("/")
    @Operation(summary = "Get all pools")
    @ApiResponse(description = "Returns an array of Pool objects", responseCode = "200")
    public ResponseEntity<Iterable<Pool>> getPools() {
        return ResponseEntity.status(HttpStatus.OK).body(this.adminPoolsService.getPools());
    }

    @PostMapping("/")
    @Operation(summary = "Register a new pool")
    @ApiResponse(description = "Returns Pool object if successful", responseCode = "201")
    @ApiResponse(description = "Registration failed", responseCode = "403")
    public ResponseEntity<Pool> registerPool(@RequestBody PoolDTO poolDTO) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(this.adminPoolsService.createPool(poolDTO));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
        }
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a pool by ID")
    @ApiResponse(description = "Returns Pool object if successful", responseCode = "200")
    @ApiResponse(description = "Pool not found", responseCode = "404")
    public ResponseEntity<Pool> getPool(@PathVariable String id) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(this.adminPoolsService.getPool(id));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a pool")
    @ApiResponse(description = "Returns Pool object if successful", responseCode = "200")
    @ApiResponse(description = "Update failed", responseCode = "403")
    public ResponseEntity<Pool> updatePool(@PathVariable String id, @RequestBody PoolDTO poolDTO) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(this.adminPoolsService.updatePool(id, poolDTO));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a pool")
    @ApiResponse(description = "Returns Pool object if successful", responseCode = "200")
    @ApiResponse(description = "Deletion failed", responseCode = "403")
    public ResponseEntity<Pool> deletePool(@PathVariable String id) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(this.adminPoolsService.deletePool(id));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
        }
    }
}
