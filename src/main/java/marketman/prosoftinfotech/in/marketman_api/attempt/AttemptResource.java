package marketman.prosoftinfotech.in.marketman_api.attempt;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import java.util.List;
import marketman.prosoftinfotech.in.marketman_api.util.ReferencedException;
import marketman.prosoftinfotech.in.marketman_api.util.ReferencedWarning;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(value = "/api/attempts", produces = MediaType.APPLICATION_JSON_VALUE)
public class AttemptResource {

    private final AttemptService attemptService;

    public AttemptResource(final AttemptService attemptService) {
        this.attemptService = attemptService;
    }

    @GetMapping
    public ResponseEntity<List<AttemptDTO>> getAllAttempts() {
        return ResponseEntity.ok(attemptService.findAll());
    }

    @GetMapping("/{attemptId}")
    public ResponseEntity<AttemptDTO> getAttempt(
            @PathVariable(name = "attemptId") final Integer attemptId) {
        return ResponseEntity.ok(attemptService.get(attemptId));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Integer> createAttempt(@RequestBody @Valid final AttemptDTO attemptDTO) {
        final Integer createdAttemptId = attemptService.create(attemptDTO);
        return new ResponseEntity<>(createdAttemptId, HttpStatus.CREATED);
    }

    @PutMapping("/{attemptId}")
    public ResponseEntity<Integer> updateAttempt(
            @PathVariable(name = "attemptId") final Integer attemptId,
            @RequestBody @Valid final AttemptDTO attemptDTO) {
        attemptService.update(attemptId, attemptDTO);
        return ResponseEntity.ok(attemptId);
    }

    @DeleteMapping("/{attemptId}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteAttempt(
            @PathVariable(name = "attemptId") final Integer attemptId) {
        final ReferencedWarning referencedWarning = attemptService.getReferencedWarning(attemptId);
        if (referencedWarning != null) {
            throw new ReferencedException(referencedWarning);
        }
        attemptService.delete(attemptId);
        return ResponseEntity.noContent().build();
    }

}
