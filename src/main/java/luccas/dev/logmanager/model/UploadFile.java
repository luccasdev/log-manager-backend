package luccas.dev.logmanager.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tb_upload_file_upfi", schema = "public")
public class UploadFile {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sq_upload_file")
    @SequenceGenerator(name = "sq_upload_file", sequenceName = "sq_upload_file", allocationSize = 1)
    @Column(name = "id_upload_file")
    private Long id;

    @Column(name = "file_name")
    private String fileName;

    @Column(name = "upload_at", nullable = false)
    private Date uploadAt;

    @Enumerated
    @Column(name = "import_process_status", columnDefinition = "smallint")
    private UploadProcessEnum status;

}
