package marketman.prosoftinfotech.in.marketman_api.list;

import marketman.prosoftinfotech.in.marketman_api.campaign.Campaign;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ListRepository extends JpaRepository<List, Integer> {

    List findFirstByCampaign(Campaign campaign);

}
