package marketman.prosoftinfotech.in.marketman_api.contact;

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
@RequestMapping(value = "/api/contacts", produces = MediaType.APPLICATION_JSON_VALUE)
public class ContactResource {

    private final ContactService contactService;

    public ContactResource(final ContactService contactService) {
        this.contactService = contactService;
    }

    @GetMapping
    public ResponseEntity<List<ContactDTO>> getAllContacts() {
        return ResponseEntity.ok(contactService.findAll());
    }

    @GetMapping("/{contactId}")
    public ResponseEntity<ContactDTO> getContact(
            @PathVariable(name = "contactId") final Integer contactId) {
        return ResponseEntity.ok(contactService.get(contactId));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Integer> createContact(@RequestBody @Valid final ContactDTO contactDTO) {
        final Integer createdContactId = contactService.create(contactDTO);
        return new ResponseEntity<>(createdContactId, HttpStatus.CREATED);
    }

    @PutMapping("/{contactId}")
    public ResponseEntity<Integer> updateContact(
            @PathVariable(name = "contactId") final Integer contactId,
            @RequestBody @Valid final ContactDTO contactDTO) {
        contactService.update(contactId, contactDTO);
        return ResponseEntity.ok(contactId);
    }

    @DeleteMapping("/{contactId}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteContact(
            @PathVariable(name = "contactId") final Integer contactId) {
        final ReferencedWarning referencedWarning = contactService.getReferencedWarning(contactId);
        if (referencedWarning != null) {
            throw new ReferencedException(referencedWarning);
        }
        contactService.delete(contactId);
        return ResponseEntity.noContent().build();
    }

}
