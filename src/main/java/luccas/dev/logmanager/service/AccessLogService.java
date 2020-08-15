package luccas.dev.logmanager.service;

import lombok.extern.slf4j.Slf4j;
import luccas.dev.logmanager.controller.v1.AccessLogDto;
import luccas.dev.logmanager.model.AccessLog;
import luccas.dev.logmanager.repository.AccessLogRepository;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class AccessLogService {

    private AccessLogRepository accessLogRepository;

    public AccessLogService(AccessLogRepository accessLogRepository) {
        this.accessLogRepository = accessLogRepository;
    }

    public AccessLog findById(Long id) throws Exception {
        return this.accessLogRepository.findById(id)
                .orElseThrow(() -> new Exception("Log não encontrado!"));
    }
}
