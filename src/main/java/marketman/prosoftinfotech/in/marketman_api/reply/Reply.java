package marketman.prosoftinfotech.in.marketman_api.reply;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.OffsetDateTime;
import lombok.Getter;
import lombok.Setter;
import marketman.prosoftinfotech.in.marketman_api.attempt.Attempt;
import marketman.prosoftinfotech.in.marketman_api.campaign.Campaign;
import marketman.prosoftinfotech.in.marketman_api.contact.Contact;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;


@Entity
@Table(name = "Replies")
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
public class Reply {

    @Id
    @Column(nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer replyId;

    @Column
    private String emailText;

    @Column(columnDefinition = "longtext")
    private String emailBody;

    @Column(columnDefinition = "longtext")
    private String callNotes;

    @Column
    private String smsText;

    @Column(columnDefinition = "longtext")
    private String callRecording;

    @Column
    private OffsetDateTime replyTimestamp;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "attempt_id")
    private Attempt attempt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "contact_id")
    private Contact contact;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "campaign_id")
    private Campaign campaign;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private OffsetDateTime dateCreated;

    @LastModifiedDate
    @Column(nullable = false)
    private OffsetDateTime lastUpdated;

}
