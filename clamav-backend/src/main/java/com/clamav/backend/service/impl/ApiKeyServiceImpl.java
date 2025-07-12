package com.clamav.backend.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.clamav.backend.entity.ApiKey;
import com.clamav.backend.mapper.ApiKeyMapper;
import com.clamav.backend.service.ApiKeyService;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;

@Service
public class ApiKeyServiceImpl extends ServiceImpl<ApiKeyMapper, ApiKey> implements ApiKeyService {

    private static final String API_KEY_PREFIX = "cg_sk_";

    @Override
    public String generateApiKey(Long userId) {
        String key = API_KEY_PREFIX + UUID.randomUUID().toString().replace("-", "");
        ApiKey apiKey = new ApiKey();
        apiKey.setApiKey(key);
        apiKey.setUserId(userId);
        apiKey.setStatus("Active");
        apiKey.setCreateTime(new Date());
        apiKey.setUpdateTime(new Date());
        this.save(apiKey);
        return key;
    }

    @Override
    public String generateApiKey(String keyName) {
        String key = API_KEY_PREFIX + UUID.randomUUID().toString().replace("-", "");
        ApiKey apiKey = new ApiKey();
        apiKey.setApiKey(key);
        apiKey.setKeyName(keyName);
        apiKey.setStatus("Active");
        apiKey.setCreateTime(new Date());
        apiKey.setUpdateTime(new Date());
        this.save(apiKey);
        return key;
    }
}