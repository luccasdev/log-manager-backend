package luccas.dev.logmanager.controller.v1;

import lombok.extern.slf4j.Slf4j;
import luccas.dev.logmanager.model.UploadFile;
import luccas.dev.logmanager.service.AccessLogService;
import luccas.dev.logmanager.service.UploadFileService;
import luccas.dev.logmanager.utils.Pages;
import luccas.dev.logmanager.utils.dto.PageFilter;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;


@Slf4j
@RestController
@RequestMapping("/access-log/v1")
public class AccessLogController {

    private final AccessLogService accessLogService;
    private final UploadFileService uploadFileService;

    public AccessLogController(AccessLogService accessLogService, UploadFileService uploadFileService) {
        this.accessLogService = accessLogService;
        this.uploadFileService = uploadFileService;
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
    public ResponseEntity<AccessLogUploadResultDto> upload(@RequestParam("file") final MultipartFile multiPart) throws IOException, FileUploadException {

        log.info("Receive file: {} with Content Type: {} - to Upload.", multiPart.getOriginalFilename(), multiPart.getContentType());

        InputStream fileInputStream = multiPart.getInputStream();
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            fileInputStream.transferTo(byteArrayOutputStream);
            byteArrayOutputStream.flush();
            UploadFile uploadFile = this.uploadFileService.createUploadFile();
            this.uploadFileService.readFileAndPersist(byteArrayOutputStream, uploadFile.getId());

            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(new AccessLogUploadResultDto("File uploading in batch.", false));

        } catch (Exception exception) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new AccessLogUploadResultDto("Error on upload file to batch.", true));

        }

    }
}
