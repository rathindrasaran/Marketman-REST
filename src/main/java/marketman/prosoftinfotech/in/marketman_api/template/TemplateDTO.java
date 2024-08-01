package marketman.prosoftinfotech.in.marketman_api.template;

import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class TemplateDTO {

    private Integer templateId;

    @Size(max = 255)
    private String caption;

    private String descc;

    @Size(max = 50)
    private String type;

    private String content;

    private Integer campaign;

}
