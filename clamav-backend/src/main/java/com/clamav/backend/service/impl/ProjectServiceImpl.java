package com.clamav.backend.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.clamav.backend.entity.Project;
import com.clamav.backend.mapper.ProjectMapper;
import com.clamav.backend.service.ProjectService;
import org.springframework.stereotype.Service;

@Service
public class ProjectServiceImpl extends ServiceImpl<ProjectMapper, Project> implements ProjectService {
}