package com.module.hrm.web.module.report.service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

public interface PdfGenerateService {
    ByteArrayOutputStream generatePdfFile(String templateName, Object obj);

    // TODO: Remove later
    ByteArrayInputStream generatePdfFiles(String templateName, Object obj);
}
