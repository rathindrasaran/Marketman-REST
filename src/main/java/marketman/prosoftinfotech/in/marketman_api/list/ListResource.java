package marketman.prosoftinfotech.in.marketman_api.list;

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
@RequestMapping(value = "/api/lists", produces = MediaType.APPLICATION_JSON_VALUE)
public class ListResource {

    private final ListService listService;

    public ListResource(final ListService listService) {
        this.listService = listService;
    }

    @GetMapping
    public ResponseEntity<List<ListDTO>> getAllLists() {
        return ResponseEntity.ok(listService.findAll());
    }

    @GetMapping("/{listId}")
    public ResponseEntity<ListDTO> getList(@PathVariable(name = "listId") final Integer listId) {
        return ResponseEntity.ok(listService.get(listId));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Integer> createList(@RequestBody @Valid final ListDTO listDTO) {
        final Integer createdListId = listService.create(listDTO);
        return new ResponseEntity<>(createdListId, HttpStatus.CREATED);
    }

    @PutMapping("/{listId}")
    public ResponseEntity<Integer> updateList(@PathVariable(name = "listId") final Integer listId,
            @RequestBody @Valid final ListDTO listDTO) {
        listService.update(listId, listDTO);
        return ResponseEntity.ok(listId);
    }

    @DeleteMapping("/{listId}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteList(@PathVariable(name = "listId") final Integer listId) {
        final ReferencedWarning referencedWarning = listService.getReferencedWarning(listId);
        if (referencedWarning != null) {
            throw new ReferencedException(referencedWarning);
        }
        listService.delete(listId);
        return ResponseEntity.noContent().build();
    }

}
