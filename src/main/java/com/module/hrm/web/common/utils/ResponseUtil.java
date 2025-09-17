package com.module.hrm.web.common.utils;

import com.module.hrm.web.common.model.AbstractRecordDto;
import com.module.hrm.web.common.model.FileDownloadDto;
import com.module.hrm.web.common.model.ObjectVM;
import com.module.hrm.web.common.model.PagedMetadata;
import com.module.hrm.web.common.model.PagedRestResponse;
import com.module.hrm.web.common.model.SimpleRestResponse;
import com.module.hrm.web.common.processor.FileWriter;
import com.module.hrm.web.common.processor.ReportExtractor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

public class ResponseUtil {

    /**
     * create a code response
     */
    public static <T> ResponseEntity<SimpleRestResponse<ObjectVM>> createCodeResponse(String code) {
        return ResponseEntity.ok(new SimpleRestResponse<>(new ObjectVM(code)));
    }

    /**
     * create a simple response
     */
    public static <T> ResponseEntity<SimpleRestResponse<T>> createSimpleResponse(T payload) {
        return ResponseEntity.ok(new SimpleRestResponse<>(payload));
    }

    /**
     * create a simple response
     */
    public static <T> ResponseEntity<SimpleRestResponse<T>> createSimpleResponse(T payload, HttpStatus status) {
        return ResponseEntity.ok(new SimpleRestResponse<>(payload, status));
    }

    /**
     * create a paged response
     */
    public static <T> ResponseEntity<PagedRestResponse<T>> createPagedResponse(PagedMetadata<T> payload) {
        return ResponseEntity.ok(new PagedRestResponse<>(payload));
    }

    /**
     * create a resource response with media type
     */
    public static ResponseEntity<Resource> createResourceResponse(FileDownloadDto fileDto, String mediaType) {
        return ResponseEntity.ok()
            .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + fileDto.getFileName())
            .contentLength(fileDto.getContentLength())
            .contentType(MediaType.parseMediaType(mediaType))
            .body(fileDto.getFile());
    }

    /**
     * create a resource response with media type
     */
    public static ResponseEntity<Resource> createResourceResponse(Resource resource, String fileName, String mediaType) {
        return ResponseEntity.ok()
            .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + fileName)
            .contentType(MediaType.parseMediaType(mediaType))
            .body(resource);
    }

    /**
     * create a resource response
     */
    public static <T extends AbstractRecordDto> ResponseEntity<Resource> createResourceResponse(FileWriter<T> fileWriter) {
        FileDownloadDto response = fileWriter.getResult();

        return ResponseEntity.ok()
            .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + response.getFileName())
            .contentType(MediaType.parseMediaType(fileWriter.getMediaType()))
            .body(response.getFile());
    }

    /**
     * create a resource response
     */
    public static ResponseEntity<Resource> createResourceResponse(ReportExtractor<?, ?> reportExtractor) {
        FileDownloadDto response = reportExtractor.getResult();

        return ResponseEntity.ok()
            .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + response.getFileName())
            .contentType(MediaType.parseMediaType(reportExtractor.getMediaType()))
            .body(response.getFile());
    }

    private ResponseUtil() {}
}
