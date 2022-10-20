package cn.microboat.dao;

import cn.microboat.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

/**
 * 用户数据库访问
 *
 * @author zhouwei
 */
@Repository
public interface UserRepository extends BaseMapper<User> {

    /**
     * 根据用户名查询用户信息
     *
     * @param username 用户名
     * @return User
     */
    User findUserByUsername(String username);
}
