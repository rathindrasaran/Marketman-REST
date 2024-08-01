package marketman.prosoftinfotech.in.marketman_api.contact;

import marketman.prosoftinfotech.in.marketman_api.list.List;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ContactRepository extends JpaRepository<Contact, Integer> {

    Contact findFirstByList(List list);

}
