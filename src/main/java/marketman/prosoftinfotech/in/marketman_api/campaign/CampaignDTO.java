package marketman.prosoftinfotech.in.marketman_api.campaign;

import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class CampaignDTO {

    private Integer campaignId;

    @Size(max = 255)
    private String caption;

    private String descc;

    @Size(max = 50)
    private String type;

}
