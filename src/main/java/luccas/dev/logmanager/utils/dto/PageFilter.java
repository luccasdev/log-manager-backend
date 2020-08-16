package luccas.dev.logmanager.utils.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PageFilter<F> {

    private F filter;
    private int pageNumber;
    private int pageSize;
    private String direction;
    private String field;

}
