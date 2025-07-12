package com.clamav.backend.controller;

import com.clamav.backend.proxy.ClamAVClient;
import com.clamav.backend.proxy.ClamAVClientFactory;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Indexed;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author Mr Shu
 * @Version 1.0.0
 * @Description //TODO
 * @CreateTime 2025/7/10 16:35
 */
@Slf4j
@Indexed
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/clam")
@Api("clamav内部测试")
public class ClamAVInternalController {

    @Resource
    private ClamAVClientFactory clamAVClientFactory;

    @GetMapping("/ping")
    @ApiOperation("服务响应测试")
    public Map<String,Object> ping() throws IOException {
        ClamAVClient clamAVClient = clamAVClientFactory.newClient();
        Map<String,Object> resultMap = new HashMap<>();
        resultMap.put("code",200);
        resultMap.put("msg","operation successful");
        resultMap.put("data","Clamd responding: " + clamAVClient.ping());
        return resultMap;
    }

    @PostMapping(value="/scan")
    @ApiOperation("检查返回自定义的信息 如果data为true表示检查通过 false表示未通过")
    public Map<String, Object> handleFileUpload(@RequestParam("file") MultipartFile file) throws IOException{
        if (file.isEmpty()) {
            throw new IllegalArgumentException("empty file");
        }
        ClamAVClient clamAVClient = clamAVClientFactory.newClient();
        byte[] r = clamAVClient.scan(file.getInputStream());
        boolean cleanReply = ClamAVClient.isCleanReply(r);
        Map<String,Object> resultMap = new HashMap<>();
        resultMap.put("code",200);
        resultMap.put("data",cleanReply);
        String originalFilename = file.getOriginalFilename();
        if(cleanReply) {
            String msg = String.format("fileName:[%s] Check and Passed",originalFilename);
            log.info(msg);
            resultMap.put("msg",msg);
            return resultMap;
        }
        String msg = String.format("fileName:[%s] Check and Refused",originalFilename);
        log.info(msg);
        resultMap.put("msg",msg);
        return resultMap;
    }


    @PostMapping(value="/scanReply")
    @ApiOperation("检查文件并返回原始检查信息")
    public Map<String,Object> handleFileUploadReply(@RequestParam("file") MultipartFile file) throws IOException{
        if (file.isEmpty()) {
            throw new IllegalArgumentException("empty file");
        }
        ClamAVClient clamAVClient = clamAVClientFactory.newClient();
        Map<String,Object> resultMap = new HashMap<>();
        resultMap.put("code",200);
        String originalFilename = file.getOriginalFilename();
        String outPutMsg = new String(clamAVClient.scan(file.getInputStream()));
        String msg = String.format("Check file:[%s] OutPutMsg is:[%s]",originalFilename,outPutMsg);
        log.info(msg);
        resultMap.put("msg",msg);
        return resultMap;
    }


    @PostMapping(value = "/scanStream", consumes = "application/octet-stream")
    @ApiOperation("以文件流的方式扫描是否携带病毒")
    public Map<String,Object> scanFile(HttpServletRequest request) throws IOException {
        ClamAVClient clamAVClient = clamAVClientFactory.newClient();
        InputStream inputStream = request.getInputStream();
        byte[] result = clamAVClient.scan(inputStream);
        boolean cleanReply = ClamAVClient.isCleanReply(result);
        Map<String,Object> resultMap = new HashMap<>();
        resultMap.put("code",200);
        resultMap.put("data",cleanReply);
        String fileName = request.getHeader("X-Filename");
        if(cleanReply) {
            String msg = String.format("fileName:[%s] Check and Passed",fileName);
            log.info(msg);
            resultMap.put("msg",msg);
            return resultMap;
        }
        String msg = String.format("fileName:[%s] Check and Refused",fileName);
        log.info(msg);
        resultMap.put("msg",msg);
        return resultMap;
    }
}
