package marketman.prosoftinfotech.in.marketman_api.reply;

import marketman.prosoftinfotech.in.marketman_api.attempt.Attempt;
import marketman.prosoftinfotech.in.marketman_api.campaign.Campaign;
import marketman.prosoftinfotech.in.marketman_api.contact.Contact;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ReplyRepository extends JpaRepository<Reply, Integer> {

    Reply findFirstByAttempt(Attempt attempt);

    Reply findFirstByContact(Contact contact);

    Reply findFirstByCampaign(Campaign campaign);

}
