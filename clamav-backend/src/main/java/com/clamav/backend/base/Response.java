package com.clamav.backend.base;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;

/**
 * @Author Mr Shu
 * @Version 1.0.0
 * @Description //TODO
 * @CreateTime 2025/2/26 14:39
 */
@Slf4j
public class Response implements Serializable {

    private static final long serialVersionUID = -5025234238340185524L;

    /**
     * 状态码
     */
    private String  code;

    /**
     * 响应信息
     */
    private String msg;

    /**
     * 数据
     */
    private Object data;

    private static final String SUCCESS_CODE = "200";

    public final static String LICENSE_VALIDATE_FAIL_CODE = "201";

    public final static String BATCH_PARTIAL_SUCCESS_CODE = "206";
    /**
     * 多条数据批处理，业务处理部分数据处理成功，部分业务处理失败；
     */

    public final static String BATCH_WHOLE_FAIL_CODE = "207";
    /**
     * 连接成功，多条数据批处理，业务处理全部失败；
     */

    public final static String BUSINESS_FAIL_CODE = "400";

    public final static String FAIL_CODE = "500";

    public final static String LICENSE_FAIL_CODE = "501";
    /**
     * 业务标记码-需要前台再次确认情形
     */
    public static final String STATUS_BIZ_NEED_CONFIRM = "409";

    /**
     * 业务标记码-需要前台再次确认是否
     */
    public static final String STATUS_BIZ_NEED_LOGIC_CHOICE = "411";


    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    public boolean isSuccess() {
        return this.code.startsWith("2");
    }

    public Response(){
        super();
    }

    public String getCode() {
        return code;
    }

    public Response setCode(String code) {
        this.code = code;
        return this;
    }

    public String getMsg() {
        return msg;
    }

    public Response setMsg(String msg) {
        this.msg = msg;
        return this;
    }

    public Object getData() {
//		if (encrypted) {
//			if (data != null) {
//				try {
//					String json = _objectMapper.writeValueAsString(data);
//					String dymaKey = (String) PtyContext.getValue(PtyContext.CURRENT_USER_CODE)
//							+ (String) PtyContext.getValue(PtyContext.CURRENT_TRANS_DATA);
//					String hexStr = Sm4.encrypt(json, _md5.digestHex(dymaKey));
//					return Base64.getEncoder().encodeToString(HexUtil.decodeHex(hexStr));
//				} catch (Exception ex) {
//					log.error("encryped error", ex);
//					return data;
//				}
//			}
//		}
        return data;
    }

    public Response setData(Object data) {
        this.data = data;
        return this;
    }

    public static Response success() {
        return new SuccessResponse();
    }

    public static Response fail() {
        return new FailResponse();
    }

    public static Response businessFailResponse() {
        return new BusinessFailResponse();
    }

    public static Response businessFailResponse(String msg) {
        return new BusinessFailResponse(msg);
    }

    public static Response businessFailResponse(String errCode, String errMsg) {
        return new BusinessFailResponse(errCode, errMsg);
    }


    public static Response commonResponse(String code, String msg) {
        return new CommonResponse(code, msg);
    }

    public static Response commonResponse(String msg) {
        return new CommonResponse(msg);
    }

    /**
     * fail
     */
    public static class FailResponse extends Response {
        private static final long serialVersionUID = -2485130096025044970L;

        public FailResponse() {
            super();
            this.setCode(FAIL_CODE);
            this.setMsg("fail");
        }
    }

    /**
     * success
     *
     * @author liubo
     */
    public static class SuccessResponse extends Response {
        private static final long serialVersionUID = -2395996558104816956L;

        public SuccessResponse() {
            super();
            this.setCode(SUCCESS_CODE);
            this.setMsg("成功");
        }
    }

    public static class BusinessFailResponse extends Response {
        private static final long serialVersionUID = -5238070512611403402L;

        public BusinessFailResponse() {
            super();
            this.setCode(BUSINESS_FAIL_CODE);
            this.setMsg("业务处理失败");
        }

        public BusinessFailResponse(String code, String msg) {
            super();
            this.setCode(code);
            this.setMsg(msg);
        }

        public BusinessFailResponse(String errMsg) {
            super();
            this.setCode(BUSINESS_FAIL_CODE);
            this.setMsg(errMsg);
        }
    }

    public static class CommonResponse extends Response {
        private static final long serialVersionUID = -687461638087689607L;

        public CommonResponse(String code, String msg) {
            super();
            this.setCode(code);
            this.setMsg(msg);
            this.setData("");
        }

        public CommonResponse(String msg) {
            super();
            this.setCode(SUCCESS_CODE);
            this.setMsg(msg);
            this.setData("");
        }

    }

}
