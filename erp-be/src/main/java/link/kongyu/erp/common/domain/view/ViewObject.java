package link.kongyu.erp.common.domain.view;

/**
 * 视图接口
 */
public interface ViewObject {

    /**
     * 基础视图
     */
    interface Basic extends ViewObject {}

    /**
     * 详情视图
     */
    interface Detail extends Basic {}

    /**
     * 列表视图
     */
    interface List extends Basic {}

    /**
     * 内部视图（不对外暴露）
     */
    interface Internal {}
}
