package marketman.prosoftinfotech.in.marketman_api.template;

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
@RequestMapping(value = "/api/templates", produces = MediaType.APPLICATION_JSON_VALUE)
public class TemplateResource {

    private final TemplateService templateService;

    public TemplateResource(final TemplateService templateService) {
        this.templateService = templateService;
    }

    @GetMapping
    public ResponseEntity<List<TemplateDTO>> getAllTemplates() {
        return ResponseEntity.ok(templateService.findAll());
    }

    @GetMapping("/{templateId}")
    public ResponseEntity<TemplateDTO> getTemplate(
            @PathVariable(name = "templateId") final Integer templateId) {
        return ResponseEntity.ok(templateService.get(templateId));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Integer> createTemplate(
            @RequestBody @Valid final TemplateDTO templateDTO) {
        final Integer createdTemplateId = templateService.create(templateDTO);
        return new ResponseEntity<>(createdTemplateId, HttpStatus.CREATED);
    }

    @PutMapping("/{templateId}")
    public ResponseEntity<Integer> updateTemplate(
            @PathVariable(name = "templateId") final Integer templateId,
            @RequestBody @Valid final TemplateDTO templateDTO) {
        templateService.update(templateId, templateDTO);
        return ResponseEntity.ok(templateId);
    }

    @DeleteMapping("/{templateId}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteTemplate(
            @PathVariable(name = "templateId") final Integer templateId) {
        final ReferencedWarning referencedWarning = templateService.getReferencedWarning(templateId);
        if (referencedWarning != null) {
            throw new ReferencedException(referencedWarning);
        }
        templateService.delete(templateId);
        return ResponseEntity.noContent().build();
    }

}
