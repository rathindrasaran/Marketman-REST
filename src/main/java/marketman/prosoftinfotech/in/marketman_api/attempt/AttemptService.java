package marketman.prosoftinfotech.in.marketman_api.attempt;

import java.util.List;
import marketman.prosoftinfotech.in.marketman_api.campaign.Campaign;
import marketman.prosoftinfotech.in.marketman_api.campaign.CampaignRepository;
import marketman.prosoftinfotech.in.marketman_api.contact.Contact;
import marketman.prosoftinfotech.in.marketman_api.contact.ContactRepository;
import marketman.prosoftinfotech.in.marketman_api.reply.Reply;
import marketman.prosoftinfotech.in.marketman_api.reply.ReplyRepository;
import marketman.prosoftinfotech.in.marketman_api.template.Template;
import marketman.prosoftinfotech.in.marketman_api.template.TemplateRepository;
import marketman.prosoftinfotech.in.marketman_api.util.NotFoundException;
import marketman.prosoftinfotech.in.marketman_api.util.ReferencedWarning;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class AttemptService {

    private final AttemptRepository attemptRepository;
    private final ContactRepository contactRepository;
    private final CampaignRepository campaignRepository;
    private final TemplateRepository templateRepository;
    private final ReplyRepository replyRepository;

    public AttemptService(final AttemptRepository attemptRepository,
            final ContactRepository contactRepository, final CampaignRepository campaignRepository,
            final TemplateRepository templateRepository, final ReplyRepository replyRepository) {
        this.attemptRepository = attemptRepository;
        this.contactRepository = contactRepository;
        this.campaignRepository = campaignRepository;
        this.templateRepository = templateRepository;
        this.replyRepository = replyRepository;
    }

    public List<AttemptDTO> findAll() {
        final List<Attempt> attempts = attemptRepository.findAll(Sort.by("attemptId"));
        return attempts.stream()
                .map(attempt -> mapToDTO(attempt, new AttemptDTO()))
                .toList();
    }

    public AttemptDTO get(final Integer attemptId) {
        return attemptRepository.findById(attemptId)
                .map(attempt -> mapToDTO(attempt, new AttemptDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Integer create(final AttemptDTO attemptDTO) {
        final Attempt attempt = new Attempt();
        mapToEntity(attemptDTO, attempt);
        return attemptRepository.save(attempt).getAttemptId();
    }

    public void update(final Integer attemptId, final AttemptDTO attemptDTO) {
        final Attempt attempt = attemptRepository.findById(attemptId)
                .orElseThrow(NotFoundException::new);
        mapToEntity(attemptDTO, attempt);
        attemptRepository.save(attempt);
    }

    public void delete(final Integer attemptId) {
        attemptRepository.deleteById(attemptId);
    }

    private AttemptDTO mapToDTO(final Attempt attempt, final AttemptDTO attemptDTO) {
        attemptDTO.setAttemptId(attempt.getAttemptId());
        attemptDTO.setEmailText(attempt.getEmailText());
        attemptDTO.setEmailBody(attempt.getEmailBody());
        attemptDTO.setCallNotes(attempt.getCallNotes());
        attemptDTO.setSmsText(attempt.getSmsText());
        attemptDTO.setCallRecording(attempt.getCallRecording());
        attemptDTO.setAttemptTimestamp(attempt.getAttemptTimestamp());
        attemptDTO.setContact(attempt.getContact() == null ? null : attempt.getContact().getContactId());
        attemptDTO.setCampaign(attempt.getCampaign() == null ? null : attempt.getCampaign().getCampaignId());
        attemptDTO.setTemplate(attempt.getTemplate() == null ? null : attempt.getTemplate().getTemplateId());
        return attemptDTO;
    }

    private Attempt mapToEntity(final AttemptDTO attemptDTO, final Attempt attempt) {
        attempt.setEmailText(attemptDTO.getEmailText());
        attempt.setEmailBody(attemptDTO.getEmailBody());
        attempt.setCallNotes(attemptDTO.getCallNotes());
        attempt.setSmsText(attemptDTO.getSmsText());
        attempt.setCallRecording(attemptDTO.getCallRecording());
        attempt.setAttemptTimestamp(attemptDTO.getAttemptTimestamp());
        final Contact contact = attemptDTO.getContact() == null ? null : contactRepository.findById(attemptDTO.getContact())
                .orElseThrow(() -> new NotFoundException("contact not found"));
        attempt.setContact(contact);
        final Campaign campaign = attemptDTO.getCampaign() == null ? null : campaignRepository.findById(attemptDTO.getCampaign())
                .orElseThrow(() -> new NotFoundException("campaign not found"));
        attempt.setCampaign(campaign);
        final Template template = attemptDTO.getTemplate() == null ? null : templateRepository.findById(attemptDTO.getTemplate())
                .orElseThrow(() -> new NotFoundException("template not found"));
        attempt.setTemplate(template);
        return attempt;
    }

    public ReferencedWarning getReferencedWarning(final Integer attemptId) {
        final ReferencedWarning referencedWarning = new ReferencedWarning();
        final Attempt attempt = attemptRepository.findById(attemptId)
                .orElseThrow(NotFoundException::new);
        final Reply attemptReply = replyRepository.findFirstByAttempt(attempt);
        if (attemptReply != null) {
            referencedWarning.setKey("attempt.reply.attempt.referenced");
            referencedWarning.addParam(attemptReply.getReplyId());
            return referencedWarning;
        }
        return null;
    }

}
