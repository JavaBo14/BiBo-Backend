package com.bo.springbootinit.manager;

import com.bo.springbootinit.common.ErrorCode;
import com.bo.springbootinit.exception.BusinessException;

import com.yupi.yucongming.dev.client.YuCongMingClient;
import com.yupi.yucongming.dev.common.BaseResponse;
import com.yupi.yucongming.dev.model.DevChatRequest;
import com.yupi.yucongming.dev.model.DevChatResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 对接Ai
 */
@Slf4j
@Service
public class AiManager {

    @Resource
    private YuCongMingClient yuCongMingClient;

    public String doChat(long modelId,String message){
        DevChatRequest devChatRequest = new DevChatRequest();
        devChatRequest.setModelId(modelId);
        devChatRequest.setMessage(message);
        BaseResponse<DevChatResponse> response = yuCongMingClient.doChat(devChatRequest);
        if (response == null){
            throw new BusinessException(ErrorCode.SYSTEM_ERROR,"Ai响应错误");
        }
        return response.getData().getContent();
    }
//    @Resource
//    private SparkClient sparkClient;

    /**
     * 向讯飞AI发送请求
     */
//    public String sendMesToAIUseXingHuo(String content) {
//        // 消息列表，可以在此列表添加历史对话记录
//        List<SparkMessage> messages = new ArrayList<>();
//        messages.add(SparkMessage.systemContent("你是一个数据分析师和前端开发专家，接下来我会按照以下固定格式给你提供内容。"));
//        messages.add(SparkMessage.userContent( "分析需求：\n" +
//                "{数据分析的需求或者目标}\n" +
//                "原始数据：\n" +
//                "{csv格式的原始数据，用,作为分隔符}\n" +
//                "请根据这两部分内容，按照以下指定格式生成内容（此外不要输出任何多余的开头、结尾、注释）\n" +
//                "【【【【【\n" +
//                "{前端 Echarts V5 的 option 配置对象js代码（符合JSON格式），合理地将数据进行可视化，不要生成任何多余的内容，比如代码注释}\n" +
//                "【【【【【\n" +
//                "{明确的数据分析结论、越详细越好，不要生成多余的注释}\n"));
//        // 构造请求
//        SparkRequest sparkRequest = SparkRequest.builder()
//                // 消息列表
//                .messages(messages)
//                // 模型回答的tokens的最大长度,非必传，默认为2048。
//                //V1.5取值为[1,4096]
//                //V2.0取值为[1,8192]
//                // V3.0取值为[1,8192]
//                .maxTokens(2048)
//// 核采样阈值。用于决定结果随机性,取值越高随机性越强即相同的问题得到的不同答案的可能性越高 非必传,取值为[0,1],默认为0.5
//                .temperature(0.2)
//// 指定请求版本，默认使用最新3.0版本
//                .apiVersion(SparkApiVersion.V3_0)
//                .build();
//        // 同步调用
//        SparkSyncChatResponse chatResponse = sparkClient.chatSync(sparkRequest);
//        String responseContent=chatResponse.getContent();
//        log.info("星火AI返回的结果{}",responseContent);
//        return responseContent;
//    }

}
