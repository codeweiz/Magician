package cn.microboat.service.impl;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.microboat.core.Return;
import cn.microboat.core.pojo.dto.UserDto;
import cn.microboat.core.pojo.vo.UserVo;
import cn.microboat.core.utils.CryptoUtils;
import cn.microboat.dao.UserRepository;
import cn.microboat.entity.User;
import cn.microboat.mapper.User2DtoMapper;
import cn.microboat.mapper.User2VoMapper;
import cn.microboat.service.LoginService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author zhouwei
 */
@Service
public class LoginServiceImpl extends ServiceImpl<UserRepository, User> implements LoginService {

    private final UserRepository userRepository;
    private final User2VoMapper user2VoMapper;
    private final User2DtoMapper user2DtoMapper;

    @Autowired
    LoginServiceImpl(UserRepository userRepository, User2VoMapper user2VoMapper, User2DtoMapper user2DtoMapper) {
        this.userRepository = userRepository;
        this.user2VoMapper = user2VoMapper;
        this.user2DtoMapper = user2DtoMapper;
    }

    /**
     * 根据用户名获取用户信息
     *
     * @param username 用户名
     * @return User
     */
    private User getUserByUsername(String username) {
        return userRepository.selectOne(new QueryWrapper<User>().eq("username", username));
    }


    /**
     * 注册
     *
     * @param userDto userDto
     * @return Return<UserVo>
     */
    @Override
    public Return<UserVo> register(UserDto userDto) {
        // 用户名为空
        if (StrUtil.isBlankIfStr(userDto.getUsername())) {
            return Return.fail("username is blank");
        }

        // 数据库里已经有了这个 username 的用户
        User userByUsername = getUserByUsername(userDto.getUsername());
        if (ObjectUtil.isNotEmpty(userByUsername)) {
            return Return.fail("username is already exists");
        }

        // 密码为空
        if (StrUtil.isBlankIfStr(userDto.getPassword())) {
            return Return.fail("password is blank");
        }

        User user = user2DtoMapper.modelToEntity(userDto);
        // 密码 AES 加密
        user.setPassword(CryptoUtils.AES.encryptHex(user.getPassword()));
        this.save(user);
        return Return.succeed(user2VoMapper.entityToModel(user));
    }

    /**
     * 登陆
     *
     * @param userDto userDto
     * @return Return<UserVo>
     */
    @Override
    public Return<UserVo> login(UserDto userDto) {
        // username 为空
        if (StrUtil.isBlankIfStr(userDto.getUsername())) {
            return Return.fail("username is blank");
        }

        // password 为空
        if (StrUtil.isBlankIfStr(userDto.getPassword())) {
            return Return.fail("password is blank");
        }

        User user = getUserByUsername(userDto.getUsername());

        // 数据库中没有这个用户名的用户
        if (ObjectUtil.isEmpty(user)) {
            return Return.fail("user obtained by username of userDto is null");
        }

        // 密码正确
        if (user.getPassword().equals(CryptoUtils.AES.encryptHex(userDto.getPassword()))) {
            return Return.succeed(user2VoMapper.entityToModel(user));
        }

        // 密码不正确
        return Return.fail("The password is not correct");
    }

    /**
     * 重设密码
     *
     * @param userDto userDto
     * @return Return<UserVo>
     */
    @Override
    public Return<UserVo> resetPassword(UserDto userDto) {
        // username 为空
        if (StrUtil.isBlankIfStr(userDto.getUsername())) {
            return Return.fail("username is blank");
        }

        User user = getUserByUsername(userDto.getUsername());

        // 数据库中没有这个用户名的用户
        if (ObjectUtil.isEmpty(user)) {
            return Return.fail("user obtained by username of userDto is null");
        }

        // 修改密码
        user.setPassword(CryptoUtils.AES.encryptHex(userDto.getPassword()));

        this.saveOrUpdate(user);
        return Return.succeed(user2VoMapper.entityToModel(user));
    }
}
