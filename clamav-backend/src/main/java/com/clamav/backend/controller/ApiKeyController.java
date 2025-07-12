package com.clamav.backend.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.clamav.backend.base.BaseController;
import com.clamav.backend.base.Response;
import com.clamav.backend.entity.ApiKey;
import com.clamav.backend.entity.User;
import com.clamav.backend.service.ApiKeyService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Indexed;
import org.springframework.web.bind.annotation.*;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.stream.Collectors;

@Slf4j
@Indexed
@RestController
@RequestMapping("/api/api-keys")
@Api("api-key管理")
public class ApiKeyController extends BaseController {


    @Resource
    private ApiKeyService apiKeyService;

    @PostMapping()
    @ApiOperation("创建api-key")
    public Response createApiKey(@RequestBody Map<String, String> payload) {
        String keyName = payload.get("keyName");
        User currentUser = getCurrentUser();
        String keyString = apiKeyService.generateApiKey(currentUser.getId());
        ApiKey apiKey = new ApiKey();
        apiKey.setUserId(currentUser.getId());
        apiKey.setApiKey(keyString);
        apiKey.setKeyName(keyName);
        apiKey.setStatus("Active");
        apiKey.setCreateTime(new Date());
        apiKey.setUpdateTime(new Date());
        apiKeyService.save(apiKey);
        return Response.success();
    }

    @GetMapping()
    @ApiOperation("获取api-key列表")
    public Response getApiKeys(@RequestParam(defaultValue = "1") int page,
                              @RequestParam(defaultValue = "10") int size) {
        User currentUser = getCurrentUser();
        LambdaQueryWrapper<ApiKey> lambdaQueryWrapper = new LambdaQueryWrapper<ApiKey>().eq(ApiKey::getUserId, currentUser.getId());
        Page<ApiKey> pageParam = new Page<>(page, size);
        IPage<ApiKey> apiKeysPage = apiKeyService.page(pageParam, lambdaQueryWrapper);
        List<ApiKey> apiKeys = apiKeysPage.getRecords();
        
        // 转换字段名称以匹配前端期望
        List<Map<String, Object>> formattedKeys = apiKeys.stream()
            .map(apiKey -> {
                Map<String, Object> map = new HashMap<>();
                map.put("id", apiKey.getId());
                map.put("key", apiKey.getApiKey());
                map.put("status", apiKey.getStatus());
                map.put("createdAt", apiKey.getCreateTime());
                return map;
            })
            .collect(Collectors.toList());
            
        Map<String, Object> result = new HashMap<>();
        result.put("data", formattedKeys);
        result.put("total", apiKeysPage.getTotal());
        return Response.success().setData(result);
    }

    @PutMapping("/{id}")
    @ApiOperation("更新api-key")
    public Response updateApiKey(@PathVariable Long id, @RequestBody Map<String, String> payload) {
        String status = payload.get("status");
        User currentUser = getCurrentUser();
        LambdaQueryWrapper<ApiKey> lambdaQueryWrapper = new LambdaQueryWrapper<ApiKey>().eq(ApiKey::getId, id).eq(ApiKey::getUserId, currentUser.getId());
        ApiKey apiKey = apiKeyService.getOne(lambdaQueryWrapper);
        if (apiKey == null) {
            return Response.fail();
        }
        apiKey.setStatus(status);
        apiKey.setUpdateTime(new Date());
        apiKeyService.updateById(apiKey);
        return Response.success().setData(apiKey);
    }

    @DeleteMapping("/{id}")
    @ApiOperation("删除api-key")
    public Response deleteApiKey(@PathVariable Long id) {
        User currentUser = getCurrentUser();
        LambdaQueryWrapper<ApiKey> lambdaQueryWrapper = new LambdaQueryWrapper<ApiKey>().eq(ApiKey::getId, id).eq(ApiKey::getUserId, currentUser.getId());
        boolean removed = apiKeyService.remove(lambdaQueryWrapper);
        if (!removed) {
            return Response.fail();
        }
        return Response.success();
    }
}