package luccas.dev.logmanager.repository;

import luccas.dev.logmanager.model.UploadFile;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional(readOnly = true)
public interface UploadFileRepository extends CrudRepository<UploadFile, Long>, JpaSpecificationExecutor<UploadFile> {
}
