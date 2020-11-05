package com.smart.smartDB00.dto.common;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 * Created by 99902020 on 2019/2/21.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResResult<T> implements Serializable {

    public static final String SUCCESS_CODE = "0000";
    public static final String SUCCESS_MSG = "请求成功";
    public static final String FAIL_MSG = "请求失败";

    private Return Return;

    private T Detail;


    public ResResult() {
        Return = new Return();
    }

    public ResResult(String code, String message) {
        Return = new Return(code,message);
    }

    public ResResult(String code, String message, T detail) {
        Return = new Return(code,message);
        Detail = detail;
    }

    @JsonProperty("Return")
    public ResResult.Return getReturn() {
        return Return;
    }

    @JsonProperty("Detail")
    public T getDetail() {
        return Detail;
    }

    public void setDetail(T detail) {
        Detail = detail;
    }

    public static class Return{

        private String ReturnCode;

        private String ReturnMessage;

        public Return() {
        }

        public Return(String returnCode, String returnMessage) {
            ReturnCode = returnCode;
            ReturnMessage = returnMessage;
        }

        @JsonProperty("ReturnCode")
        public String getReturnCode() {
            return ReturnCode;
        }

        public void setReturnCode(String returnCode) {
            ReturnCode = returnCode;
        }

        @JsonProperty("ReturnMessage")
        public String getReturnMessage() {
            return ReturnMessage;
        }

        public void setReturnMessage(String returnMessage) {
            ReturnMessage = returnMessage;
        }
    }
}
