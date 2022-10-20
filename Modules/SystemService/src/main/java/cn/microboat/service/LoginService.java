package cn.microboat.service;

import cn.microboat.core.Return;
import cn.microboat.core.pojo.dto.UserDto;
import cn.microboat.core.pojo.vo.UserVo;
import cn.microboat.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @author zhouwei
 */
public interface LoginService extends IService<User> {

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
    Return<UserVo> login(UserDto userDto);

    /**
     * 重设密码
     *
     * @param userDto userDto
     * @return Return<UserVo>
     */
    Return<UserVo> resetPassword(UserDto userDto);
}
