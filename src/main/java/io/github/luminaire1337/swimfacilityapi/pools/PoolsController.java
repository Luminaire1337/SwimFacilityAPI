package io.github.luminaire1337.swimfacilityapi.pools;

import io.github.luminaire1337.swimfacilityapi.models.pool.Pool;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/pools")
@AllArgsConstructor
public class PoolsController {
    private final PoolsService poolsService;

    @GetMapping("/")
    @Operation(summary = "Get all pools")
    @ApiResponse(description = "Returns an array of Pool objects", responseCode = "200")
    public ResponseEntity<Iterable<Pool>> getPools() {
        return ResponseEntity.status(HttpStatus.OK).body(this.poolsService.getPools());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a pool by ID")
    @ApiResponse(description = "Returns Pool object if successful", responseCode = "200")
    @ApiResponse(description = "Pool not found", responseCode = "404")
    public ResponseEntity<Pool> getPool(@PathVariable String id) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(this.poolsService.getPool(id));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @GetMapping("/{id}/occupancy")
    @Operation(summary = "Get the occupancy of a pool by ID")
    @ApiResponse(description = "Returns the occupancy of the pool", responseCode = "200")
    @ApiResponse(description = "Pool not found", responseCode = "404")
    public ResponseEntity<String> getPoolOccupancy(@PathVariable String id) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(this.poolsService.getPoolOccupancy(id).toString());
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @PostMapping("/{id}/entry")
    @Operation(summary = "Register an entry to a pool")
    @ApiResponse(description = "Returns the new occupancy of the pool", responseCode = "200")
    @ApiResponse(description = "Pool not found", responseCode = "404")
    @ApiResponse(description = "Pool is full", responseCode = "403")
    public ResponseEntity<String> registerEntry(@PathVariable String id) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(this.poolsService.registerEntry(id).toString());
        } catch (FullPoolException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @PostMapping("/{id}/exit")
    @Operation(summary = "Register an exit from a pool")
    @ApiResponse(description = "Returns the new occupancy of the pool", responseCode = "200")
    @ApiResponse(description = "Pool not found", responseCode = "404")
    @ApiResponse(description = "Pool is empty", responseCode = "403")
    public ResponseEntity<String> registerExit(@PathVariable String id) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(this.poolsService.registerExit(id).toString());
        } catch (EmptyPoolException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @GetMapping("/{id}/can-enter")
    @Operation(summary = "Check if customer can enter a pool")
    @ApiResponse(description = "Returns true if customer can enter", responseCode = "200")
    @ApiResponse(description = "Pool not found", responseCode = "404")
    @ApiResponse(description = "Pool is full", responseCode = "403")
    public ResponseEntity<String> canEnter(@PathVariable String id) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(this.poolsService.canEnter(id) ? "true" : "false");
        } catch (FullPoolException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }
}
