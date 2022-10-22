package cn.microboat.service.impl;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.microboat.core.Return;
import cn.microboat.core.mapper.User2DtoMapper;
import cn.microboat.core.mapper.User2VoMapper;
import cn.microboat.core.pojo.dto.LoginUser;
import cn.microboat.core.pojo.dto.UserDto;
import cn.microboat.core.pojo.entity.User;
import cn.microboat.core.pojo.vo.UserVo;
import cn.microboat.core.utils.CryptoUtils;
import cn.microboat.dao.UserRepository;
import cn.microboat.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author zhouwei
 */
@Service
public class LoginServiceImpl implements LoginService {

    private final UserRepository userRepository;
    private final User2VoMapper user2VoMapper;
    private final User2DtoMapper user2DtoMapper;

    @SuppressWarnings("all")
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
        User user = new User();
        user.setUsername(username);
        List<User> users = userRepository.selectUserList(user);
        return users.stream().limit(1L).collect(Collectors.toList()).get(0);
    }

    /**
     * 判断用户名在 user 表中是不是存在
     *
     * @param username 用户名
     * @return 是否存在
     */
    private boolean checkExists(String username) {
        User user = new User();
        user.setUsername(username);
        List<User> users = userRepository.selectUserList(user);
        return !ObjectUtil.isEmpty(users);
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
        if (checkExists(userDto.getUsername())) {
            return Return.fail("username is already exists");
        }

        // 密码为空
        if (StrUtil.isBlankIfStr(userDto.getPassword())) {
            return Return.fail("password is blank");
        }

        User user = user2DtoMapper.modelToEntity(userDto);
        // 密码 AES 加密
        user.setPassword(CryptoUtils.AES.encryptHex(user.getPassword()));
        userRepository.insertUser(user);
        return Return.succeed(user2VoMapper.entityToModel(user));
    }

    /**
     * 登陆
     *
     * @param userDto userDto
     * @return Return<UserVo>
     */
    @Override
    public Return<LoginUser> login(UserDto userDto) {
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
            LoginUser loginUser = new LoginUser();
            loginUser.setUser(user);
            return Return.succeed(loginUser);
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

        this.userRepository.updateUser(user);
        return Return.succeed(user2VoMapper.entityToModel(user));
    }
}
