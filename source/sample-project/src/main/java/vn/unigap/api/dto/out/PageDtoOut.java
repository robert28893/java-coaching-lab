package vn.unigap.api.dto.out;

import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PageDtoOut<T> {
    @Builder.Default
    private Integer page = 1;
    @Builder.Default
    private Integer pageSize = 10;
    @Builder.Default
    private Long totalElements = 0L;
    @Builder.Default
    private Long totalPages = 0L;
    @Builder.Default
    private List<T> data = new ArrayList<>();

    public static <T> PageDtoOut<T> from(Integer page, Integer pageSize, Long totalElements, List<T> data) {
        Long totalPages = totalElements / pageSize;
        if (totalElements % pageSize != 0) {
            totalPages++;
        }
        return PageDtoOut.<T>builder().page(page).pageSize(pageSize).totalElements(totalElements).totalPages(totalPages)
                .data(data).build();
    }
}
