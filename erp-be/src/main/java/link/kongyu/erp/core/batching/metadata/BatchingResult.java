package link.kongyu.erp.core.batching.metadata;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 批处理结果跟踪器
 *
 * @author Luojun
 * @version v1.0.0
 * @since 2025/10/23
 */

@Data
@AllArgsConstructor
public class BatchingResult implements Serializable {
    private static final long serialVersionUID = -4588144118013764525L;

    private int total;

    private int successCount;

    private int errorCount;

    private List<String> errorMessages = null;
}
