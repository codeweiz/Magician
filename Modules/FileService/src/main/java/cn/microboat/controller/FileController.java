package cn.microboat.controller;

import cn.microboat.core.Return;
import cn.microboat.service.FileService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

/**
 * 文件请求处理
 *
 * @author zhouwei
 */
@Api(value = "FileController", tags = "FileController")
@RestController
@RequestMapping("/file")
@Slf4j
public class FileController {

    private final FileService fileService;

    @Autowired
    FileController(FileService fileService) {
        this.fileService = fileService;
    }

    @ApiOperation(value = "上传多个文件", httpMethod = "POST")
    @PostMapping(value = "/upload")
    public Return<?> upload(@RequestParam(name = "files") List<MultipartFile> files) {
        List<String> urls = new ArrayList<>();
        files.forEach(file -> urls.add(fileService.uploadFile(file)));
        return Return.succeed(urls);
    }
}
