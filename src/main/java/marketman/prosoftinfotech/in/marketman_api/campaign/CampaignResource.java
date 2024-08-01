package marketman.prosoftinfotech.in.marketman_api.campaign;

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
@RequestMapping(value = "/api/campaigns", produces = MediaType.APPLICATION_JSON_VALUE)
public class CampaignResource {

    private final CampaignService campaignService;

    public CampaignResource(final CampaignService campaignService) {
        this.campaignService = campaignService;
    }

    @GetMapping
    public ResponseEntity<List<CampaignDTO>> getAllCampaigns() {
        return ResponseEntity.ok(campaignService.findAll());
    }

    @GetMapping("/{campaignId}")
    public ResponseEntity<CampaignDTO> getCampaign(
            @PathVariable(name = "campaignId") final Integer campaignId) {
        return ResponseEntity.ok(campaignService.get(campaignId));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Integer> createCampaign(
            @RequestBody @Valid final CampaignDTO campaignDTO) {
        final Integer createdCampaignId = campaignService.create(campaignDTO);
        return new ResponseEntity<>(createdCampaignId, HttpStatus.CREATED);
    }

    @PutMapping("/{campaignId}")
    public ResponseEntity<Integer> updateCampaign(
            @PathVariable(name = "campaignId") final Integer campaignId,
            @RequestBody @Valid final CampaignDTO campaignDTO) {
        campaignService.update(campaignId, campaignDTO);
        return ResponseEntity.ok(campaignId);
    }

    @DeleteMapping("/{campaignId}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteCampaign(
            @PathVariable(name = "campaignId") final Integer campaignId) {
        final ReferencedWarning referencedWarning = campaignService.getReferencedWarning(campaignId);
        if (referencedWarning != null) {
            throw new ReferencedException(referencedWarning);
        }
        campaignService.delete(campaignId);
        return ResponseEntity.noContent().build();
    }

}
