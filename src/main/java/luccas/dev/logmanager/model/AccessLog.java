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
@Table(name = "tb_access_log_aclo", schema = "public")
public class AccessLog {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sq_access_log")
    @SequenceGenerator(name = "sq_access_log", sequenceName = "sq_access_log", allocationSize = 1)
    @Column(name = "id_access_log")
    private Long id;

    @Column(name = "created_at", nullable = false)
    private Date createdAt;

    @Column(name = "ip_address", nullable = false)
    private String ipAddress;

    @Column(name = "request_line", nullable = false)
    private String requestLine;

    @Column(name = "response_status", nullable = false)
    private Integer responseStatus;

    @Column(name = "user_agent", nullable = false)
    private String userAgent;

}
