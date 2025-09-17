package com.module.hrm.web.common.processor;

import com.module.hrm.web.common.enumeration.FileType;
import com.module.hrm.web.common.enumeration.PatternConstants;
import com.module.hrm.web.common.enumeration.TemplateConstants;
import com.module.hrm.web.common.model.FileDownloadDto;
import com.module.hrm.web.common.utils.MessageSourceResolver;
import com.module.hrm.web.module.report.extractor.ExcelExtractor;
import com.module.hrm.web.module.report.service.PdfGenerateService;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.function.Supplier;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.InputStreamResource;
import org.springframework.util.StringUtils;

@Slf4j
public class ReportExtractor<T extends ExcelExtractor<E>, E> {

    private static final String DOT_CHARACTER = ".";

    private final E data;

    private final String templateFileName;

    private final FileType fileType;
    private final String distributorCode;

    private final Supplier<T> extractorClass;

    private FileDownloadDto result = null;

    private final MessageSourceResolver messageSourceReader;

    private final PdfGenerateService pdfGenerateService;

    private Boolean isMerchandisingFlag = null;

    public ReportExtractor(
        Supplier<T> extractorClass,
        E data,
        String templateFileName,
        FileType fileType,
        String distributorCode,
        MessageSourceResolver messageSourceReader,
        PdfGenerateService pdfGenerateService
    ) {
        this.extractorClass = extractorClass;
        this.data = data;
        this.templateFileName = TemplateConstants.PDF_TEMPLATE_DIRECTORY + templateFileName;
        this.fileType = fileType;
        this.distributorCode = distributorCode;
        this.messageSourceReader = messageSourceReader;
        this.pdfGenerateService = pdfGenerateService;
    }

    public ReportExtractor(
        Supplier<T> extractorClass,
        E data,
        String templateFileName,
        FileType fileType,
        String distributorCode,
        MessageSourceResolver messageSourceReader,
        PdfGenerateService pdfGenerateService,
        Boolean isMerchandisingFlag
    ) {
        this(extractorClass, data, templateFileName, fileType, distributorCode, messageSourceReader, pdfGenerateService);
        this.isMerchandisingFlag = isMerchandisingFlag;
    }

    /**
     * Export file based on file type
     *
     * @return
     */
    public void export() {
        ByteArrayOutputStream out;

        switch (this.getFileType()) {
            case xlsx:
                out = exportExcel();
                break;
            case pdf:
                out = exportPdf();
                break;
            default:
                out = new ByteArrayOutputStream();
                break;
        }

        ByteArrayInputStream inputStream = new ByteArrayInputStream(out.toByteArray());

        this.result = new FileDownloadDto(new InputStreamResource(inputStream), this.getExportFileName(), out.size());
    }

    /**
     * Get media type for download
     *
     * @return
     */
    public String getMediaType() {
        if (FileType.pdf.equals(this.getFileType())) {
            return FileReader.MEDIA_TYPE_APPLICATION_PDF;
        }

        return FileReader.MEDIA_TYPE_APPLICATION_XLSX;
    }

    /**
     * Get result
     *
     * @return
     */
    public FileDownloadDto getResult() {
        if (result == null) {
            log.debug("Export Extractor did not execute or parsed error.");
        }

        return this.result;
    }

    /**
     * exportExcel
     *
     * @param method
     *
     * @return
     */
    private ByteArrayOutputStream exportExcel() {
        T instance = newInstance();
        instance.setMessageSourceReader(messageSourceReader);
        return instance.extract(data);
    }

    /**
     * exportPdf
     *
     * @return
     */
    private ByteArrayOutputStream exportPdf() {
        return pdfGenerateService.generatePdfFile(this.templateFileName, data);
    }

    /**
     * Get file name by file type
     *
     * @return
     */
    private String getExportFileName() {
        StringBuilder sb = new StringBuilder();

        String template = templateFileName;
        if (template.contains(PatternConstants.SLASH_CHARACTER)) {
            template = templateFileName.split(PatternConstants.SLASH_CHARACTER)[1];
        }
        String filename = TemplateConstants.TEMPLATE_FILENAME_MAP.get(template);
        if (StringUtils.hasText(this.distributorCode)) {
            if (isMerchandisingFlag != null) {
                if (Boolean.TRUE.equals(isMerchandisingFlag)) filename = String.format(
                    filename,
                    TemplateConstants.EXTENSION_DISPLAY_LOYALTY,
                    this.distributorCode
                );
                else filename = String.format(filename, TemplateConstants.EXTENSION_EARNING_LOYALTY, this.distributorCode);
            } else {
                filename = String.format(filename, this.distributorCode);
            }
        }

        sb.append(filename);
        sb.append(DOT_CHARACTER);
        sb.append(this.getFileType().toString());

        return sb.toString();
    }

    /**
     * Get file type
     *
     * @return
     */
    private FileType getFileType() {
        return this.fileType == null ? FileType.xlsx : this.fileType;
    }

    /**
     * create a instance
     *
     * @return
     */
    private T newInstance() {
        return extractorClass.get();
    }
}
