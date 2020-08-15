package luccas.dev.logmanager.service;

import lombok.extern.slf4j.Slf4j;
import luccas.dev.logmanager.model.AccessLog;
import luccas.dev.logmanager.repository.AccessLogRepository;
import luccas.dev.logmanager.utils.errors.CustomException;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class AccessLogService {

    private AccessLogRepository accessLogRepository;

    public AccessLogService(AccessLogRepository accessLogRepository) {
        this.accessLogRepository = accessLogRepository;
    }

    public AccessLog findById(Long id) {
        return this.accessLogRepository.findById(id)
                .orElseThrow(() -> new CustomException("Log não encontrado!", HttpStatus.NOT_FOUND));
    }

    public Page<AccessLog> findAll() {
        return this.accessLogRepository.
    }
}
