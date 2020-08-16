package luccas.dev.logmanager.controller.v1;

import luccas.dev.logmanager.service.AccessLogService;
import luccas.dev.logmanager.utils.Pages;
import luccas.dev.logmanager.utils.dto.PageFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;


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
    public void upload(HttpServletRequest request, HttpServletResponse response) throws IOException {

        String filename = request.getParameter("file-name");
        String contentType = request.getContentType();
        InputStream file = request.getInputStream();

        System.out.println(file);
        System.out.println(contentType);
        System.out.println(filename);
        this.accessLogService.upload(file);
    }
}
