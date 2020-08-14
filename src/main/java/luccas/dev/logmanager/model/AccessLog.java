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
@Table(name = "tb_access_log_aclo")
public class AccessLog {

    @Id
    @GeneratedValue(generator = "sq_access_log", strategy = GenerationType.SEQUENCE)
    @Column(name = "id_access_log", nullable = false)
    private Long id;

    @Column(name = "created_at", nullable = false)
    private Date created_at;

    @Column(name = "ip_address", nullable = false)
    private String ipAddress;

    @Column(name = "request_line", nullable = false)
    private String requestLine;

    @Column(name = "response_status", nullable = false)
    private Integer responseStatus;

    @Column(name = "user_agent", nullable = false)
    private String userAgent;
}
