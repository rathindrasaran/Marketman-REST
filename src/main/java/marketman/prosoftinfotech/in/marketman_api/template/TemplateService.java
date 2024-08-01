package marketman.prosoftinfotech.in.marketman_api.template;

import java.util.List;
import marketman.prosoftinfotech.in.marketman_api.attempt.Attempt;
import marketman.prosoftinfotech.in.marketman_api.attempt.AttemptRepository;
import marketman.prosoftinfotech.in.marketman_api.campaign.Campaign;
import marketman.prosoftinfotech.in.marketman_api.campaign.CampaignRepository;
import marketman.prosoftinfotech.in.marketman_api.util.NotFoundException;
import marketman.prosoftinfotech.in.marketman_api.util.ReferencedWarning;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class TemplateService {

    private final TemplateRepository templateRepository;
    private final CampaignRepository campaignRepository;
    private final AttemptRepository attemptRepository;

    public TemplateService(final TemplateRepository templateRepository,
            final CampaignRepository campaignRepository,
            final AttemptRepository attemptRepository) {
        this.templateRepository = templateRepository;
        this.campaignRepository = campaignRepository;
        this.attemptRepository = attemptRepository;
    }

    public List<TemplateDTO> findAll() {
        final List<Template> templates = templateRepository.findAll(Sort.by("templateId"));
        return templates.stream()
                .map(template -> mapToDTO(template, new TemplateDTO()))
                .toList();
    }

    public TemplateDTO get(final Integer templateId) {
        return templateRepository.findById(templateId)
                .map(template -> mapToDTO(template, new TemplateDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Integer create(final TemplateDTO templateDTO) {
        final Template template = new Template();
        mapToEntity(templateDTO, template);
        return templateRepository.save(template).getTemplateId();
    }

    public void update(final Integer templateId, final TemplateDTO templateDTO) {
        final Template template = templateRepository.findById(templateId)
                .orElseThrow(NotFoundException::new);
        mapToEntity(templateDTO, template);
        templateRepository.save(template);
    }

    public void delete(final Integer templateId) {
        templateRepository.deleteById(templateId);
    }

    private TemplateDTO mapToDTO(final Template template, final TemplateDTO templateDTO) {
        templateDTO.setTemplateId(template.getTemplateId());
        templateDTO.setCaption(template.getCaption());
        templateDTO.setDescc(template.getDescc());
        templateDTO.setType(template.getType());
        templateDTO.setContent(template.getContent());
        templateDTO.setCampaign(template.getCampaign() == null ? null : template.getCampaign().getCampaignId());
        return templateDTO;
    }

    private Template mapToEntity(final TemplateDTO templateDTO, final Template template) {
        template.setCaption(templateDTO.getCaption());
        template.setDescc(templateDTO.getDescc());
        template.setType(templateDTO.getType());
        template.setContent(templateDTO.getContent());
        final Campaign campaign = templateDTO.getCampaign() == null ? null : campaignRepository.findById(templateDTO.getCampaign())
                .orElseThrow(() -> new NotFoundException("campaign not found"));
        template.setCampaign(campaign);
        return template;
    }

    public ReferencedWarning getReferencedWarning(final Integer templateId) {
        final ReferencedWarning referencedWarning = new ReferencedWarning();
        final Template template = templateRepository.findById(templateId)
                .orElseThrow(NotFoundException::new);
        final Attempt templateAttempt = attemptRepository.findFirstByTemplate(template);
        if (templateAttempt != null) {
            referencedWarning.setKey("template.attempt.template.referenced");
            referencedWarning.addParam(templateAttempt.getAttemptId());
            return referencedWarning;
        }
        return null;
    }

}
