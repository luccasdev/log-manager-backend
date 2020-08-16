package luccas.dev.logmanager.controller.v1;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import luccas.dev.logmanager.utils.dto.RangeDateDto;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccessLogFilter {

    private RangeDateDto createDateRange;

    private String ipAddress;

    private String userAgent;

}
