package cn.microboat.dao;

import cn.microboat.core.pojo.entity.User;

import java.util.List;

/**
 * 用户数据库访问
 *
 * @author zhouwei
 */
public interface UserRepository {

    /**
     * 根据 id 查询用户信息
     *
     * @param id 用户 id
     * @return User 用户信息
     */
    User selectUserById(Integer id);

    /**
     * 根据 id 数组查询用户信息列表
     *
     * @param ids 用户 id 列表
     * @return List<User> 用户信息列表
     */
    List<User> selectUsersByIds(List<Integer> ids);

    /**
     * 查询用户信息列表
     *
     * @param user 用户信息
     * @return List<User> 用户信息列表
     */
    List<User> selectUserList(User user);

    /**
     * 新增用户信息
     *
     * @param user 用户信息
     * @return 是否新增成功
     */
    int insertUser(User user);

    /**
     * 修改用户信息
     *
     * @param user 用户信息
     * @return 是否修改成功
     */
    int updateUser(User user);

    /**
     * 根据 id 删除用户
     *
     * @param id 用户 id
     * @return 是否删除成功
     */
    int deleteUserById(Integer id);

    /**
     * 根据 id 列表批量删除用户
     *
     * @param ids 用户 id 列表
     * @return 是否删除成功
     */
    int deleteUsersByIds(List<Integer> ids);
}
