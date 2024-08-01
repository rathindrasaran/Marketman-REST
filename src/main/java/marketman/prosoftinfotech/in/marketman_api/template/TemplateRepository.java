package marketman.prosoftinfotech.in.marketman_api.template;

import marketman.prosoftinfotech.in.marketman_api.campaign.Campaign;
import org.springframework.data.jpa.repository.JpaRepository;


public interface TemplateRepository extends JpaRepository<Template, Integer> {

    Template findFirstByCampaign(Campaign campaign);

}
