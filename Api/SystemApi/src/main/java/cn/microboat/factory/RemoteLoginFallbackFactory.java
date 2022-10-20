package cn.microboat.factory;

import cn.microboat.core.Return;
import cn.microboat.core.pojo.dto.UserDto;
import cn.microboat.core.pojo.vo.UserVo;
import cn.microboat.service.RemoteLoginService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

/**
 * @author zhouwei
 */
@Component
@Slf4j
public class RemoteLoginFallbackFactory implements FallbackFactory<RemoteLoginService> {
    /**
     * Returns an instance of the fallback appropriate for the given cause.
     *
     * @param cause cause of an exception.
     * @return fallback
     */
    @Override
    public RemoteLoginService create(Throwable cause) {
        log.error("调用 System 服务下的 login 相关接口失败：{}", cause.getMessage());

        return new RemoteLoginService() {

            /**
             * 注册
             *
             * @param userDto 用户传入的参数
             * @return Return<UserVo>
             */
            @Override
            public Return<UserVo> register(UserDto userDto) {
                return Return.fail("调用 System 服务下的 register 接口失败：" + cause.getMessage());
            }

            /**
             * 登陆
             *
             * @param userDto 用户传入的参数
             * @return Return<UserVo>
             */
            @Override
            public Return<UserVo> login(UserDto userDto) {
                return Return.fail("调用 System 服务下的 login 接口失败：" + cause.getMessage());
            }

            /**
             * 重设密码
             *
             * @param userDto 用户传入的参数
             * @return Return<UserVo>
             */
            @Override
            public Return<UserVo> resetPassword(UserDto userDto) {
                return Return.fail("调用 System 服务下的 resetPassword 接口失败：" + cause.getMessage());
            }
        };
    }
}
