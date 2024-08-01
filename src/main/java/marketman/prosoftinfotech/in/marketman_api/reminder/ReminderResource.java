package marketman.prosoftinfotech.in.marketman_api.reminder;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import java.util.List;
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
@RequestMapping(value = "/api/reminders", produces = MediaType.APPLICATION_JSON_VALUE)
public class ReminderResource {

    private final ReminderService reminderService;

    public ReminderResource(final ReminderService reminderService) {
        this.reminderService = reminderService;
    }

    @GetMapping
    public ResponseEntity<List<ReminderDTO>> getAllReminders() {
        return ResponseEntity.ok(reminderService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReminderDTO> getReminder(@PathVariable(name = "id") final Integer id) {
        return ResponseEntity.ok(reminderService.get(id));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Integer> createReminder(
            @RequestBody @Valid final ReminderDTO reminderDTO) {
        final Integer createdId = reminderService.create(reminderDTO);
        return new ResponseEntity<>(createdId, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Integer> updateReminder(@PathVariable(name = "id") final Integer id,
            @RequestBody @Valid final ReminderDTO reminderDTO) {
        reminderService.update(id, reminderDTO);
        return ResponseEntity.ok(id);
    }

    @DeleteMapping("/{id}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteReminder(@PathVariable(name = "id") final Integer id) {
        reminderService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
