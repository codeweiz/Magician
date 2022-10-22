package cn.microboat.dao;

import cn.microboat.core.pojo.entity.Task;

import java.util.List;

/**
 * @author zhouwei
 */
public interface TaskRepository {

    /**
     * 查询任务信息
     *
     * @param id 任务 id
     * @return Task 任务信息
     */
    Task selectTaskById(Integer id);

    /**
     * 查询任务信息列表
     *
     * @param task 任务信息
     * @return List<Task> 任务信息列表
     */
    List<Task> selectTaskList(Task task);

    /**
     * 新增任务信息
     *
     * @param task 任务信息
     * @return 是否新增成功
     */
    int insertTask(Task task);

    /**
     * 修改任务信息
     *
     * @param task 任务信息
     * @return 是否修改成功
     */
    int updateTask(Task task);

    /**
     * 根据 id 删除任务
     *
     * @param id 任务 id
     * @return 是否删除成功
     */
    int deleteTaskById(Integer id);

    /**
     * 根据 id 列表批量删除任务
     *
     * @param ids 任务 id 数组
     * @return 是否删除成功
     */
    int deleteTaskByIds(Integer[] ids);
}
