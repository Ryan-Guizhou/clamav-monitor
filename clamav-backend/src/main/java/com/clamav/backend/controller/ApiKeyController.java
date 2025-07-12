package com.clamav.backend.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.clamav.backend.base.BaseController;
import com.clamav.backend.base.Response;
import com.clamav.backend.entity.ApiKey;
import com.clamav.backend.entity.User;
import com.clamav.backend.service.ApiKeyService;
import com.clamav.backend.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Indexed;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

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
    public Response createApiKey(String keyName) {
        User currentUser = getCurrentUser();
        String keyString = apiKeyService.generateApiKey(currentUser.getId());
        LambdaQueryWrapper<ApiKey> lambdaQueryWrapper = new LambdaQueryWrapper<ApiKey>().eq(ApiKey::getApiKey, keyString);
        ApiKey apiKey = apiKeyService.getOne(lambdaQueryWrapper);
        return Response.success().setData(apiKey);
    }

    @GetMapping()
    @ApiOperation("获取api-key列表")
    public Response getApiKeys() {
        User currentUser = getCurrentUser();
        LambdaQueryWrapper<ApiKey> lambdaQueryWrapper = new LambdaQueryWrapper<ApiKey>().eq(ApiKey::getUserId, currentUser.getId());
        List<ApiKey> apiKeys = apiKeyService.list(lambdaQueryWrapper);
        return Response.success().setData(apiKeys);
    }

    @PutMapping("/{id}")
    @ApiOperation("更新api-key")
    public Response updateApiKey(@PathVariable Long id, @RequestBody ApiKey apiKeyDetails) {
        User currentUser = getCurrentUser();
        LambdaQueryWrapper<ApiKey> lambdaQueryWrapper = new LambdaQueryWrapper<ApiKey>().eq(ApiKey::getId, id).eq(ApiKey::getUserId, currentUser.getId());
        ApiKey apiKey = apiKeyService.getOne(lambdaQueryWrapper);
        if (apiKey == null) {
            return Response.fail();
        }
        apiKey.setStatus(apiKeyDetails.getStatus());
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