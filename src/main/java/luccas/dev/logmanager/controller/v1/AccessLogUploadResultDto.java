package luccas.dev.logmanager.controller.v1;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccessLogUploadResultDto {

    private String message;

    private boolean hasError;
}
