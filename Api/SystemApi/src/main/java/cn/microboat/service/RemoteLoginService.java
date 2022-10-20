package cn.microboat.service;

import cn.microboat.core.Return;
import cn.microboat.core.pojo.dto.UserDto;
import cn.microboat.core.pojo.vo.UserVo;
import cn.microboat.factory.RemoteLoginFallbackFactory;
import io.swagger.annotations.ApiParam;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author zhouwei
 */
@FeignClient(contextId = "remoteLoginService", value = "system", fallbackFactory = RemoteLoginFallbackFactory.class)
public interface RemoteLoginService {

    /**
     * 注册
     *
     * @param userDto 用户传入的参数
     * @return Return<UserVo>
     */
    @PostMapping("/login/register")
    Return<UserVo> register(@ApiParam @RequestBody UserDto userDto);

    /**
     * 登陆
     *
     * @param userDto 用户传入的参数
     * @return Return<UserVo>
     */
    @PostMapping("/login/login")
    Return<UserVo> login(@ApiParam @RequestBody UserDto userDto);

    /**
     * 重设密码
     *
     * @param userDto 用户传入的参数
     * @return Return<UserVo>
     */
    @PostMapping("/login/resetPassword")
    Return<UserVo> resetPassword(@ApiParam @RequestBody UserDto userDto);
}
