package marketman.prosoftinfotech.in.marketman_api.contact;

import java.util.List;
import marketman.prosoftinfotech.in.marketman_api.attempt.Attempt;
import marketman.prosoftinfotech.in.marketman_api.attempt.AttemptRepository;
import marketman.prosoftinfotech.in.marketman_api.list.ListRepository;
import marketman.prosoftinfotech.in.marketman_api.reply.Reply;
import marketman.prosoftinfotech.in.marketman_api.reply.ReplyRepository;
import marketman.prosoftinfotech.in.marketman_api.util.NotFoundException;
import marketman.prosoftinfotech.in.marketman_api.util.ReferencedWarning;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class ContactService {

    private final ContactRepository contactRepository;
    private final ListRepository listRepository;
    private final AttemptRepository attemptRepository;
    private final ReplyRepository replyRepository;

    public ContactService(final ContactRepository contactRepository,
            final ListRepository listRepository, final AttemptRepository attemptRepository,
            final ReplyRepository replyRepository) {
        this.contactRepository = contactRepository;
        this.listRepository = listRepository;
        this.attemptRepository = attemptRepository;
        this.replyRepository = replyRepository;
    }

    public List<ContactDTO> findAll() {
        final List<Contact> contacts = contactRepository.findAll(Sort.by("contactId"));
        return contacts.stream()
                .map(contact -> mapToDTO(contact, new ContactDTO()))
                .toList();
    }

    public ContactDTO get(final Integer contactId) {
        return contactRepository.findById(contactId)
                .map(contact -> mapToDTO(contact, new ContactDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Integer create(final ContactDTO contactDTO) {
        final Contact contact = new Contact();
        mapToEntity(contactDTO, contact);
        return contactRepository.save(contact).getContactId();
    }

    public void update(final Integer contactId, final ContactDTO contactDTO) {
        final Contact contact = contactRepository.findById(contactId)
                .orElseThrow(NotFoundException::new);
        mapToEntity(contactDTO, contact);
        contactRepository.save(contact);
    }

    public void delete(final Integer contactId) {
        contactRepository.deleteById(contactId);
    }

    private ContactDTO mapToDTO(final Contact contact, final ContactDTO contactDTO) {
        contactDTO.setContactId(contact.getContactId());
        contactDTO.setFirstname(contact.getFirstname());
        contactDTO.setLastname(contact.getLastname());
        contactDTO.setPhone(contact.getPhone());
        contactDTO.setEmail(contact.getEmail());
        contactDTO.setCity(contact.getCity());
        contactDTO.setState(contact.getState());
        contactDTO.setCountry(contact.getCountry());
        contactDTO.setZipcode(contact.getZipcode());
        contactDTO.setCompany(contact.getCompany());
        contactDTO.setDescc(contact.getDescc());
        contactDTO.setList(contact.getList() == null ? null : contact.getList().getListId());
        return contactDTO;
    }

    private Contact mapToEntity(final ContactDTO contactDTO, final Contact contact) {
        contact.setFirstname(contactDTO.getFirstname());
        contact.setLastname(contactDTO.getLastname());
        contact.setPhone(contactDTO.getPhone());
        contact.setEmail(contactDTO.getEmail());
        contact.setCity(contactDTO.getCity());
        contact.setState(contactDTO.getState());
        contact.setCountry(contactDTO.getCountry());
        contact.setZipcode(contactDTO.getZipcode());
        contact.setCompany(contactDTO.getCompany());
        contact.setDescc(contactDTO.getDescc());
        final marketman.prosoftinfotech.in.marketman_api.list.List list = contactDTO.getList() == null ? null : listRepository.findById(contactDTO.getList())
                .orElseThrow(() -> new NotFoundException("list not found"));
        contact.setList(list);
        return contact;
    }

    public ReferencedWarning getReferencedWarning(final Integer contactId) {
        final ReferencedWarning referencedWarning = new ReferencedWarning();
        final Contact contact = contactRepository.findById(contactId)
                .orElseThrow(NotFoundException::new);
        final Attempt contactAttempt = attemptRepository.findFirstByContact(contact);
        if (contactAttempt != null) {
            referencedWarning.setKey("contact.attempt.contact.referenced");
            referencedWarning.addParam(contactAttempt.getAttemptId());
            return referencedWarning;
        }
        final Reply contactReply = replyRepository.findFirstByContact(contact);
        if (contactReply != null) {
            referencedWarning.setKey("contact.reply.contact.referenced");
            referencedWarning.addParam(contactReply.getReplyId());
            return referencedWarning;
        }
        return null;
    }

}
