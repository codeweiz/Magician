package cn.microboat.factory;

import cn.microboat.core.Return;
import cn.microboat.core.pojo.dto.UserDto;
import cn.microboat.service.RemoteUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

/**
 * 用户服务降级处理
 *
 * @author zhouwei
 */
@Component
@Slf4j
public class RemoteUserFallbackFactory implements FallbackFactory<RemoteUserService> {
    @Override
    public RemoteUserService create(Throwable cause) {
        log.error("调用 System 服务下的登陆接口失败：{}", cause.getMessage());
        return new RemoteUserService() {
            @Override
            public Return<String> login(UserDto userDto) {
                return Return.fail("调用 System 服务下的 login 接口失败：" + cause.getMessage());
            }

            @Override
            public Return<String> register(UserDto userDto) {
                return Return.fail("调用 System 服务下的 register 接口失败：" + cause.getMessage());
            }

            @Override
            public Return<UserDto> userInfo(String username) {
                return Return.fail("调用 System 服务下的 userInfo 接口失败：" + cause.getMessage());
            }
        };
    }

}
