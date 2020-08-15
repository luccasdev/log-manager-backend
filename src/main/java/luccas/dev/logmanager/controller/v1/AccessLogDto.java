package luccas.dev.logmanager.controller.v1;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccessLogDto {

    private Long id;

    private Date createdAt;

    private String ipAddress;

    private String requestLine;

    private Integer responseStatus;

    private String userAgent;

}
