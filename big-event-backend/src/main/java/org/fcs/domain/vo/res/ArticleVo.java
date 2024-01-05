package org.fcs.domain.vo.res;

import lombok.Builder;
import lombok.Data;
import org.fcs.model.pojo.BaseEntity;

import java.io.Serial;

/**
 * @author alleyf
 * @version 1.0
 * @date 2024/1/5 19:55
 */
@Data
@Builder
public class ArticleVo extends BaseEntity {
    @Serial
    private static final long serialVersionUID = 1L;
    private String categoryName;
}
