package luccas.dev.logmanager.service;

import lombok.extern.slf4j.Slf4j;
import luccas.dev.logmanager.controller.v1.UploadHistoryDto;
import luccas.dev.logmanager.model.AccessLog;
import luccas.dev.logmanager.model.UploadFile;
import luccas.dev.logmanager.model.UploadProcessEnum;
import luccas.dev.logmanager.repository.UploadFileRepository;
import luccas.dev.logmanager.utils.errors.CustomException;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class UploadFileService {

    private final AccessLogService accessLogService;
    private final UploadFileRepository uploadFileRepository;

    public UploadFileService(AccessLogService accessLogService, UploadFileRepository uploadFileRepository) {
        this.accessLogService = accessLogService;
        this.uploadFileRepository = uploadFileRepository;
    }

    @Async
    public void readFileAndPersist(final ByteArrayOutputStream  arrayOutputStream, Long uploadId) throws Exception {
        InputStream file = new ByteArrayInputStream(arrayOutputStream.toByteArray());

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        try {
            try (final BufferedReader br = new BufferedReader(new InputStreamReader(file))) {
                String line;
                while ((line = br.readLine()) != null) {
                    final String[] data = line.split("\\|",-1);
                    AccessLog accessLog = AccessLog.builder()
                            .createdAt(dateFormat.parse(data[0]))
                            .ipAddress(data[1])
                            .requestLine(data[2].replaceAll("^[\"']+|[\"']+$", ""))
                            .responseStatus(Integer.parseInt(data[3]))
                            .userAgent(data[4].replaceAll("^[\"']+|[\"']+$", ""))
                            .build();
                    this.accessLogService.create(accessLog);
                }
                log.info("File processed with success");
                this.updateStatusUploadFile(uploadId, UploadProcessEnum.SUCCESSFULY_PROCESSED);
            }
        } catch(final IOException e) {
            this.updateStatusUploadFile(uploadId, UploadProcessEnum.ERROR_ON_PROCESS);
            log.error("Failed to read file ", e);
            throw new CustomException("Failed to read file", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public UploadFile createUploadFile(String fileName) {
        return this.uploadFileRepository.save(
                UploadFile.builder()
                        .status(UploadProcessEnum.PROCESSING)
                        .fileName(fileName)
                        .uploadAt(new Date())
                        .build()
        );
    }

    private void updateStatusUploadFile(Long uploadId, UploadProcessEnum status) {
        Optional<UploadFile> uploadFile = this.uploadFileRepository.findById(uploadId);
        if (uploadFile.isEmpty())
            return;
        uploadFile.get().setStatus(status);
        this.uploadFileRepository.save(uploadFile.get());
    }

    public List<UploadFile> findAllUploadHistory() {
        return this.uploadFileRepository.findAll();
    }
}
