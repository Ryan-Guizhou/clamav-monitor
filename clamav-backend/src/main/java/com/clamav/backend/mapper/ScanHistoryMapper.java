package com.clamav.backend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.clamav.backend.entity.ScanHistory;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ScanHistoryMapper extends BaseMapper<ScanHistory> {
}