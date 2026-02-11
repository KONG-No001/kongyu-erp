package link.kongyu.erp.modules.myDemo;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonValue;
import link.kongyu.erp.common.domain.Result;
import lombok.Getter;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Luojun
 * @version v1.0.0
 * @since 2026/1/4
 */

@RestController
@RequestMapping("/my-demo")
public class MyDemoController {

    @GetMapping("/an-enum-format-example")
    public Result<Object> anEnumFormatExample() {
        Example example = new Example(ExampleEnum.DEMO);
        return Result.success(example);
    }

    @Getter
    public static enum ExampleEnum {
        DEMO("demo"),
        TEST("test");

        @JsonValue
        private final String value;

        ExampleEnum(String value) {
            this.value = value;
        }

        @JsonCreator
        public static ExampleEnum from(String code) {
            for (ExampleEnum status : ExampleEnum.values()) {
                if (status.getValue().equals(code)) {
                    return status;
                }
            }
            throw new IllegalArgumentException("Invalid status code: " + code);
        }
    }

    public static class Example {
        private final ExampleEnum anEnum;

        public Example(ExampleEnum anEnum) {
            this.anEnum = anEnum;
        }

        public ExampleEnum getAnEnum() {
            return anEnum;
        }
    }
}
