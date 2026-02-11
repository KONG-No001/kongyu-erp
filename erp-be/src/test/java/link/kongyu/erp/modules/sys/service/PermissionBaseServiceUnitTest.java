package link.kongyu.erp.modules.sys.service;

import link.kongyu.erp.common.domain.ResponseCode;
import link.kongyu.erp.common.exception.ServiceException;
import link.kongyu.erp.core.batching.metadata.BatchingResult;
import link.kongyu.erp.modules.sys.constants.PermissionType;
import link.kongyu.erp.modules.sys.entity.Permission;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Random;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@Transactional
class PermissionBaseServiceUnitTest {

    @Autowired
    private PermissionBaseService service;

    private long getUserId() {
        return 0;
    }

    @Test
    void getInfoById() {
    }

    /* ========== ADD ========== */

    @Test
    void addPermission1() {
        int i = new Random().nextInt();
        addPermission("TEST#" + i, "test:" + i);
    }

    @Test
    void addPermissionError1() {
        int i = new Random().nextInt();
        try {
            // 异常原因：组别相同，名称相同
            addPermission("TEST#1", "test:" + i);
            throw new RuntimeException("未按预期结果抛出异常");
        }
        catch (ServiceException e) {
            int code = e.getCode();
            int anticipate = ResponseCode.PARAM_VALID_ERROR.getCode();
            if (anticipate != code) { // code == 40001
                throw new RuntimeException("报错Code:" + code + " 与预期 " + anticipate + " 不一致");
            }
        }
    }

    @Test
    void addPermissionError2() {
        int i = new Random().nextInt();
        try {
            // 异常原因：资源编号相同
            addPermission("TEST#" + i, "test:1");
            throw new RuntimeException("未按预期结果抛出异常");
        }
        catch (ServiceException e) {
            int code = e.getCode();
            int anticipate = ResponseCode.PARAM_VALID_ERROR.getCode();
            if (anticipate != code) { // code == 40001
                throw new RuntimeException("报错Code:" + code + " 与预期 " + anticipate + " 不一致");
            }
        }
    }

    private void addPermission(String name, String resource) {
        Permission permission = new Permission();
        permission.setBusinessGroup("TEST");
        permission.setPermissionName(name);
        permission.setType(PermissionType.DATA);
        permission.setAccessResource(resource);
        service.addPermission(permission, getUserId());
    }

    /* ========== UPDATE ========== */

    @Test
    void updatePermission1() {
        int i = new Random().nextInt();
        updatePermission("TEST#" + i, "test:" + i);
    }

    @Test
    void updatePermissionError1() {
        int i = new Random().nextInt();
        try {
            // 异常原因：组别相同，名称与其他记录重复
            updatePermission("TEST#2", "test:1");
            throw new RuntimeException("未按预期结果抛出异常");
        }
        catch (ServiceException e) {
            int code = e.getCode();
            int anticipate = ResponseCode.PARAM_VALID_ERROR.getCode();
            if (anticipate != code) { // code == 40001
                throw new RuntimeException("报错Code:" + code + " 与预期 " + anticipate + " 不一致");
            }
        }
    }

    @Test
    void updatePermissionError2() {
        int i = new Random().nextInt();
        try {
            // 异常原因：资源编号与其他记录重复
            updatePermission("TEST#1", "test:2");
            throw new RuntimeException("未按预期结果抛出异常");
        }
        catch (ServiceException e) {
            int code = e.getCode();
            int anticipate = ResponseCode.PARAM_VALID_ERROR.getCode();
            if (anticipate != code) { // code == 40001
                throw new RuntimeException("报错Code:" + code + " 与预期 " + anticipate + " 不一致");
            }
        }
    }

    private void updatePermission(String name, String resource) {
        Permission permission = new Permission();
        permission.setId(-1L);
        permission.setBusinessGroup("TEST");
        permission.setPermissionName(name);
        permission.setType(PermissionType.DATA);
        permission.setAccessResource(resource);
        service.updatePermission(permission, getUserId());
    }

    /* ========== DELETE ========== */

    @Test
    void batchDeletePermission() {
        service.batchDeletePermission(new long[]{-1L, -2L}, getUserId());
    }

    /* ========== ENABLE ========== */

    @Test
    void batchEnablePermission() {
        BatchingResult batchingResult1 = service.batchEnablePermission(new long[]{-1L, -2L, -1000L}, true, getUserId());
        if (batchingResult1.getSuccessCount() != 2) {
            throw new RuntimeException("成功数量 " + batchingResult1.getSuccessCount() + " 与预期 2 不一致");
        }
        if (batchingResult1.getErrorCount() != 1) { // 失败原因：ID[-1000L]不存在
            throw new RuntimeException("失败数量 " + batchingResult1.getSuccessCount() + " 与预期 1 不一致");
        }
    }

    @Test
    void enablePermission() {
        service.enablePermission(-1L, true, getUserId());
    }

    @Test
    void enablePermissionError1() {
        try {
            // 异常原因：ID[-1000L]不存在
            service.enablePermission(-1000L, true, getUserId());
            throw new RuntimeException("未按预期结果抛出异常");
        }
        catch (ServiceException e) {
            int code = e.getCode();
            int anticipate = ResponseCode.PARAM_VALID_ERROR.getCode();
            if (anticipate != code) { // code == 40001
                throw new RuntimeException("报错Code:" + code + " 与预期 " + anticipate + " 不一致");
            }
        }
    }

}