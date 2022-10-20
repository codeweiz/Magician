package cn.microboat.service.impl;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.microboat.core.Page;
import cn.microboat.core.Return;
import cn.microboat.core.pojo.dto.UserDto;
import cn.microboat.core.pojo.vo.UserVo;
import cn.microboat.dao.UserRepository;
import cn.microboat.entity.User;
import cn.microboat.mapper.User2DtoMapper;
import cn.microboat.mapper.User2VoMapper;
import cn.microboat.service.UserService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author zhouwei
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserRepository, User> implements UserService {

    private final UserRepository userRepository;
    private final User2VoMapper user2VoMapper;
    private final User2DtoMapper user2DtoMapper;

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
        return userRepository.selectOne(new QueryWrapper<User>().eq("id", id).eq("delete_flag", 0));
    }

    /**
     * 根据 username 查询用户信息
     *
     * @param username 用户名
     * @return User
     */
    private User getUserByUsername(String username) {
        return userRepository.selectOne(new QueryWrapper<User>().eq("username", username).eq("delete_flag", 0));
    }

    /**
     * 添加一个用户
     *
     * @param userDto 用户传递的参数
     * @return Return<UserVo>
     */
    @Override
    public Return<UserVo> add(UserDto userDto) {
        if (ObjectUtil.isEmpty(userDto)) {
            return Return.fail("userDto is empty");
        }
        User user = user2DtoMapper.modelToEntity(userDto);
        userRepository.insert(user);
        return Return.succeed(user2VoMapper.entityToModel(user));
    }

    /**
     * 批量添加用户
     *
     * @param userDtoList 用户传递的参数
     * @return Return<List < UserVo>>
     */
    @Override
    public Return<List<UserVo>> batchAdd(List<UserDto> userDtoList) {
        if (ObjectUtil.isEmpty(userDtoList)) {
            return Return.fail("userDtoList is empty");
        }
        if (this.saveBatch(user2DtoMapper.modelsToEntities(userDtoList))) {
            return Return.succeed(user2VoMapper.entitiesToModels(user2DtoMapper.modelsToEntities(userDtoList)));
        }
        return Return.fail("fail to saveBatch userDtoList");
    }

    /**
     * 删除一个用户
     *
     * @param id id
     * @return Return<UserVo>
     */
    @Override
    public Return<UserVo> delete(Integer id) {
        // 如果 id 为空或空串，快速返回失败
        if (StrUtil.isBlankIfStr(id)) {
            return Return.fail("id is blank");
        }

        User selectOne = this.getUserById(id);

        // 如果 id 对应的用户不存在，快速返回失败
        if (ObjectUtil.isEmpty(selectOne)) {
            return Return.fail("The user obtained by id is null");
        }

        // 如果 id 对应的用户存在，就把 deleteFlag 置为 1，表示逻辑删除
        selectOne.setDeleteFlag(1);
        userRepository.insert(selectOne);
        return Return.succeed(user2VoMapper.entityToModel(selectOne));
    }

    /**
     * 批量删除用户
     *
     * @param ids id 列表
     * @return Return<List < UserVo>>
     */
    @Override
    public Return<List<UserVo>> batchDelete(List<Integer> ids) {
        // 如果 ids 中有空串
        if (StrUtil.hasBlank((CharSequence) ids)) {
            return Return.fail("ids has blank");
        }

        List<User> userList = userRepository.selectBatchIds(ids);

        // 如果 ids 对应的用户存在，就把 deleteFlag 置为 1，表示逻辑删除
        userList.forEach(user -> user.setDeleteFlag(1));
        this.saveBatch(userList);
        return Return.succeed(user2VoMapper.entitiesToModels(userList));
    }

    /**
     * 更新一个用户
     *
     * @param userDto 用户传递的参数
     * @return Return<UserVo>
     */
    @Override
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
        return Return.succeed(user2VoMapper.entityToModel(user));
    }

    /**
     * 批量更新用户
     *
     * @param userDtoList 用户传递的参数
     * @return Return<List < UserVo>>
     */
    @Override
    public Return<List<UserVo>> batchUpdate(List<UserDto> userDtoList) {
        // 如果 userList 为空
        if (ObjectUtil.isEmpty(userDtoList)) {
            return Return.fail("userDtoList is empty");
        }

        List<Integer> ids = userDtoList.stream().map(UserDto::getId).collect(Collectors.toList());
        List<User> userList = userRepository.selectBatchIds(ids);

        // 如果 ids 对应的用户存在，就动态地更新属性
        return Return.succeed(user2VoMapper.entitiesToModels(userList));
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
        return Return.succeed(user2VoMapper.entitiesToModels(userRepository.selectList(null)));
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
        return null;
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
