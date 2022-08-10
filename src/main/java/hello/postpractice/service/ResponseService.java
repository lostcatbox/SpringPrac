package hello.postpractice.service;

import hello.postpractice.model.response.CommonResult;
import hello.postpractice.model.response.ListResult;
import hello.postpractice.model.response.SingleResult;
import org.springframework.stereotype.Service;

import java.util.List;

@Service // 해당 Class가 Service임을 명시합니다.
public class ResponseService {

    // enum으로 api 요청 결과에 대한 code, message를 정의합니다.
    public enum CommonResponse {
        SUCCESS("성공하였습니다.");

        String msg;

        CommonResponse(String msg) {
            this.msg = msg;
        }

        public String getMsg() {
            return msg;
        }
    }
    // 단일건 결과를 처리하는 메소드
    public <T> SingleResult<T> getSingleResult(T data) {
        SingleResult<T> result = new SingleResult<>();
        result.setData(data);
        setSuccessResult(result);
        return result;
    }
    // 다중건 결과를 처리하는 메소드
    public <T> ListResult<T> getListResult(List<T> list) {
        ListResult<T> result = new ListResult<>();
        result.setList(list);
        setSuccessResult(result);
        return result;
    }
    // 성공 결과만 처리하는 메소드
    public CommonResult getSuccessResult() {
        CommonResult result = new CommonResult();
        setSuccessResult(result);
        return result;
    }
    // 실패 결과만 처리하는 메소드
    public CommonResult getFailResult(String errormsg) {
        CommonResult result = new CommonResult();
        setFailResult(result, errormsg);
        return result;
    }
    // 결과 모델에 api 요청 성공 데이터를 세팅해주는 메소드
    private void setSuccessResult(CommonResult result) {
        result.setMsg(CommonResponse.SUCCESS.getMsg());
    }
    // API 요청 실패 시 응답 모델을 실패 데이터로 세팅
    private void setFailResult(CommonResult result,String errormsg) {
        result.setMsg(errormsg+"인해 실패하였습니다.");
    }
}