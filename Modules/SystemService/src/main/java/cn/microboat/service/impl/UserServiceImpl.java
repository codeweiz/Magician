package cn.microboat.service.impl;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.microboat.core.Page;
import cn.microboat.core.Return;
import cn.microboat.core.pojo.dto.UserDto;
import cn.microboat.core.pojo.entity.User;
import cn.microboat.core.pojo.vo.UserVo;
import cn.microboat.dao.UserRepository;
import cn.microboat.core.mapper.User2DtoMapper;
import cn.microboat.core.mapper.User2VoMapper;
import cn.microboat.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author zhouwei
 */
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final User2VoMapper user2VoMapper;
    private final User2DtoMapper user2DtoMapper;

    @SuppressWarnings("all")
    @Autowired
    UserServiceImpl(UserRepository userRepository, User2VoMapper user2VoMapper, User2DtoMapper user2DtoMapper) {
        this.userRepository = userRepository;
        this.user2VoMapper = user2VoMapper;
        this.user2DtoMapper = user2DtoMapper;
    }

    /**
     * 根据 id 查询用户信息
     *
     * @param id 主键
     * @return User
     */
    private User getUserById(Integer id) {
        return userRepository.selectUserById(id);
    }

    /**
     * 根据 username 查询用户信息
     *
     * @param username 用户名
     * @return User
     */
    private User getUserByUsername(String username) {
        User user = new User();
        user.setUsername(username);
        return userRepository.selectUserList(user).stream().limit(1L).collect(Collectors.toList()).get(0);
    }

    /**
     * 添加一个用户
     *
     * @param userDto 用户传递的参数
     * @return Return<UserVo>
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public Return<UserVo> add(UserDto userDto) {
        if (ObjectUtil.isEmpty(userDto)) {
            return Return.fail("userDto is empty");
        }
        User user = user2DtoMapper.modelToEntity(userDto);
        userRepository.insertUser(user);
        return Return.succeed(user2VoMapper.entityToModel(user));
    }

    /**
     * 批量添加用户
     *
     * @param userDtoList 用户传递的参数
     * @return Return<List < UserVo>>
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Return<List<UserVo>> batchAdd(List<UserDto> userDtoList) {
        if (ObjectUtil.isEmpty(userDtoList)) {
            return Return.fail("userDtoList is empty");
        }
        user2DtoMapper.modelsToEntities(userDtoList).forEach(userRepository::insertUser);
        return Return.succeed();
    }

    /**
     * 删除一个用户
     *
     * @param id id
     * @return Return<UserVo>
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Return<UserVo> delete(Integer id) {
        // 如果 id 为空，快速返回失败
        if (ObjectUtil.isEmpty(id)) {
            return Return.fail("id is empty");
        }

        User selectOne = this.getUserById(id);

        // 如果 id 对应的用户不存在，快速返回失败
        if (ObjectUtil.isEmpty(selectOne)) {
            return Return.fail("The user obtained by id is null");
        }

        // 如果 id 对应的用户存在，就把 deleteFlag 置为 1，表示逻辑删除
        userRepository.deleteUserById(id);
        return Return.succeed(user2VoMapper.entityToModel(selectOne));
    }

    /**
     * 批量删除用户
     *
     * @param ids id 列表
     * @return Return<List < UserVo>>
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Return<List<UserVo>> batchDelete(List<Integer> ids) {
        // 如果 ids 为空，快速返回失败
        if (ObjectUtil.isEmpty(ids)) {
            return Return.fail("ids has blank");
        }

        // 如果 ids 对应的用户列表存在，就把 deleteFlag 置为 1，表示逻辑删除
        userRepository.deleteUsersByIds(ids);
        return Return.succeed();
    }

    /**
     * 更新一个用户
     *
     * @param userDto 用户传递的参数
     * @return Return<UserVo>
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Return<UserVo> update(UserDto userDto) {
        // 如果 userDto 为空，快速返回失败
        if (ObjectUtil.isEmpty(userDto)) {
            return Return.fail("userDto is empty");
        }

        User user = this.getUserById(userDto.getId());

        // 如果 id 对应的用户不存在，快速返回失败
        if (ObjectUtil.isEmpty(user)) {
            return Return.fail("The user obtained by id is null");
        }

        // 如果 id 对应的用户存在，就动态地更新属性
        userRepository.updateUser(user);
        return Return.succeed();
    }

    /**
     * 批量更新用户
     *
     * @param userDtoList 用户传递的参数
     * @return Return<List < UserVo>>
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Return<List<UserVo>> batchUpdate(List<UserDto> userDtoList) {
        // 如果 userList 为空
        if (ObjectUtil.isEmpty(userDtoList)) {
            return Return.fail("userDtoList is empty");
        }

        // 如果 ids 对应的用户存在，就动态地更新属性
        user2DtoMapper.modelsToEntities(userDtoList).forEach(userRepository::updateUser);
        return Return.succeed();
    }

    /**
     * 根据用户名查询用户信息
     * 用户名唯一
     *
     * @param username 用户名
     * @return Return<UserVo>
     */
    @Override
    public Return<UserVo> getByUsername(String username) {
        // 如果 username 为空
        if (StrUtil.isBlankIfStr(username)) {
            return Return.fail("username is blank");
        }
        User user = getUserByUsername(username);
        // 查询的用户为 null
        if (ObjectUtil.isEmpty(user)) {
            return Return.fail("The user obtained by username is null");
        }
        return Return.succeed(user2VoMapper.entityToModel(user));
    }

    /**
     * 查询所有用户信息
     *
     * @return Return<UserVo>
     */
    @Override
    public Return<List<UserVo>> userList() {
        return Return.succeed(user2VoMapper.entitiesToModels(userRepository.selectUserList(null)));
    }

    /**
     * 分页查询所有用户信息
     *
     * @param page 分页
     * @return Return<UserVo>
     */
    @Override
    public Return<List<UserVo>> page(Page page) {
        return null;
    }

    /**
     * 根据条件查询所有用户信息
     *
     * @param userDto 用户传递的参数
     * @return Return<UserVo>
     */
    @Override
    public Return<List<UserVo>> listByConditions(UserDto userDto) {
        // 如果 userDto 为空，快速返回失败
        if (ObjectUtil.isEmpty(userDto)) {
            return Return.fail("userDto is empty");
        }
        return Return.succeed(user2VoMapper.entitiesToModels(userRepository.selectUserList(user2DtoMapper.modelToEntity(userDto))));
    }

    /**
     * 根据条件分页查询所有用户信息
     *
     * @param userDto 用户传递的参数
     * @param page    分页
     * @return Return<UserVo>
     */
    @Override
    public Return<List<UserVo>> pageByConditions(UserDto userDto, Page page) {
        return null;
    }
}
