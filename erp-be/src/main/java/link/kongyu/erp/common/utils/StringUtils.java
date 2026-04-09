package link.kongyu.erp.common.utils;

/**
 * 字符串工具类
 *
 * @author Luojun
 * @version v1.0.0
 * @since 2026/4/8
 */
public class StringUtils {

    /**
     * 驼峰命名转下划线命名
     * 例如: userName -> user_name
     *
     * @param camelCaseStr 驼峰命名字符串
     * @return 下划线命名字符串
     */
    public static String camelToUnderline(String camelCaseStr) {
        if (camelCaseStr == null || camelCaseStr.trim().isEmpty()) {
            return "";
        }

        int len = camelCaseStr.length();
        StringBuilder sb = new StringBuilder(len + (len >> 1));

        for (int i = 0; i < len; i++) {
            char c = camelCaseStr.charAt(i);
            if (Character.isUpperCase(c)) {
                if (i > 0) {
                    sb.append("_");
                }
                sb.append(Character.toLowerCase(c));
            }
            else {
                sb.append(c);
            }
        }
        return sb.toString();
    }

    /**
     * 下划线命名转驼峰命名
     * 例如: user_name -> userName
     *
     * @param underlineStr 下划线名字符串
     * @return 下划线命名字符串
     */
    public static String underlineToCamel(String underlineStr) {
        if (underlineStr == null || underlineStr.trim().isEmpty()) {
            return "";
        }
        int len = underlineStr.length();
        StringBuilder sb = new StringBuilder(len);
        boolean nextUpperCase = false;
        for (int i = 0; i < len; i++) {
            char c = underlineStr.charAt(i);
            if (c == '_') {
                if (sb.length() > 0) {
                    nextUpperCase = true;
                }
            }
            else {
                if (nextUpperCase) {
                    sb.append(Character.toUpperCase(c));
                    nextUpperCase = false;
                }
                else {
                    sb.append(Character.toLowerCase(c));
                }
            }
        }
        return sb.toString();
    }
}
