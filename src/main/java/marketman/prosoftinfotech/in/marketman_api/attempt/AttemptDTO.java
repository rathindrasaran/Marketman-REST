package marketman.prosoftinfotech.in.marketman_api.attempt;

import jakarta.validation.constraints.Size;
import java.time.OffsetDateTime;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class AttemptDTO {

    private Integer attemptId;

    @Size(max = 255)
    private String emailText;

    private String emailBody;

    private String callNotes;

    @Size(max = 255)
    private String smsText;

    private String callRecording;

    private OffsetDateTime attemptTimestamp;

    private Integer contact;

    private Integer campaign;

    private Integer template;

}
