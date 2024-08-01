package marketman.prosoftinfotech.in.marketman_api.campaign;

import java.util.List;
import marketman.prosoftinfotech.in.marketman_api.attempt.Attempt;
import marketman.prosoftinfotech.in.marketman_api.attempt.AttemptRepository;
import marketman.prosoftinfotech.in.marketman_api.list.ListRepository;
import marketman.prosoftinfotech.in.marketman_api.reply.Reply;
import marketman.prosoftinfotech.in.marketman_api.reply.ReplyRepository;
import marketman.prosoftinfotech.in.marketman_api.template.Template;
import marketman.prosoftinfotech.in.marketman_api.template.TemplateRepository;
import marketman.prosoftinfotech.in.marketman_api.util.NotFoundException;
import marketman.prosoftinfotech.in.marketman_api.util.ReferencedWarning;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class CampaignService {

    private final CampaignRepository campaignRepository;
    private final ListRepository listRepository;
    private final TemplateRepository templateRepository;
    private final AttemptRepository attemptRepository;
    private final ReplyRepository replyRepository;

    public CampaignService(final CampaignRepository campaignRepository,
            final ListRepository listRepository, final TemplateRepository templateRepository,
            final AttemptRepository attemptRepository, final ReplyRepository replyRepository) {
        this.campaignRepository = campaignRepository;
        this.listRepository = listRepository;
        this.templateRepository = templateRepository;
        this.attemptRepository = attemptRepository;
        this.replyRepository = replyRepository;
    }

    public List<CampaignDTO> findAll() {
        final List<Campaign> campaigns = campaignRepository.findAll(Sort.by("campaignId"));
        return campaigns.stream()
                .map(campaign -> mapToDTO(campaign, new CampaignDTO()))
                .toList();
    }

    public CampaignDTO get(final Integer campaignId) {
        return campaignRepository.findById(campaignId)
                .map(campaign -> mapToDTO(campaign, new CampaignDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Integer create(final CampaignDTO campaignDTO) {
        final Campaign campaign = new Campaign();
        mapToEntity(campaignDTO, campaign);
        return campaignRepository.save(campaign).getCampaignId();
    }

    public void update(final Integer campaignId, final CampaignDTO campaignDTO) {
        final Campaign campaign = campaignRepository.findById(campaignId)
                .orElseThrow(NotFoundException::new);
        mapToEntity(campaignDTO, campaign);
        campaignRepository.save(campaign);
    }

    public void delete(final Integer campaignId) {
        campaignRepository.deleteById(campaignId);
    }

    private CampaignDTO mapToDTO(final Campaign campaign, final CampaignDTO campaignDTO) {
        campaignDTO.setCampaignId(campaign.getCampaignId());
        campaignDTO.setCaption(campaign.getCaption());
        campaignDTO.setDescc(campaign.getDescc());
        campaignDTO.setType(campaign.getType());
        return campaignDTO;
    }

    private Campaign mapToEntity(final CampaignDTO campaignDTO, final Campaign campaign) {
        campaign.setCaption(campaignDTO.getCaption());
        campaign.setDescc(campaignDTO.getDescc());
        campaign.setType(campaignDTO.getType());
        return campaign;
    }

    public ReferencedWarning getReferencedWarning(final Integer campaignId) {
        final ReferencedWarning referencedWarning = new ReferencedWarning();
        final Campaign campaign = campaignRepository.findById(campaignId)
                .orElseThrow(NotFoundException::new);
        final marketman.prosoftinfotech.in.marketman_api.list.List campaignList = listRepository.findFirstByCampaign(campaign);
        if (campaignList != null) {
            referencedWarning.setKey("campaign.list.campaign.referenced");
            referencedWarning.addParam(campaignList.getListId());
            return referencedWarning;
        }
        final Template campaignTemplate = templateRepository.findFirstByCampaign(campaign);
        if (campaignTemplate != null) {
            referencedWarning.setKey("campaign.template.campaign.referenced");
            referencedWarning.addParam(campaignTemplate.getTemplateId());
            return referencedWarning;
        }
        final Attempt campaignAttempt = attemptRepository.findFirstByCampaign(campaign);
        if (campaignAttempt != null) {
            referencedWarning.setKey("campaign.attempt.campaign.referenced");
            referencedWarning.addParam(campaignAttempt.getAttemptId());
            return referencedWarning;
        }
        final Reply campaignReply = replyRepository.findFirstByCampaign(campaign);
        if (campaignReply != null) {
            referencedWarning.setKey("campaign.reply.campaign.referenced");
            referencedWarning.addParam(campaignReply.getReplyId());
            return referencedWarning;
        }
        return null;
    }

}
