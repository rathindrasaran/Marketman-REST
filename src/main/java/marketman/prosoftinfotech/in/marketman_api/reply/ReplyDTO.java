package marketman.prosoftinfotech.in.marketman_api.reply;

import jakarta.validation.constraints.Size;
import java.time.OffsetDateTime;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class ReplyDTO {

    private Integer replyId;

    @Size(max = 255)
    private String emailText;

    private String emailBody;

    private String callNotes;

    @Size(max = 255)
    private String smsText;

    private String callRecording;

    private OffsetDateTime replyTimestamp;

    private Integer attempt;

    private Integer contact;

    private Integer campaign;

}
