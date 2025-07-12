package com.clamav.backend.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.clamav.backend.base.BaseController;
import com.clamav.backend.base.Response;
import com.clamav.backend.entity.Project;
import com.clamav.backend.entity.User;
import com.clamav.backend.service.ProjectService;
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
@RequestMapping("/api/projects")
@Api("项目")
public class ProjectController extends BaseController {

    @Resource
    private  ProjectService projectService;

    @PostMapping
    @ApiOperation("创建项目")
    public Response createProject(@RequestBody Project project) {
        User currentUser = getCurrentUser();
        project.setUserId(currentUser.getId());
        project.setCreateTime(new Date());
        project.setUpdateTime(new Date());
        projectService.save(project);
        return Response.success().setData(project);
    }

    @GetMapping
    @ApiOperation("获取项目列表")
    public Response getProjects() {
        User currentUser = getCurrentUser();
        List<Project> projects = projectService.list(new QueryWrapper<Project>().eq("user_id", currentUser.getId()));
        return Response.success().setData(projects);
    }

    @GetMapping("/{id}")
    @ApiOperation("根据id获取项目信息")
    public Response getProjectById(@PathVariable Long id) {
        User currentUser = getCurrentUser();
        Project project = projectService.getOne(new QueryWrapper<Project>().eq("id", id).eq("user_id", currentUser.getId()));
        if (project == null) {
            return Response.fail();
        }
        return Response.success().setData(project);
    }

    @PutMapping("/{id}")
    @ApiOperation("修改项目信息")
    public Response updateProject(@PathVariable Long id, @RequestBody Project projectDetails) {
        User currentUser = getCurrentUser();
        Project project = projectService.getOne(new QueryWrapper<Project>().eq("id", id).eq("user_id", currentUser.getId()));
        if (project == null) {
            return Response.fail();
        }
        project.setName(projectDetails.getName());
        project.setDescription(projectDetails.getDescription());
        project.setUpdateTime(new Date());
        projectService.updateById(project);
        return Response.success().setData(project);
    }

    @DeleteMapping("/{id}")
    @ApiOperation("删除项目信息")
    public Response deleteProject(@PathVariable Long id) {
        User currentUser = getCurrentUser();
        Project project = projectService.getOne(new QueryWrapper<Project>().eq("id", id).eq("user_id", currentUser.getId()));
        if (project == null) {
            return Response.fail();
        }
        projectService.removeById(id);
        return Response.success();
    }
}