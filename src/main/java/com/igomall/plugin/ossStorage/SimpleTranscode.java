package com.igomall.plugin.ossStorage;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.mts.model.v20140618.*;
import org.apache.commons.lang3.StringUtils;

public final class SimpleTranscode {

    private SimpleTranscode(){};

    // private static String accessKeyId = "LTAI4Fifc11BgZLtkR2pdEja";
    // private static String accessKeySecret = "1A3k5lzkQTF764x26bIQuLPZF667M8";
    private static String mpsRegionId = "cn-hangzhou";
    // private static String pipelineId = "cde3ac9b1e7747c59a7538fa0fd664aa";
    //  private static String templateId = "e278d852a19e4014a1b773610cd385a5";
    private static String ossLocation = "oss-cn-hangzhou";
    // private static String ossBucket = "ishangedu";
    // private static String ossInputObject = "3.mp4";
    // private static String ossOutputObject = "33.mp4";
    public static void main(String[] args) {
        // transform(accessKeyId,accessKeySecret,pipelineId,templateId,ossBucket,ossInputObject,ossOutputObject);
    }

    public static void transform(String accessKeyId,String accessKeySecret,String pipelineId,String templateId,String ossBucket,String ossInputObject,String ossOutputObject) {
        if(StringUtils.isEmpty(accessKeyId)){

        }
        if(StringUtils.isEmpty(accessKeySecret)){

        }
        if(StringUtils.isEmpty(pipelineId)){

        }
        if(StringUtils.isEmpty(templateId)){

        }
        if(StringUtils.isEmpty(ossBucket)){

        }
        if(StringUtils.isEmpty(ossInputObject)){

        }
        if(StringUtils.isEmpty(ossOutputObject)){

        }






        // 创建DefaultAcsClient实例并初始化
        DefaultProfile profile = DefaultProfile.getProfile(
                mpsRegionId,      // 地域ID
                accessKeyId,      // RAM账号的AccessKey ID
                accessKeySecret); // RAM账号Access Key Secret
        IAcsClient client = new DefaultAcsClient(profile);
        // 创建API请求并设置参数
        SubmitJobsRequest request = new SubmitJobsRequest();
        // Input
        JSONObject input = new JSONObject();
        input.put("Location", ossLocation);
        input.put("Bucket", ossBucket);
        try {
            input.put("Object", URLEncoder.encode(ossInputObject, "utf-8"));
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("input URL encode failed");
        }
        request.setInput(input.toJSONString());
        // Output
        String outputOSSObject;
        try {
            outputOSSObject = URLEncoder.encode(ossOutputObject, "utf-8");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("output URL encode failed");
        }
        JSONObject output = new JSONObject();
        output.put("OutputObject", outputOSSObject);
        // Ouput->Container
        JSONObject container = new JSONObject();
        container.put("Format", "mp4");
        output.put("Container", container.toJSONString());
        // Ouput->Video
        JSONObject video = new JSONObject();
        video.put("Codec", "H.264");
        video.put("Bitrate", "1500");
        video.put("Width", "1280");
        video.put("Fps", "25");
        output.put("Video", video.toJSONString());
        // Ouput->Audio
        JSONObject audio = new JSONObject();
        audio.put("Codec", "AAC");
        audio.put("Bitrate", "128");
        audio.put("Channels", "2");
        audio.put("Samplerate", "44100");
        output.put("Audio", audio.toJSONString());
        // Ouput->TemplateId
        output.put("TemplateId", templateId);
        JSONArray outputs = new JSONArray();
        outputs.add(output);
        request.setOutputs(outputs.toJSONString());
        request.setOutputBucket(ossBucket);
        request.setOutputLocation(ossLocation);
        // PipelineId
        request.setPipelineId(pipelineId);
        // 发起请求并处理应答或异常
        SubmitJobsResponse response;
        try {
            response = client.getAcsResponse(request);
            System.out.println("RequestId is:"+response.getRequestId());
            if (response.getJobResultList().get(0).getSuccess()) {
                System.out.println("JobId is:" + response.getJobResultList().get(0).getJob().getJobId());
            } else {
                System.out.println("SubmitJobs Failed code:" + response.getJobResultList().get(0).getCode() +
                                   " message:" + response.getJobResultList().get(0).getMessage());
            }
        } catch (ServerException e) {
            e.printStackTrace();
        } catch (ClientException e) {
            e.printStackTrace();
        }
    }
}