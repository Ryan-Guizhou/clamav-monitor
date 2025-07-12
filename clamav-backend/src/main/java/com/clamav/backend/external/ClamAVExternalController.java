package com.clamav.backend.external;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.clamav.backend.base.Response;
import com.clamav.backend.entity.ApiKey;
import com.clamav.backend.proxy.ClamAVClient;
import com.clamav.backend.proxy.ClamAVClientFactory;
import com.clamav.backend.service.ApiKeyService;
import com.clamav.backend.utils.JwtTokenProvider;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Indexed;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;


/**
 * @Author Mr Shu
 * @Version 1.0.0
 * @Description //TODO
 * @CreateTime 2025/7/10 18:32
 */
@Slf4j
@Indexed
@RestController
@RequestMapping("/external/calmav/v1")
@Api("外部调用扫描控制器")
public class ClamAVExternalController {

    @Resource
    private ApiKeyService apiKeyService;

    @Resource
    private JwtTokenProvider jwtTokenProvider;

    @Resource
    private UserDetailsService userDetailsService;

    @Resource
    private ClamAVClientFactory clamAVClientFactory;


    @PostMapping("/createKey")
    @ApiOperation("创建api-key")
    public Response createKey(String keyName){
        if (keyName != null || keyName == "" || keyName == null){
            return Response.fail().setData("keyName is null or empty");
        }
        String apiKey = apiKeyService.generateApiKey(keyName);
        return Response.success().setData(apiKey);
    }

    @GetMapping("/isValid")
    @ApiOperation("判断key是否有效")
    public Response isValid(String apiKey){
        if (apiKey != null || apiKey == "" || apiKey == null){
            return Response.fail().setData("keyName is null or empty");
        }
        ApiKey key = apiKeyService.getOne(new QueryWrapper<ApiKey>().eq("apiKey", apiKey));
        boolean isValid = key != null && key.getStatus().equals("Active");
        return Response.success().setData(isValid);
    }


    @PostMapping(value = "/scanStream", consumes = "application/octet-stream")
    @ApiOperation("以文件流的方式扫描是否携带病毒")
    public Response scanFile(HttpServletRequest request,
                             @RequestHeader(value = "X-FILE-NAME") String fileName,
                             @RequestHeader(value = "X-API-KEY",required = false) String apiKey,
                             @RequestHeader(value = "X-TOKEN",required = false) String authoration
                             ) throws IOException {
        if (apiKey == null && authoration == null){
            return Response.fail().setData("apiKey is null or empty");
        }
        if (apiKey != null){
            ApiKey key = apiKeyService.getOne(new LambdaQueryWrapper<ApiKey>().eq(ApiKey::getApiKey, apiKey));
            if (key == null || !key.getStatus().equals("Active")){
                return Response.fail().setData("apiKey is not exist or active");
            }
        }

        if (authoration != null){
            String username = jwtTokenProvider.getUsernameFromToken(authoration);
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);

            Boolean isValid = jwtTokenProvider.validateToken(authoration, userDetails);
            if (!isValid){
                return Response.fail().setData("token is not valid");
            }
        }

        // 扫描文件
        ClamAVClient clamAVClient = clamAVClientFactory.newClient();
        InputStream inputStream = request.getInputStream();
        byte[] result = clamAVClient.scan(inputStream);
        boolean cleanReply = ClamAVClient.isCleanReply(result);

        if(cleanReply) {
            String msg = String.format("fileName:[%s] Check and Passed",fileName);
            log.info(msg);
            return Response.success().setData(true).setMsg(msg);
        }
        String msg = String.format("fileName:[%s] Check and Refused",fileName);
        log.info(msg);
        return Response.fail().setData(false).setMsg(msg);
    }
}
