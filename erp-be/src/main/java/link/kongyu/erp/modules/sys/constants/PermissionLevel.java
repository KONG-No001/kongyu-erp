package link.kongyu.erp.modules.sys.constants;

/**
 * @author Luojun
 * @version v1.0.0
 * @since 2025/12/3
 */
public enum PermissionLevel {
    NONE(0, "无权限"),
    VIEW(1, "查看权限"),      // 只能查看
    OPERATE(2, "操作权限"),   // 可操作但不可修改关键数据
    DOWNLOAD(3, "下载权限"),  // 可下载导出
    MODIFY(4, "修改权限"),    // 可修改数据
    DELETE(5, "删除权限"),    // 可删除数据
    MANAGE(6, "管理权限"),    // 完全管理
    ADMIN(7, "管理员权限");   // 系统管理员

    private final int level;
    private final String desc;

    PermissionLevel(int level, String desc) {
        this.level = level;
        this.desc = desc;
    }
}
