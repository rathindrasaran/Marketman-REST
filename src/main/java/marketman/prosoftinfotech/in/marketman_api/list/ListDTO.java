package marketman.prosoftinfotech.in.marketman_api.list;

import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class ListDTO {

    private Integer listId;

    @Size(max = 255)
    private String caption;

    private String descc;

    private Integer campaign;

}
