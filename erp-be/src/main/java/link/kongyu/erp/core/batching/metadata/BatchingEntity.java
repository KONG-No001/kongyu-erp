package link.kongyu.erp.core.batching.metadata;

import lombok.Getter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Luojun
 * @version v1.0.0
 * @since 2025/12/3
 */
public class BatchingEntity {
    private static final long serialVersionUID = 5430962067772534201L;

    @Getter
    private final int totalCount;

    private final AtomicInteger successCount = new AtomicInteger(0);
    private final AtomicInteger errorCount = new AtomicInteger(0);

    @Getter
    private final List<String> errorMessages = Collections.synchronizedList(new ArrayList<>());

    @Getter
    private ProcessingStatus status = ProcessingStatus.PENDING;

    public BatchingEntity(int totalCount) {
        if (totalCount < 0) {
            throw new IllegalArgumentException("总数量不能为负数");
        }
        this.totalCount = totalCount;
    }

    /*
     * 成功计数相关方法
     */

    /**
     * 成功计数+1
     */
    public int incrementSuccessCount() {
        int count = successCount.incrementAndGet();
        updateStatus();
        return count;
    }

    /**
     * 成功计数+count
     */
    public int addSuccessCount(int count) {
        int result = successCount.addAndGet(count);
        updateStatus();
        return result;
    }

    /**
     * 获取成功计数
     */
    public int getSuccessCount() {
        return successCount.get();
    }

    /*
     * 错误计数相关方法
     */

    /**
     * 错误计数+1
     */
    public int incrementErrorCount() {
        int count = errorCount.incrementAndGet();
        updateStatus();
        return count;
    }

    /**
     * 错误计数+1
     */
    public int addErrorCount(int count) {
        int result = errorCount.addAndGet(count);
        updateStatus();
        return result;
    }

    /**
     * 获取错误计数
     */
    public int getErrorCount() {
        return errorCount.get();
    }

    /*
     * 错误信息管理
     */

    /**
     * 新增一条错误计数和信息
     */
    public void addErrorMessage(String message) {
        errorMessages.add(message);
        errorCount.incrementAndGet();
        updateStatus();
    }

    /**
     * 批量新增错误计数和信息
     */
    public void addErrorMessages(List<String> messages) {
        errorMessages.addAll(messages);
        errorCount.addAndGet(messages.size());
        updateStatus();
    }

    /*
     * 状态和进度计算
     */

    /**
     * 获取处理计数
     */
    private int getProcessedCount() {
        return successCount.get() + errorCount.get();
    }

    /**
     * 是已完成
     */
    public boolean isCompleted() {
        return getProcessedCount() >= totalCount;
    }

    /**
     * 更新状态
     */
    private void updateStatus() {
        if (isCompleted()) {
            this.status = ProcessingStatus.COMPLETED;
        }
        else if (getProcessedCount() > 0) {
            this.status = ProcessingStatus.IN_PROGRESS;
        }
    }

    /**
     * 获取进度
     */
    public double getProgress() {
        if (totalCount == 0) { return 100.0; }
        int processed = getProcessedCount();
        return Math.min(100.0, (double) processed / totalCount * 100);
    }

    /**
     * 获取摘要
     */
    public String getSummary() {
        return String.format(
                "处理完成: %d/%d (成功: %d, 失败: %d, 进度: %.2f%%)",
                getProcessedCount(),
                totalCount,
                successCount.get(),
                errorCount.get(),
                getProgress()
        );
    }

    public BatchingResult toResult() {
        return new BatchingResult(
                totalCount,
                getSuccessCount(),
                getErrorCount(),
                getErrorMessages()
        );
    }

}
