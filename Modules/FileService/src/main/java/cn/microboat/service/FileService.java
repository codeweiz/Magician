package cn.microboat.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * 文件上传接口
 *
 * @author zhouwei
 */
public interface FileService {

    /**
     * 文件上传
     *
     * @param file 文件
     * @return 访问地址
     */
    String uploadFile(MultipartFile file);
}
