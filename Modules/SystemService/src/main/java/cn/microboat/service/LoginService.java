package cn.microboat.service;

import cn.microboat.core.Return;
import cn.microboat.core.pojo.dto.LoginUser;
import cn.microboat.core.pojo.dto.UserDto;
import cn.microboat.core.pojo.vo.UserVo;

/**
 * @author zhouwei
 */
public interface LoginService {

    /**
     * 注册
     *
     * @param userDto userDto
     * @return Return<UserVo>
     */
    Return<UserVo> register(UserDto userDto);

    /**
     * 登陆
     *
     * @param userDto userDto
     * @return Return<UserVo>
     */
    Return<LoginUser> login(UserDto userDto);

    /**
     * 重设密码
     *
     * @param userDto userDto
     * @return Return<UserVo>
     */
    Return<UserVo> resetPassword(UserDto userDto);
}
