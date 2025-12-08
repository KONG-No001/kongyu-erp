package link.kongyu.erp.modules.sys.controller;

import link.kongyu.erp.common.domain.Result;
import link.kongyu.erp.modules.sys.service.PermissionBaseService;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class PermissionBaseControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void getInfoById() throws Exception {
        System.out.println(restTemplate.getForObject("/permission/info/1", Result.class));
    }

    @Test
    void addInfo() {
    }

    @Test
    void update() {
    }

    @Test
    void delete() {
    }

    @Test
    void enable() {
    }
}