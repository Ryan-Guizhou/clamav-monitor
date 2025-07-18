package com.clamav.backend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.clamav.backend.entity.ApiKey;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ApiKeyMapper extends BaseMapper<ApiKey> {
}