package com.clamav.backend.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.clamav.backend.base.BaseController;
import com.clamav.backend.base.Response;
import com.clamav.backend.entity.ScanHistory;
import com.clamav.backend.entity.User;
import com.clamav.backend.service.ScanHistoryService;
import com.clamav.backend.service.UserService;
import com.clamav.backend.service.impl.ScanHistoryServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Indexed;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Slf4j
@Indexed
@RestController
@RequestMapping("/api/scan-history")
@Api("文件扫描历史")
public class ScanHistoryController extends BaseController {

    @Resource
    private ScanHistoryServiceImpl scanHistoryService;

    @GetMapping
    @ApiOperation("获取文件扫描历史记录")
    public Response getScanHistory(
            @RequestParam(required = false) String status,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Date startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Date endDate) {

        User currentUser = getCurrentUser();
        QueryWrapper<ScanHistory> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", currentUser.getId());

        if (StringUtils.hasText(status)) {
            queryWrapper.eq("status", status);
        }
        if (startDate != null) {
            queryWrapper.ge("scanned_at", startDate);
        }
        if (endDate != null) {
            queryWrapper.le("scanned_at", endDate);
        }

        queryWrapper.orderByDesc("scanned_at");

        List<ScanHistory> history = scanHistoryService.list(queryWrapper);
        return Response.success().setData(history);
    }
}