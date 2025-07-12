package com.clamav.backend.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.clamav.backend.entity.ApiKey;

public interface ApiKeyService extends IService<ApiKey> {

    String generateApiKey(Long userId);

    String generateApiKey(String keyName);

}