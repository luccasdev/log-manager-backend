package luccas.dev.logmanager.controller.v1;

import lombok.extern.slf4j.Slf4j;
import luccas.dev.logmanager.service.AccessLogService;
import luccas.dev.logmanager.utils.Pages;
import luccas.dev.logmanager.utils.dto.PageFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;


@Slf4j
@RestController
@RequestMapping("/access-log/v1")
public class AccessLogController {

    private final AccessLogService accessLogService;

    public AccessLogController(AccessLogService accessLogService) {
        this.accessLogService = accessLogService;
    }

    @GetMapping("/{id}")
    public AccessLogDto findById(@PathVariable("id") Long id) {
        return AccessLogMapper.entityToDto(this.accessLogService.findById(id));
    }

    @GetMapping
    public Page<AccessLogDto> findAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        return Pages.from(
                accessLogService.findAll(PageRequest.of(page, size)),
                AccessLogMapper::entityToDto
        );
    }

    @PostMapping
    public ResponseEntity<AccessLogDto> create(@RequestBody AccessLogDto accessLogDto) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(AccessLogMapper.entityToDto(accessLogService.create(AccessLogMapper.dtoToEntity(accessLogDto))));
    }

    @PutMapping
    public AccessLogDto update(@RequestBody AccessLogDto accessLogDto) {
        return AccessLogMapper.entityToDto(accessLogService.update(AccessLogMapper.dtoToEntity(accessLogDto)));
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Long accessLogId) {
        accessLogService.deleteById(accessLogId);
    }

    @PostMapping("/filter")
    public Page<AccessLogDto> findWithFilter(@RequestBody PageFilter<AccessLogFilter> pageFilter) {
        return Pages.from(
                accessLogService.findAllWithFilter(pageFilter),
                AccessLogMapper::entityToDto
        );
    }

    @PostMapping("/upload")
    public ResponseEntity<AccessLogUploadResultDto> upload(HttpServletRequest request) throws IOException {

        String filename = request.getParameter("file-name");
        String contentType = request.getContentType();
        InputStream file = request.getInputStream();

        log.info("Receive file: {} - with Content Type: {} to Upload.", filename, contentType);

        try {
            this.accessLogService.upload(file);
            log.info("File: {} - upload with Success.", filename);

            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(new AccessLogUploadResultDto("File upload with success", false));

        } catch (Exception exception) {
            log.error("File: {} - upload with Error: {}", filename, exception.getMessage());

            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new AccessLogUploadResultDto("File upload with error: " + exception.getMessage(), true));
        }
    }
}
