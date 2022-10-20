package cn.microboat.service;

import cn.microboat.core.Return;
import cn.microboat.core.pojo.vo.UserVo;
import cn.microboat.factory.RemoteUserFallbackFactory;
import io.swagger.annotations.ApiParam;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

/**
 * @author zhouwei
 */
@FeignClient(contextId = "remoteUserService", value = "system", fallbackFactory = RemoteUserFallbackFactory.class)
public interface RemoteUserService {

    /**
     * 根据用户名获取用户信息
     *
     * @param username 用户名
     * @return Return<UserVo>
     */
    @GetMapping("/user/userInfo/{username}")
    Return<UserVo> userInfo(@ApiParam @PathVariable(value = "username") String username);

    /**
     * 查询所有的用户信息
     *
     * @return Return<List < UserVo>>
     */
    @GetMapping("/user/list")
    Return<List<UserVo>> list();

}
