package luccas.dev.logmanager.service;

import lombok.extern.slf4j.Slf4j;
import luccas.dev.logmanager.model.AccessLog;
import luccas.dev.logmanager.repository.AccessLogRepository;
import luccas.dev.logmanager.utils.errors.CustomException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Slf4j
public class AccessLogService {

    private final Sort defaultSort = Sort.by("createdDate").descending();

    private AccessLogRepository accessLogRepository;

    public AccessLogService(AccessLogRepository accessLogRepository) {
        this.accessLogRepository = accessLogRepository;
    }

    public AccessLog findById(Long id) {
        return this.accessLogRepository.findById(id)
                .orElseThrow(() -> new CustomException("Log não encontrado!", HttpStatus.NOT_FOUND));
    }

    public Page<AccessLog> findAll(Pageable pageable) {
        Pageable pageableWithDefaultSort = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), defaultSort);
        return this.accessLogRepository.findAll(pageableWithDefaultSort);
    }

    public AccessLog create(AccessLog accessLog) {
        return this.accessLogRepository.save(accessLog);
    }

    public AccessLog update(AccessLog accessLog) {
        return this.accessLogRepository.save(accessLog);
    }

    @Transactional
    public void deleteById(Long accessLogId) {
        this.accessLogRepository.deleteById(accessLogId);
    }
}
