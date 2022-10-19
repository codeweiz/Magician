package cn.microboat.service;

import cn.microboat.Return;
import cn.microboat.factory.RemoteUserFallbackFactory;
import cn.microboat.pojo.dto.UserDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author zhouwei
 */
@FeignClient(contextId = "remoteUserService", value = "system", fallbackFactory = RemoteUserFallbackFactory.class)
public interface RemoteUserService {

    /**
     * 用户登陆
     *
     * @param userDto 用户传输对象
     * @return Return<String>
     */
    @PostMapping("/user/login")
    Return<String> login(@RequestBody UserDto userDto);

    /**
     * 用户注册
     *
     * @param userDto 用户传输对象
     * @return Return<String>
     */
    @PostMapping("/user/register")
    Return<String> register(@RequestBody UserDto userDto);

    /**
     * 获取用户信息
     *
     * @param username 用户名
     * @return Return<UserDto>
     */
    @GetMapping("/user/userInfo/{username}")
    Return<UserDto> userInfo(@PathVariable(value = "username") String username);

}
