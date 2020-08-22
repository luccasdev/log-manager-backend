package luccas.dev.logmanager.controller.v1;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccessLogFilter {

    private List<Date> createDateRange;

    private String ipAddress;

    private String userAgent;

}
