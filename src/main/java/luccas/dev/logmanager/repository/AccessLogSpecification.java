package luccas.dev.logmanager.repository;

import luccas.dev.logmanager.model.AccessLog;
import org.junit.platform.commons.util.StringUtils;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class AccessLogSpecification {

    public static Specification<AccessLog> byIpAddress(String ipAddress) {
        return new Specification<>() {
            @Override
            public Predicate toPredicate(Root<AccessLog> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
                if (StringUtils.isNotBlank(ipAddress))
                    return builder.equal(root.get("ipAddress"), ipAddress);
                return null;
            }
        };
    }
    public static Specification<AccessLog> byCreatedAt(List<Date> rangeDate) {
        return new Specification<>() {
            @Override
            public Predicate toPredicate(Root<AccessLog> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
                if (Objects.nonNull(rangeDate) && Objects.nonNull(rangeDate.get(0)) && Objects.nonNull(rangeDate.get(1)))
                    return builder.between(root.get("createdAt"), rangeDate.get(0), rangeDate.get(1));
                return null;
            }
        };
    }

    public static Specification<AccessLog> byUserAgent(String userAgent) {
        return new Specification<>() {
            @Override
            public Predicate toPredicate(Root<AccessLog> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
                if (StringUtils.isNotBlank(userAgent))
                    return builder.like(root.get("userAgent"), "%" + userAgent + "%");
                return null;
            }
        };
    }
}
