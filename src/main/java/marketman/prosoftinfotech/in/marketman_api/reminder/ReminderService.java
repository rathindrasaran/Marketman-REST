package marketman.prosoftinfotech.in.marketman_api.reminder;

import java.util.List;
import marketman.prosoftinfotech.in.marketman_api.util.NotFoundException;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class ReminderService {

    private final ReminderRepository reminderRepository;

    public ReminderService(final ReminderRepository reminderRepository) {
        this.reminderRepository = reminderRepository;
    }

    public List<ReminderDTO> findAll() {
        final List<Reminder> reminders = reminderRepository.findAll(Sort.by("id"));
        return reminders.stream()
                .map(reminder -> mapToDTO(reminder, new ReminderDTO()))
                .toList();
    }

    public ReminderDTO get(final Integer id) {
        return reminderRepository.findById(id)
                .map(reminder -> mapToDTO(reminder, new ReminderDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Integer create(final ReminderDTO reminderDTO) {
        final Reminder reminder = new Reminder();
        mapToEntity(reminderDTO, reminder);
        return reminderRepository.save(reminder).getId();
    }

    public void update(final Integer id, final ReminderDTO reminderDTO) {
        final Reminder reminder = reminderRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(reminderDTO, reminder);
        reminderRepository.save(reminder);
    }

    public void delete(final Integer id) {
        reminderRepository.deleteById(id);
    }

    private ReminderDTO mapToDTO(final Reminder reminder, final ReminderDTO reminderDTO) {
        reminderDTO.setId(reminder.getId());
        reminderDTO.setText(reminder.getText());
        reminderDTO.setTime(reminder.getTime());
        reminderDTO.setType(reminder.getType());
        reminderDTO.setIdoftype(reminder.getIdoftype());
        return reminderDTO;
    }

    private Reminder mapToEntity(final ReminderDTO reminderDTO, final Reminder reminder) {
        reminder.setText(reminderDTO.getText());
        reminder.setTime(reminderDTO.getTime());
        reminder.setType(reminderDTO.getType());
        reminder.setIdoftype(reminderDTO.getIdoftype());
        return reminder;
    }

}
