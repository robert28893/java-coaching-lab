package vn.unigap.reqres;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
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
public class ReqresPageDtoOut<T> {
    private Integer page;

    @JsonProperty(value = "per_page")
    private Integer perPage;

    private Long total;

    @JsonProperty(value = "total_pages")
    private Long totalPages;

    private List<T> data;
}
