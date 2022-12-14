package cn.microboat.service.impl;

import cn.microboat.core.pojo.entity.Task;
import cn.microboat.dao.TaskRepository;
import cn.microboat.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 任务服务接口的实现类
 *
 * @author zhouwei
 */
@Service
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;

    @SuppressWarnings("all")
    @Autowired
    TaskServiceImpl(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    /**
     * 根据 id 查询任务信息
     *
     * @param id 任务 id
     * @return Task 任务信息
     */
    @Override
    public Task selectTaskById(Integer id) {
        return taskRepository.selectTaskById(id);
    }

    /**
     * 查询任务信息列表
     *
     * @param task 任务信息
     * @return List<Task> 任务信息列表
     */
    @Override
    public List<Task> selectTaskList(Task task) {
        return taskRepository.selectTaskList(task);
    }

    /**
     * 新增任务信息
     *
     * @param task 任务信息
     * @return 是否新增成功
     */
    @Override
    public int insertTask(Task task) {
        return taskRepository.insertTask(task);
    }

    /**
     * 修改任务信息
     *
     * @param task 任务信息
     * @return 是否修改成功
     */
    @Override
    public int updateTask(Task task) {
        return taskRepository.updateTask(task);
    }

    /**
     * 根据 id 删除任务
     *
     * @param id 任务 id
     * @return 是否删除成功
     */
    @Override
    public int deleteTaskById(Integer id) {
        return taskRepository.deleteTaskById(id);
    }

    /**
     * 根据 id 列表批量删除任务
     *
     * @param ids 任务 id 数组
     * @return 是否删除成功
     */
    @Override
    public int deleteTaskByIds(Integer[] ids) {
        return taskRepository.deleteTaskByIds(ids);
    }
}
