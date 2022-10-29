package cn.microboat.service.impl;

import cn.microboat.config.MinioConfig;
import cn.microboat.service.FileService;
import cn.microboat.utils.FileUploadUtils;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 * Minio 文件存储服务实现类
 *
 * @author zhouwei
 */
@Service(value = "MinioFileServiceImpl")
public class MinioFileServiceImpl implements FileService {

    /**
     * minio 配置
     */
    private final MinioConfig minioConfig;

    /**
     * minio 客户端
     */
    private final MinioClient minioClient;

    @Autowired
    MinioFileServiceImpl(MinioConfig minioConfig, MinioClient minioClient) {
        this.minioConfig = minioConfig;
        this.minioClient = minioClient;
    }

    /**
     * 文件上传
     *
     * @param file 文件
     * @return 访问地址
     */
    @Override
    public String uploadFile(MultipartFile file) {
        String filename = FileUploadUtils.extractFilename(file);
        try {
            PutObjectArgs putObjectArgs = PutObjectArgs.builder()
                    .bucket(minioConfig.getBucketName())
                    .object(filename)
                    .stream(file.getInputStream(), file.getSize(), -1)
                    .contentType(file.getContentType())
                    .build();
            minioClient.putObject(putObjectArgs);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return minioConfig.getUrl() + "/" + minioConfig.getBucketName() + "/" + filename;
    }
}
