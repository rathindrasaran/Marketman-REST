package marketman.prosoftinfotech.in.marketman_api.contact;

import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class ContactDTO {

    private Integer contactId;

    @Size(max = 255)
    private String firstname;

    @Size(max = 255)
    private String lastname;

    @Size(max = 20)
    private String phone;

    @Size(max = 255)
    private String email;

    @Size(max = 100)
    private String city;

    @Size(max = 100)
    private String state;

    @Size(max = 100)
    private String country;

    @Size(max = 20)
    private String zipcode;

    @Size(max = 255)
    private String company;

    private String descc;

    private Integer list;

}
