package com.clamav.backend.controller;

import com.clamav.backend.base.Response;
import com.clamav.backend.proxy.ClamAVClient;
import com.clamav.backend.proxy.ClamAVClientFactory;
import com.clamav.backend.vo.EngineStatus;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Indexed;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
@Indexed
@RestController
@RequestMapping("/api/engine-status")
@Api("clamav服务器状态管理")
public class EngineStatusController {

    @Resource
    private ClamAVClientFactory clamAVClientFactory;

    @GetMapping
    @ApiOperation("获取clamav状态信息")
    public Response getEngineStatus() throws IOException, ParseException {
        ClamAVClient clamAVClient = clamAVClientFactory.newClient();
        String versionInfo = clamAVClient.getVersion();
        String stats  = clamAVClient.getStats();

        EngineStatus status = new EngineStatus();
        // 解析版本信息
        String[] versionParts = versionInfo.split("/");
        if (versionParts.length >= 3) {
            status.setClamavVersion(versionParts[0].replace("ClamAV", "").trim());
            status.setVirusDbVersion(versionParts[1].trim());
            // 原格式
            SimpleDateFormat originalFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss yyyy", java.util.Locale.ENGLISH);
            // 目标格式
            SimpleDateFormat targetFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

            Date date = originalFormat.parse(versionParts[2].trim());
            String formatted = targetFormat.format(date);
            status.setVirusDbUpdateTime(formatted);
        }

        // 状态判断
        if (stats.contains("STATE: READY") || stats.contains("STATE: VALID PRIMARY")) {
            status.setProcessStatus("Running");
        } else {
            status.setProcessStatus("Unknown");
        }

        // 内存使用（提取 pools_used）
        Matcher memMatcher = Pattern.compile("pools_used\\s+(\\d+(\\.\\d+)?M)").matcher(stats);
        if (memMatcher.find()) {
            status.setMemoryUsage(memMatcher.group(1).replace("M", "MB"));
        }

        // 只能通过docker 内查看数据库 sigtool --info /var/lib/clamav/main.cvd
        status.setVirusSignatures("6647427");

        return Response.success().setData(status);
    }
}