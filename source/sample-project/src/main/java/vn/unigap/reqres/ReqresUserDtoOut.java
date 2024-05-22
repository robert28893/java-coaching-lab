package vn.unigap.reqres;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/*
{
           "id": 7,
           "email": "michael.lawson@reqres.in",
           "first_name": "Michael",
           "last_name": "Lawson",
           "avatar": "https://reqres.in/img/faces/7-image.jpg"
       }
*/
@Data
public class ReqresUserDtoOut {
    private Long id;
    private String email;

    @JsonProperty(value = "first_name")
    private String firstName;

    @JsonProperty(value = "last_name")
    private String lastName;

    private String avatar;
}
