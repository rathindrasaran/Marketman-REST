package marketman.prosoftinfotech.in.marketman_api.reminder;

import jakarta.validation.constraints.Size;
import java.time.OffsetDateTime;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class ReminderDTO {

    private Integer id;

    @Size(max = 255)
    private String text;

    private OffsetDateTime time;

    @Size(max = 50)
    private String type;

    private Integer idoftype;

}
