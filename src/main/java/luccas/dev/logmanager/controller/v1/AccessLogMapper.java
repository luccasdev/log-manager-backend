package luccas.dev.logmanager.controller.v1;

import luccas.dev.logmanager.model.AccessLog;

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
}
