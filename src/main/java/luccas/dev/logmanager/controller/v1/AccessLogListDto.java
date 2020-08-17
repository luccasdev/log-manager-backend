package luccas.dev.logmanager.controller.v1;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccessLogListDto {

    private Long id;

    private String createdAt;

    private String ipAddress;

    private String requestLine;

    private Integer responseStatus;

    private String userAgent;

}
