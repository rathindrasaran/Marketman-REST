package marketman.prosoftinfotech.in.marketman_api.reply;

import java.util.List;
import marketman.prosoftinfotech.in.marketman_api.attempt.Attempt;
import marketman.prosoftinfotech.in.marketman_api.attempt.AttemptRepository;
import marketman.prosoftinfotech.in.marketman_api.campaign.Campaign;
import marketman.prosoftinfotech.in.marketman_api.campaign.CampaignRepository;
import marketman.prosoftinfotech.in.marketman_api.contact.Contact;
import marketman.prosoftinfotech.in.marketman_api.contact.ContactRepository;
import marketman.prosoftinfotech.in.marketman_api.util.NotFoundException;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class ReplyService {

    private final ReplyRepository replyRepository;
    private final AttemptRepository attemptRepository;
    private final ContactRepository contactRepository;
    private final CampaignRepository campaignRepository;

    public ReplyService(final ReplyRepository replyRepository,
            final AttemptRepository attemptRepository, final ContactRepository contactRepository,
            final CampaignRepository campaignRepository) {
        this.replyRepository = replyRepository;
        this.attemptRepository = attemptRepository;
        this.contactRepository = contactRepository;
        this.campaignRepository = campaignRepository;
    }

    public List<ReplyDTO> findAll() {
        final List<Reply> replies = replyRepository.findAll(Sort.by("replyId"));
        return replies.stream()
                .map(reply -> mapToDTO(reply, new ReplyDTO()))
                .toList();
    }

    public ReplyDTO get(final Integer replyId) {
        return replyRepository.findById(replyId)
                .map(reply -> mapToDTO(reply, new ReplyDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Integer create(final ReplyDTO replyDTO) {
        final Reply reply = new Reply();
        mapToEntity(replyDTO, reply);
        return replyRepository.save(reply).getReplyId();
    }

    public void update(final Integer replyId, final ReplyDTO replyDTO) {
        final Reply reply = replyRepository.findById(replyId)
                .orElseThrow(NotFoundException::new);
        mapToEntity(replyDTO, reply);
        replyRepository.save(reply);
    }

    public void delete(final Integer replyId) {
        replyRepository.deleteById(replyId);
    }

    private ReplyDTO mapToDTO(final Reply reply, final ReplyDTO replyDTO) {
        replyDTO.setReplyId(reply.getReplyId());
        replyDTO.setEmailText(reply.getEmailText());
        replyDTO.setEmailBody(reply.getEmailBody());
        replyDTO.setCallNotes(reply.getCallNotes());
        replyDTO.setSmsText(reply.getSmsText());
        replyDTO.setCallRecording(reply.getCallRecording());
        replyDTO.setReplyTimestamp(reply.getReplyTimestamp());
        replyDTO.setAttempt(reply.getAttempt() == null ? null : reply.getAttempt().getAttemptId());
        replyDTO.setContact(reply.getContact() == null ? null : reply.getContact().getContactId());
        replyDTO.setCampaign(reply.getCampaign() == null ? null : reply.getCampaign().getCampaignId());
        return replyDTO;
    }

    private Reply mapToEntity(final ReplyDTO replyDTO, final Reply reply) {
        reply.setEmailText(replyDTO.getEmailText());
        reply.setEmailBody(replyDTO.getEmailBody());
        reply.setCallNotes(replyDTO.getCallNotes());
        reply.setSmsText(replyDTO.getSmsText());
        reply.setCallRecording(replyDTO.getCallRecording());
        reply.setReplyTimestamp(replyDTO.getReplyTimestamp());
        final Attempt attempt = replyDTO.getAttempt() == null ? null : attemptRepository.findById(replyDTO.getAttempt())
                .orElseThrow(() -> new NotFoundException("attempt not found"));
        reply.setAttempt(attempt);
        final Contact contact = replyDTO.getContact() == null ? null : contactRepository.findById(replyDTO.getContact())
                .orElseThrow(() -> new NotFoundException("contact not found"));
        reply.setContact(contact);
        final Campaign campaign = replyDTO.getCampaign() == null ? null : campaignRepository.findById(replyDTO.getCampaign())
                .orElseThrow(() -> new NotFoundException("campaign not found"));
        reply.setCampaign(campaign);
        return reply;
    }

}
