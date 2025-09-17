package com.module.hrm.service.impl;

import com.module.hrm.config.ApplicationProperties;
import com.module.hrm.service.AmazonLambdaService;
import com.module.hrm.web.common.enumeration.PatternConstants;
import com.module.hrm.web.common.model.AwsDecompressResponse;
import com.module.hrm.web.common.utils.ConvertUtil;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import software.amazon.awssdk.core.SdkBytes;
import software.amazon.awssdk.services.lambda.LambdaClient;
import software.amazon.awssdk.services.lambda.model.InvokeRequest;
import software.amazon.awssdk.services.lambda.model.InvokeResponse;

@Service
@Slf4j
@AllArgsConstructor
public class AmazonLambdaServiceImpl implements AmazonLambdaService {

    private static final String BLANK_JOIN = "";
    private static final String S3_PREFIX = "s3://";
    private static final String BODY_NODE = "body";

    private final LambdaClient lambdaClient;
    private final ApplicationProperties applicationProperties;

    /**
     * decompressImageFile
     *
     * @param compressedFilePath
     * @param distributorCode
     * @return
     */
    @Override
    public List<String> decompressImageFile(
        String compressedFilePath,
        String routeCode,
        String distributorCode,
        Long distributorId,
        String userCode
    ) {
        log.debug("decompressImageFile: {} - {}", compressedFilePath, routeCode);

        String functionName = applicationProperties.getAws().getDecompressionLambda();

        Map<String, Map<String, String>> elements = new HashMap<>();
        Map<String, String> bodyElements = new HashMap<>();
        bodyElements.put("route_code", routeCode);
        bodyElements.put(
            "s3_key",
            ConvertUtil.concat(
                BLANK_JOIN,
                S3_PREFIX,
                applicationProperties.getAws().getS3bucket(),
                PatternConstants.SLASH_CHARACTER,
                compressedFilePath
            )
        );
        elements.put("body", bodyElements);

        var response = invokeFunction(functionName, ConvertUtil.toJson(elements));
        var lambdaResponse = ConvertUtil.toObject(response, AwsDecompressResponse.class, BODY_NODE);

        List<String> extractedImages = new ArrayList<>();
        if (!ObjectUtils.isEmpty(lambdaResponse)) extractedImages = lambdaResponse.getDetail().getFiles();

        return extractedImages;
    }

    public String invokeFunction(String functionName, String payload) {
        InvokeRequest request = InvokeRequest.builder()
            .functionName(functionName)
            .payload(SdkBytes.fromString(payload, StandardCharsets.UTF_8))
            .build();

        InvokeResponse response = lambdaClient.invoke(request);

        if (HttpStatus.OK.value() != response.statusCode()) {
            log.error(String.format("StatusCode: %s - Reponse: %s", response.statusCode(), response));
            return null;
        }

        return response.payload().asUtf8String();
    }
}
