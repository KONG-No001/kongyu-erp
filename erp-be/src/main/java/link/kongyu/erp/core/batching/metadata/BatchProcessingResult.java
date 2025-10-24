package link.kongyu.erp.core.batching.metadata;

import lombok.Getter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Luojun
 * @version v1.0.0
 * @since 2025/10/23
 */

public class BatchProcessingResult implements Serializable {
    private static final long serialVersionUID = -4588144118013764525L;

    @Getter
    private final int totalCount;
    private final AtomicInteger successCount = new AtomicInteger(0);
    private final AtomicInteger errorCount = new AtomicInteger(0);
    @Getter
    private final List<String> errorMassages = new ArrayList<>();

    public BatchProcessingResult(int totalCount) {
        this.totalCount = totalCount;
    }

    public int incrementSuccessCount() {
        return successCount.incrementAndGet();
    }

    public int getSuccessCount() {
        return successCount.get();
    }

    public int incrementErrorCount() {
        return errorCount.incrementAndGet();
    }

    public int getErrorCount() {
        return errorCount.get();
    }
}
