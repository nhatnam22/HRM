package com.module.hrm.service;

import java.util.List;

public interface AmazonLambdaService {
    List<String> decompressImageFile(
        String compressedFilePath,
        String routeCode,
        String distributorCode,
        Long distributorId,
        String userCode
    );
}
