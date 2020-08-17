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
public class UploadHistoryDto {

    private String fileName;

    private Date uploadAt;

    private String uploadStatusMessage;
}
