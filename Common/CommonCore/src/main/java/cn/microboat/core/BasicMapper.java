package cn.microboat.core;

import java.util.List;

/**
 * 通用基础映射
 * E：实体，对应数据库表结构
 * M：模型
 *
 * @author zhouwei
 */
public interface BasicMapper<E, M> {

    /**
     * 实体转模型
     *
     * @param entity 实体
     * @return M 模型
     */
    M entityToModel(E entity);

    /**
     * 模型转实体
     *
     * @param model 模型
     * @return E 实体
     */
    E modelToEntity(M model);

    /**
     * 实体列表转模型列表
     *
     * @param entities 实体列表
     * @return List<M> 模型列表
     */
    List<M> entitiesToModels(List<E> entities);

    /**
     * 模型列表转实体列表
     *
     * @param models 模型列表
     * @return List<E> 实体列表
     */
    List<E> modelsToEntities(List<M> models);
}
