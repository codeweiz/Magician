package cn.microboat.service;

import cn.microboat.core.Page;
import cn.microboat.core.Return;
import cn.microboat.core.pojo.dto.UserDto;
import cn.microboat.core.pojo.vo.UserVo;
import cn.microboat.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @author zhouwei
 */
public interface UserService extends IService<User> {

    /**
     * 添加一个用户
     *
     * @param userDto 用户传递的参数
     * @return Return<UserVo>
     */
    Return<UserVo> add(UserDto userDto);

    /**
     * 批量添加用户
     *
     * @param userDtoList 用户传递的参数
     * @return Return<List < UserVo>>
     */
    Return<List<UserVo>> batchAdd(List<UserDto> userDtoList);

    /**
     * 删除一个用户
     *
     * @param id id
     * @return Return<UserVo>
     */
    Return<UserVo> delete(Integer id);

    /**
     * 批量删除用户
     *
     * @param ids id 列表
     * @return Return<List < UserVo>>
     */
    Return<List<UserVo>> batchDelete(List<Integer> ids);

    /**
     * 更新一个用户
     *
     * @param userDto 用户传递的参数
     * @return Return<UserVo>
     */
    Return<UserVo> update(UserDto userDto);

    /**
     * 批量更新用户
     *
     * @param userDtoList 用户传递的参数
     * @return Return<List < UserVo>>
     */
    Return<List<UserVo>> batchUpdate(List<UserDto> userDtoList);

    /**
     * 根据用户名查询用户信息
     * 用户名唯一
     *
     * @param username 用户名
     * @return Return<UserVo>
     */
    Return<UserVo> getByUsername(String username);

    /**
     * 查询所有用户信息
     *
     * @return Return<UserVo>
     */
    Return<List<UserVo>> userList();

    /**
     * 分页查询所有用户信息
     *
     * @param page 分页
     * @return Return<UserVo>
     */
    Return<List<UserVo>> page(Page page);

    /**
     * 根据条件查询所有用户信息
     *
     * @param userDto 用户传递的参数
     * @return Return<UserVo>
     */
    Return<List<UserVo>> listByConditions(UserDto userDto);

    /**
     * 根据条件分页查询所有用户信息
     *
     * @param userDto 用户传递的参数
     * @param page 分页
     * @return Return<UserVo>
     */
    Return<List<UserVo>> pageByConditions(UserDto userDto, Page page);
}
