package cn.microboat.factory;

import cn.microboat.core.Return;
import cn.microboat.core.pojo.vo.UserVo;
import cn.microboat.service.RemoteUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

import java.util.List;

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
        log.error("调用 System 服务下的 user 相关接口失败：{}", cause.getMessage());
        return new RemoteUserService() {

            /**
             * 根据用户名获取用户信息
             *
             * @param username 用户名
             * @return Return<UserVo>
             */
            @Override
            public Return<UserVo> userInfo(String username) {
                return Return.fail("调用 System 服务下的 userInfo 接口失败：" + cause.getMessage());
            }

            /**
             * 查询所有的用户信息
             *
             * @return Return<List < UserVo>>
             */
            @Override
            public Return<List<UserVo>> list() {
                return Return.fail("调用 System 服务下的 list 接口失败：" + cause.getMessage());
            }
        };
    }

}
