package marketman.prosoftinfotech.in.marketman_api.list;

import marketman.prosoftinfotech.in.marketman_api.campaign.Campaign;
import marketman.prosoftinfotech.in.marketman_api.campaign.CampaignRepository;
import marketman.prosoftinfotech.in.marketman_api.contact.Contact;
import marketman.prosoftinfotech.in.marketman_api.contact.ContactRepository;
import marketman.prosoftinfotech.in.marketman_api.util.NotFoundException;
import marketman.prosoftinfotech.in.marketman_api.util.ReferencedWarning;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class ListService {

    private final ListRepository listRepository;
    private final CampaignRepository campaignRepository;
    private final ContactRepository contactRepository;

    public ListService(final ListRepository listRepository,
            final CampaignRepository campaignRepository,
            final ContactRepository contactRepository) {
        this.listRepository = listRepository;
        this.campaignRepository = campaignRepository;
        this.contactRepository = contactRepository;
    }

    public java.util.List<ListDTO> findAll() {
        final java.util.List<List> lists = listRepository.findAll(Sort.by("listId"));
        return lists.stream()
                .map(list -> mapToDTO(list, new ListDTO()))
                .toList();
    }

    public ListDTO get(final Integer listId) {
        return listRepository.findById(listId)
                .map(list -> mapToDTO(list, new ListDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Integer create(final ListDTO listDTO) {
        final List list = new List();
        mapToEntity(listDTO, list);
        return listRepository.save(list).getListId();
    }

    public void update(final Integer listId, final ListDTO listDTO) {
        final List list = listRepository.findById(listId)
                .orElseThrow(NotFoundException::new);
        mapToEntity(listDTO, list);
        listRepository.save(list);
    }

    public void delete(final Integer listId) {
        listRepository.deleteById(listId);
    }

    private ListDTO mapToDTO(final List list, final ListDTO listDTO) {
        listDTO.setListId(list.getListId());
        listDTO.setCaption(list.getCaption());
        listDTO.setDescc(list.getDescc());
        listDTO.setCampaign(list.getCampaign() == null ? null : list.getCampaign().getCampaignId());
        return listDTO;
    }

    private List mapToEntity(final ListDTO listDTO, final List list) {
        list.setCaption(listDTO.getCaption());
        list.setDescc(listDTO.getDescc());
        final Campaign campaign = listDTO.getCampaign() == null ? null : campaignRepository.findById(listDTO.getCampaign())
                .orElseThrow(() -> new NotFoundException("campaign not found"));
        list.setCampaign(campaign);
        return list;
    }

    public ReferencedWarning getReferencedWarning(final Integer listId) {
        final ReferencedWarning referencedWarning = new ReferencedWarning();
        final List list = listRepository.findById(listId)
                .orElseThrow(NotFoundException::new);
        final Contact listContact = contactRepository.findFirstByList(list);
        if (listContact != null) {
            referencedWarning.setKey("list.contact.list.referenced");
            referencedWarning.addParam(listContact.getContactId());
            return referencedWarning;
        }
        return null;
    }

}
