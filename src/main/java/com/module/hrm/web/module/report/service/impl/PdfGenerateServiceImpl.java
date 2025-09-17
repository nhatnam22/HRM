package com.module.hrm.web.module.report.service.impl;

import com.lowagie.text.DocumentException;
import com.lowagie.text.pdf.BaseFont;
import com.module.hrm.security.SecurityUtils;
import com.module.hrm.web.common.enumeration.TemplateConstants;
import com.module.hrm.web.common.utils.ConvertUtil;
import com.module.hrm.web.module.report.service.PdfGenerateService;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.xhtmlrenderer.pdf.ITextRenderer;

@Service
public class PdfGenerateServiceImpl implements PdfGenerateService {

    @Autowired
    private TemplateEngine templateEngine;

    private Logger logger = LoggerFactory.getLogger(PdfGenerateServiceImpl.class);

    /**
     * generatePdfFile
     *
     * @param templateName
     * @param object       data
     * @return
     */
    @Override
    public ByteArrayInputStream generatePdfFiles(String templateName, Object obj) {
        byte[] pdfBytes = null;
        Map<String, Object> mapData = new HashMap<>();
        try {
            Locale locale = Locale.forLanguageTag(SecurityUtils.extractUserDetails().getLangKey());
            if (obj instanceof List<?>) {
                mapData.put(TemplateConstants.PDF_KEY_MAP_DATAS, ConvertUtil.convertObjToMap((List<?>) obj));
            } else if (obj instanceof Map<?, ?>) {
                mapData.put(TemplateConstants.PDF_KEY_MAP_DATAS, ConvertUtil.convertObjToMap(new ArrayList<>(((Map<?, ?>) obj).values())));
            } else {
                mapData = ConvertUtil.convertObjToMap(obj);
            }

            Context context = new Context(locale, mapData);
            String htmlContent = templateEngine.process(templateName, context);
            pdfBytes = rendererPdf(htmlContent).toByteArray();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return new ByteArrayInputStream(pdfBytes);
    }

    /**
     * generatePdfFile
     *
     * @param templateName
     * @param object       data
     * @return
     */
    @Override
    public ByteArrayOutputStream generatePdfFile(String templateName, Object obj) {
        ByteArrayOutputStream outputStream = null;
        Map<String, Object> mapData = new HashMap<>();
        try {
            Locale locale = Locale.forLanguageTag(SecurityUtils.extractUserDetails().getLangKey());
            if (obj instanceof List<?>) {
                mapData.put(TemplateConstants.PDF_KEY_MAP_DATAS, ConvertUtil.convertObjToMap((List<?>) obj));
            } else if (obj instanceof Map<?, ?>) {
                mapData.put(TemplateConstants.PDF_KEY_MAP_DATAS, ConvertUtil.convertObjToMap(new ArrayList<>(((Map<?, ?>) obj).values())));
            } else {
                mapData = ConvertUtil.convertObjToMap(obj);
            }

            Context context = new Context(locale, mapData);
            String htmlContent = templateEngine.process(templateName, context);
            outputStream = rendererPdf(htmlContent);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return outputStream;
    }

    /**
     * rendererPdf
     *
     * @param htmlContent
     * @return
     * @throws DocumentException
     * @throws IOException
     */
    private ByteArrayOutputStream rendererPdf(String htmlContent) throws DocumentException, IOException {
        var outputStream = new ByteArrayOutputStream();
        ITextRenderer renderer = new ITextRenderer();
        renderer.setDocumentFromString(htmlContent);
        renderer.getFontResolver().addFont(TemplateConstants.FONT_DIRECTORY + TemplateConstants.FONT_TAHOMA, BaseFont.IDENTITY_H, true);
        renderer.layout();
        renderer.createPDF(outputStream, false);
        renderer.finishPDF();
        return outputStream;
    }
}
