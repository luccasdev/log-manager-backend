package luccas.dev.logmanager.controller.v1;

import luccas.dev.logmanager.service.AccessLogService;
import luccas.dev.logmanager.utils.Pages;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

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
    public AccessLogDto create(@RequestBody AccessLogDto accessLogDto) {
        return AccessLogMapper.entityToDto(accessLogService.create(AccessLogMapper.dtoToEntity(accessLogDto)));
    }

    @PutMapping
    public AccessLogDto update(@RequestBody AccessLogDto accessLogDto) {
        return AccessLogMapper.entityToDto(accessLogService.update(AccessLogMapper.dtoToEntity(accessLogDto)));
    }

    @DeleteMapping("/{id}")
    public AccessLogDto delete(@PathVariable("id") Long accessLogId) {
        accessLogService.deleteById(accessLogId);
    }
}
