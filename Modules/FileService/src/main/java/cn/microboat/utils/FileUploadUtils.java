package cn.microboat.utils;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import org.apache.commons.io.FilenameUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

/**
 * 文件上传工具类
 *
 * @author zhouwei
 */
public class FileUploadUtils {

    public static final long DEFAULT_MAX_SIZE = 50 * 1024 * 1024;

    public static final int DEFAULT_FILE_NAME_LENGTH = 100;

    /**
     * 编码文件名
     */
    public static String extractFilename(MultipartFile file) {
        return StrUtil.format("{}/{}_{}.{}", DateUtil.format(new Date(), "yyyy/MM/dd"),
                FilenameUtils.getBaseName(file.getOriginalFilename()), IdUtil.fastSimpleUUID(), FilenameUtils.getExtension(file.getOriginalFilename()));
    }
}
