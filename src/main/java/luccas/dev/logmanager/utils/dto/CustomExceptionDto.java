package luccas.dev.logmanager.utils.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
public class CustomExceptionDto {

    private String message;
    private int status;
    private String error;
    private String path;
    private Date timestamp;
}
