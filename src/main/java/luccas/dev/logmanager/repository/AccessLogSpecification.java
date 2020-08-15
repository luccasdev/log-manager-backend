package luccas.dev.logmanager.repository;

import lombok.experimental.UtilityClass;
import luccas.dev.logmanager.model.AccessLog;
import org.springframework.data.jpa.domain.Specification;

import java.util.Date;
import java.util.Objects;

@UtilityClass
public class AccessLogSpecification {

    public final static Specification<AccessLog> byIpAddress(String ipAddress) {
        return (Specification<AccessLog>) (root, query, builder) -> {
            if (Objects.isNull(ipAddress))
                return null;
            return builder.equal(root.get("ipAddress"), ipAddress);
        };
    }
    public final static Specification<AccessLog> byCreatedDate(Date startDate, Date endDate) {
        return (Specification<AccessLog>) (root, query, builder) -> {
            if (startDate == null || endDate == null)
                return null;
            return builder.between(root.get("createdDate"), startDate, endDate);
        };
    }

    public final static Specification<AccessLog> byUserAgent(String userAgent) {
        return (Specification<AccessLog>) (root, query, builder) -> {
            if (Objects.isNull(userAgent))
                return null;
            return builder.like(root.get("userAgent"), userAgent);
        };
    }

}
