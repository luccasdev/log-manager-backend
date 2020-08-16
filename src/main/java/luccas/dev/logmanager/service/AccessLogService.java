package luccas.dev.logmanager.service;

import lombok.extern.slf4j.Slf4j;
import luccas.dev.logmanager.controller.v1.AccessLogFilter;
import luccas.dev.logmanager.model.AccessLog;
import luccas.dev.logmanager.repository.AccessLogRepository;
import luccas.dev.logmanager.repository.AccessLogSpecification;
import luccas.dev.logmanager.utils.dto.PageFilter;
import luccas.dev.logmanager.utils.errors.CustomException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Date;
import java.util.Objects;


@Service
@Slf4j
public class AccessLogService {

    private final Sort defaultSort = Sort.by("createdAt").descending();

    private final AccessLogRepository accessLogRepository;

    public AccessLogService(AccessLogRepository accessLogRepository) {
        this.accessLogRepository = accessLogRepository;
    }

    public AccessLog findById(Long id) {
        return this.accessLogRepository.findById(id)
                .orElseThrow(() -> new CustomException("Log não encontrado.", HttpStatus.NOT_FOUND));
    }

    public Page<AccessLog> findAll(Pageable pageable) {
        Pageable pageableWithDefaultSort = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), defaultSort);
        return this.accessLogRepository.findAll(pageableWithDefaultSort);
    }

    public Page<AccessLog> findAllWithFilter(PageFilter<AccessLogFilter> pageFilter) {
        Sort sort;
        if (Objects.isNull(pageFilter.getDirection()) && Objects.isNull(pageFilter.getField())) {
            sort = defaultSort;
        } else {
            sort = Sort.by(pageFilter.getDirection(), pageFilter.getField());
        }

        Pageable pageableWithSort = PageRequest.of(pageFilter.getPageNumber(), pageFilter.getPageSize(), sort);

        return this.accessLogRepository.findAll(
                AccessLogSpecification.byCreatedAt(pageFilter.getFilter().getCreateDateRange())
                        .and(AccessLogSpecification.byIpAddress(pageFilter.getFilter().getIpAddress()))
                        .and(AccessLogSpecification.byUserAgent(pageFilter.getFilter().getUserAgent())), pageableWithSort);
    }

    public AccessLog create(AccessLog accessLog) {
        return this.accessLogRepository.save(accessLog);
    }

    public AccessLog update(AccessLog accessLog) {
        boolean exists = this.accessLogRepository.existsById(accessLog.getId());
        if (!exists)
            throw new CustomException("ID do log não existente.", HttpStatus.UNPROCESSABLE_ENTITY);
        return this.accessLogRepository.save(accessLog);
    }

    @Transactional
    public void deleteById(Long accessLogId) {
        boolean exists = this.accessLogRepository.existsById(accessLogId);
        if (!exists)
            throw new CustomException("ID do log não existente.", HttpStatus.UNPROCESSABLE_ENTITY);
        this.accessLogRepository.deleteById(accessLogId);
    }

    public void upload(InputStream file) throws IOException {
        String text = "";
        BufferedReader bfReader = new BufferedReader(new InputStreamReader(file));

        while ((text = bfReader.readLine()) != null) {
            String[] data = text.split("\\|");
            AccessLog accessLog = AccessLog.builder()
                    .createdAt(new Date())
                    .ipAddress(data[1])
                    .requestLine(data[2].replaceAll("^[\"']+|[\"']+$", ""))
                    .responseStatus(Integer.parseInt(data[3]))
                    .userAgent(data[4].replaceAll("^[\"']+|[\"']+$", ""))
                    .build();
            this.accessLogRepository.save(accessLog);
        }
    }
}
