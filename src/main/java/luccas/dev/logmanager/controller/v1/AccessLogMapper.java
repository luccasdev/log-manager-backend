package luccas.dev.logmanager.controller.v1;

import luccas.dev.logmanager.model.AccessLog;
import luccas.dev.logmanager.model.UploadFile;
import luccas.dev.logmanager.utils.Dates;

import java.util.Objects;

public abstract class AccessLogMapper {

    public AccessLogMapper() {}

    public static AccessLogDto entityToDto(AccessLog entity) {
        if (Objects.isNull(entity)) return new AccessLogDto();
        return AccessLogDto.builder()
                .createdAt(entity.getCreatedAt())
                .id(entity.getId())
                .ipAddress(entity.getIpAddress())
                .requestLine(entity.getRequestLine())
                .responseStatus(entity.getResponseStatus())
                .userAgent(entity.getUserAgent())
                .build();
    }

    public static AccessLogListDto entityToAccessLogListDto(AccessLog entity) {
        if (Objects.isNull(entity)) return new AccessLogListDto();
        return AccessLogListDto.builder()
                .createdAt(Dates.to(entity.getCreatedAt(), "yyyy-MM-dd HH:mm:ss.SSS"))
                .id(entity.getId())
                .ipAddress(entity.getIpAddress())
                .requestLine(entity.getRequestLine())
                .responseStatus(entity.getResponseStatus())
                .userAgent(entity.getUserAgent())
                .build();
    }

    public static AccessLog dtoToEntity(AccessLogDto dto) {
        if (Objects.isNull(dto)) return new AccessLog();
        return AccessLog.builder()
                .createdAt(dto.getCreatedAt())
                .id(dto.getId())
                .ipAddress(dto.getIpAddress())
                .requestLine(dto.getRequestLine())
                .responseStatus(dto.getResponseStatus())
                .userAgent(dto.getUserAgent())
                .build();
    }

    public static UploadHistoryDto uploadEntityToDto(UploadFile uploadFile) {
        if (Objects.isNull(uploadFile)) return new UploadHistoryDto();
        return UploadHistoryDto.builder()
                .fileName(uploadFile.getFileName())
                .uploadAt(Dates.to(uploadFile.getUploadAt(), "yyyy-MM-dd HH:mm:ss.SSS"))
                .uploadStatusMessage(uploadFile.getStatus().getName())
                .build();
    }
}
