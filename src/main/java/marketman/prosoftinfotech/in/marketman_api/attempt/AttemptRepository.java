package marketman.prosoftinfotech.in.marketman_api.attempt;

import marketman.prosoftinfotech.in.marketman_api.campaign.Campaign;
import marketman.prosoftinfotech.in.marketman_api.contact.Contact;
import marketman.prosoftinfotech.in.marketman_api.template.Template;
import org.springframework.data.jpa.repository.JpaRepository;


public interface AttemptRepository extends JpaRepository<Attempt, Integer> {

    Attempt findFirstByContact(Contact contact);

    Attempt findFirstByCampaign(Campaign campaign);

    Attempt findFirstByTemplate(Template template);

}
