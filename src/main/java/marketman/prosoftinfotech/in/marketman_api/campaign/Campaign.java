package marketman.prosoftinfotech.in.marketman_api.campaign;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.time.OffsetDateTime;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;
import marketman.prosoftinfotech.in.marketman_api.attempt.Attempt;
import marketman.prosoftinfotech.in.marketman_api.list.List;
import marketman.prosoftinfotech.in.marketman_api.reply.Reply;
import marketman.prosoftinfotech.in.marketman_api.template.Template;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;


@Entity
@Table(name = "Campaigns")
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
public class Campaign {

    @Id
    @Column(nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer campaignId;

    @Column
    private String caption;

    @Column(columnDefinition = "longtext")
    private String descc;

    @Column(length = 50)
    private String type;

    @OneToMany(mappedBy = "campaign")
    private Set<List> campaignLists;

    @OneToMany(mappedBy = "campaign")
    private Set<Template> campaignTemplates;

    @OneToMany(mappedBy = "campaign")
    private Set<Attempt> campaignAttempts;

    @OneToMany(mappedBy = "campaign")
    private Set<Reply> campaignReplies;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private OffsetDateTime dateCreated;

    @LastModifiedDate
    @Column(nullable = false)
    private OffsetDateTime lastUpdated;

}
