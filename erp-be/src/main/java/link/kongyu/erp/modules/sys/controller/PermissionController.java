package link.kongyu.erp.modules.sys.controller;

import link.kongyu.erp.modules.sys.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Luojun
 * @version v1.0.0
 * @since 2026/4/17
 */
@RestController
@RequestMapping("/permission")
public class PermissionController {

    @Autowired
    private PermissionService permissionService;
}
