package link.kongyu.erp.core.batching.actuator;

import link.kongyu.erp.core.batching.metadata.BatchProcessingResult;

/**
 * @author Luojun
 * @version v1.0.0
 * @since 2025/10/23
 */
public interface BatchProcessingActuator {
    BatchProcessingResult execute();
}
