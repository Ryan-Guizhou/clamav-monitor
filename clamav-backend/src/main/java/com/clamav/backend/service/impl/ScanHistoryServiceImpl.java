package com.clamav.backend.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.clamav.backend.entity.ScanHistory;
import com.clamav.backend.mapper.ScanHistoryMapper;
import com.clamav.backend.service.ScanHistoryService;
import org.springframework.stereotype.Service;

@Service
public class ScanHistoryServiceImpl extends ServiceImpl<ScanHistoryMapper, ScanHistory> implements ScanHistoryService {
}