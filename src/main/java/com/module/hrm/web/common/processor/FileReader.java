package com.module.hrm.web.common.processor;

import com.module.hrm.config.Constants;
import com.module.hrm.service.AmazoneS3Service;
import com.module.hrm.web.common.enumeration.FileType;
import com.module.hrm.web.common.enumeration.MessageKeys;
import com.module.hrm.web.common.enumeration.PatternConstants;
import com.module.hrm.web.common.exception.ValidationGenericException;
import com.module.hrm.web.common.model.AbstractRecordDto;
import com.module.hrm.web.common.model.FieldErrorVM;
import com.module.hrm.web.common.model.FileUploadDto;
import com.module.hrm.web.common.utils.ConvertUtil;
import com.module.hrm.web.common.utils.MessageSourceResolver;
import com.module.hrm.web.common.utils.ValidationUtil;
import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.core.io.Resource;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
public class FileReader<T extends AbstractRecordDto> {

    public static final char CSV_SEPARATOR_COMMA = ',';
    public static final String ARRAY_SEPARATOR_SEMICOLON = ";";
    public static final Charset CSV_CHARSET_UTF8 = StandardCharsets.UTF_8;
    public static final char CSV_NEW_LINE_SEPARATOR = '\n';

    public static final String MEDIA_TYPE_APPLICATION_CSV = "application/csv";
    public static final String MEDIA_TYPE_APPLICATION_XLSX = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
    public static final String MEDIA_TYPE_APPLICATION_PDF = "application/pdf";
    private static final String MEDIA_TYPE_TEXT_CSV = "text/csv";

    private static final int START_ROW = 1;

    private final MessageSourceResolver messageSourceReader;

    private final MultipartFile file;

    private final Supplier<T> supplier;

    @Getter
    private final List<T> data = new ArrayList<T>();

    @Getter
    private FileUploadDto<T> result = null;

    private FileType fileType;

    private File fileTemporaryDir;

    private final AmazoneS3Service amazonS3Service;

    public FileReader(
        MultipartFile file,
        MessageSourceResolver messageSourceReader,
        Supplier<T> supplier,
        AmazoneS3Service amazonS3Service
    ) {
        this.file = file;
        this.messageSourceReader = messageSourceReader;
        this.supplier = supplier;
        this.amazonS3Service = amazonS3Service;
    }

    public boolean hasError() {
        if (result == null) {
            log.debug("CSV Parser did not execute or parsed error.");
            return true;
        }

        return result.getFailureTotal() > 0;
    }

    public void setResult(Collection<String> errorMessages) {
        if (result != null) {
            result.setSuccessTotal(result.getTotal());

            int totalSuccess = result.getSuccessTotal() - errorMessages.size();
            int totalFailure = result.getFailureTotal() + errorMessages.size();

            result.setSuccessTotal(totalSuccess);
            result.setFailureTotal(totalFailure);
        } else {
            log.debug("CSV Parser did not executed or parsed error.");
        }
    }

    public void setResult(List<T> errorRecords) {
        if (result != null) {
            result.setSuccessTotal(result.getTotal());

            int totalSuccess = result.getSuccessTotal() - errorRecords.size();
            int totalFailure = result.getFailureTotal() + errorRecords.size();

            result.setSuccessTotal(totalSuccess);
            result.setFailureTotal(totalFailure);
            result.setErrorRecords(errorRecords);
        } else {
            log.debug("CSV Parser did not executed or parsed error.");
        }
    }

    public void parse() {
        writeFile();
        parseFileType();

        if (FileType.csv.equals(this.fileType)) {
            parseCSV();
        } else {
            parseExcel();
        }

        createCSVUploadVM();
    }

    private void writeFile() {
        try {
            String pathTemporaryDir = getTemporaryDir();

            fileTemporaryDir = Path.of(pathTemporaryDir, Objects.requireNonNull(this.file.getOriginalFilename())).toFile();
            FileUtils.copyInputStreamToFile(this.file.getInputStream(), fileTemporaryDir);

            log.debug("Save file to temporary dir: {}", fileTemporaryDir.getAbsolutePath());
        } catch (Exception e) {
            e.printStackTrace();
            log.debug("Write file to temporary dir exception : {}", e.getClass().getSimpleName());
            throw new ValidationGenericException("System error", MessageKeys.FILE_PARSED_EXCEPTION);
        }
    }

    /**
     * Calculate response upload file
     */
    private void createCSVUploadVM() {
        // Get failure records

        List<T> errorRecords = this.data.stream().filter(t -> StringUtils.hasText(t.getErrorMessage())).collect(Collectors.toList());

        result = new FileUploadDto<T>(this.data.size(), 0, errorRecords.size(), errorRecords);
    }

    private static final String FIELD_HEADER_RESPONSE = "Nội Dung Lỗi";
    private static final int WIDTH_COLUMN_RESPONSE = 10000;
    private static final int SHEET_RESPONSE = 0;

    public Resource exportFileResult() {
        if (FileType.csv.equals(this.fileType)) {
            return null;
        } else {
            return null;
        }
    }

    public FileUploadDto<T> exportResult() {
        String path = null;
        if (hasError()) {
            if (FileType.csv.equals(this.fileType)) {
                path = exportCsv();
            } else {
                path = exportExcel();
            }
        }
        result.setUploadedPath(path);
        return result;
    }

    private String exportCsv() {
        BufferedReader br = null;
        try {
            Path path = fileTemporaryDir.toPath();
            br = Files.newBufferedReader(path, Charset.defaultCharset());

            StringBuilder content = new StringBuilder();
            boolean successWrite = false;
            String successMessages = messageSourceReader.resolveLocalizedMessage(
                MessageKeys.UPLOAD_FILE_SUCCESSFUL,
                new Object[] { String.valueOf(result.getSuccessTotal()) }
            );
            Map<Integer, T> errorRecords = result.getMapErrorRecords();

            // header
            content.append(br.readLine()).append(CSV_SEPARATOR_COMMA).append(FIELD_HEADER_RESPONSE).append(CSV_NEW_LINE_SEPARATOR);

            String line = null;
            int index = 2;
            while ((line = br.readLine()) != null) {
                content.append(line).append(CSV_SEPARATOR_COMMA);

                if (!hasError() && !successWrite) {
                    content.append(successMessages);
                    successWrite = true;
                } else {
                    T errorRecord = errorRecords.get(index);
                    if (null != errorRecord) {
                        content.append(errorRecord.getErrorMessage());
                    }
                }

                content.append(CSV_NEW_LINE_SEPARATOR);
                index++;
            }

            InputStream is = new ByteArrayInputStream(CSV_CHARSET_UTF8.encode(String.valueOf(content)).array());
            String returnPath = uploadS3(is);

            log.debug("Upload file {} to amazon S3 : {}", file.getOriginalFilename(), Constants.TEMP_UPLOAD_DIR);
            return returnPath;
        } catch (Exception e) {
            e.printStackTrace();
            log.debug("export file csv exception : {}", e.getClass().getSimpleName());
            throw new ValidationGenericException("System error", MessageKeys.FILE_PARSED_EXCEPTION);
        } finally {
            try {
                if (null != br) {
                    br.close();
                }
            } catch (Throwable throwable) {
                throwable.printStackTrace();
                log.debug("Export : {}", throwable.getClass().getSimpleName());
            }
        }
    }

    private String uploadS3(InputStream is) throws IOException {
        String uploadedPath = amazonS3Service.uploadFileToS3bucket(
            is,
            file.getOriginalFilename(),
            MEDIA_TYPE_APPLICATION_CSV,
            Constants.TEMP_UPLOAD_DIR
        );
        return amazonS3Service.generateSignedUrl(uploadedPath);
    }

    private String exportExcel() {
        XSSFWorkbook workbook = null;
        ByteArrayOutputStream out = null;

        try {
            workbook = new XSSFWorkbook(Files.newInputStream(fileTemporaryDir.toPath()));
            Sheet sheet = workbook.getSheetAt(SHEET_RESPONSE);

            // header
            Row headerRow = sheet.getRow(0);
            int indexLastCellHeader = headerRow.getLastCellNum();
            Cell headerCell = headerRow.createCell(indexLastCellHeader);
            createResponseHeader(headerCell, workbook);

            Map<Integer, T> errorRecords = result.getMapErrorRecords();
            int index = 2;
            for (Row row : sheet) {
                // ignore header
                if (row.getRowNum() == 0) {
                    continue;
                }

                int indexLastCellNum = row.getLastCellNum();

                // last cell
                Cell lastCell = row.getCell(indexLastCellNum - 1);
                Cell cell = row.createCell(indexLastCellNum);
                cell.setCellStyle(lastCell.getCellStyle());

                // write messages
                if (!hasError()) {
                    cell.setCellValue(
                        messageSourceReader.resolveLocalizedMessage(
                            MessageKeys.UPLOAD_FILE_SUCCESSFUL,
                            String.valueOf(result.getSuccessTotal())
                        )
                    );
                    break;
                } else {
                    T errorRecord = errorRecords.get(index);
                    if (null != errorRecord) {
                        cell = row.createCell(headerRow.getLastCellNum() - 1);
                        cell.setCellValue(errorRecord.getErrorMessage());
                    }
                }
                index++;
            }

            // write file
            out = new ByteArrayOutputStream();
            workbook.write(out);
            out.close();

            InputStream is = new ByteArrayInputStream(out.toByteArray());
            String returnPath = uploadS3(is);

            log.debug("Upload file {} to amazon S3 : {}", file.getOriginalFilename(), Constants.DEFAULT_UPLOAD_DIR);
            return returnPath;
        } catch (Throwable ex) {
            ex.printStackTrace();
            log.debug("export file excel exception : {}", ex.getClass().getSimpleName());
            throw new ValidationGenericException("System error", MessageKeys.FILE_PARSED_EXCEPTION);
        } finally {
            try {
                if (null != workbook) {
                    workbook.close();
                }
                if (null != out) {
                    out.close();
                }
            } catch (Throwable throwable) {
                throwable.printStackTrace();
                log.debug("Export : {}", throwable.getClass().getSimpleName());
            }
        }
    }

    private void createResponseHeader(Cell Cell, XSSFWorkbook workbook) {
        XSSFFont headerFont = workbook.createFont();
        headerFont.setBold(true);
        headerFont.setFontName(XSSFFont.DEFAULT_FONT_NAME);
        headerFont.setColor(IndexedColors.RED.getIndex());

        CellStyle headerStyle = workbook.createCellStyle();

        // Fill color
        headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        headerStyle.setFillForegroundColor(IndexedColors.YELLOW.getIndex());

        // Font
        headerStyle.setFont(headerFont);

        // Alignment
        headerStyle.setAlignment(HorizontalAlignment.LEFT);
        headerStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        headerStyle.setWrapText(true);

        Cell.setCellStyle(headerStyle);
        Cell.setCellValue(FIELD_HEADER_RESPONSE);
        workbook.getSheetAt(SHEET_RESPONSE).setColumnWidth(Cell.getColumnIndex(), WIDTH_COLUMN_RESPONSE);
    }

    /**
     * Parse XLSX to list of CSV record models.
     */
    private void parseExcel() {
        Workbook workbook = null;

        try {
            workbook = new XSSFWorkbook(file.getInputStream());
            Sheet sheet = workbook.getSheetAt(0);

            List<String> headers = new ArrayList<>();
            Row headerRow = sheet.getRow(START_ROW - 1);
            for (Cell cell : headerRow) {
                headers.add(ConvertUtil.toString(cell.getStringCellValue()));
            }

            if (headers.size() == 0) {
                throw new ValidationGenericException("Invalid XLSX header", MessageKeys.FILE_HEADER_INVALID);
            }

            readExcelData(sheet, headers);
        } catch (ValidationGenericException e) {
            throw e;
        } catch (Throwable ex) {
            ex.printStackTrace();
            log.debug("XLSX parsed exception : {}", ex.getClass().getSimpleName());
            throw new ValidationGenericException("System error", MessageKeys.FILE_PARSED_EXCEPTION);
        } finally {
            try {
                if (workbook != null) {
                    workbook.close();
                }
            } catch (Throwable throwable) {
                throwable.printStackTrace();
                log.debug("XLSX parsed exception : {}", throwable.getClass().getSimpleName());
            }
        }
    }

    private void readExcelData(Sheet sheet, List<String> headers) {
        T returnObj = newInstance();

        final List<String> definedHeaders = returnObj.getHeaders();
        final List<String> definedFieldNames = returnObj.getFields();

        if (definedHeaders.size() != headers.size()) {
            throw new ValidationGenericException("Invalid XLSX header", MessageKeys.FILE_HEADER_INVALID);
        }

        for (int index = START_ROW; index <= sheet.getLastRowNum(); index++) {
            Row row = sheet.getRow(index);

            returnObj = newInstance();
            returnObj.setLineNumber(row.getRowNum() + 1);

            if (row.getLastCellNum() == headers.size()) {
                List<String> errorFields = new ArrayList<>();

                // Get data of each column
                for (int i = 0; i < headers.size(); i++) {
                    String csvField = headers.get(i).trim();
                    String fieldName = definedFieldNames.get(i);
                    String cellValue = parseCellValue(row.getCell(i), returnObj.isDouble(fieldName));

                    String errorField = setValueToObject(returnObj, fieldName, cellValue);

                    // Cannot parse to data type of field name
                    if (StringUtils.hasLength(errorField)) {
                        errorFields.add(csvField);
                    }
                }

                if (!errorFields.isEmpty()) {
                    // Format message
                    returnObj.setErrorMessage(
                        messageSourceReader.resolveLocalizedMessage(
                            MessageKeys.FILE_ROW_WRONG_TYPE,
                            new Object[] { String.valueOf(returnObj.getLineNumber()), String.join(",", errorFields) }
                        )
                    );
                } else {
                    BindingResult binddingResult = ValidationUtil.validate(returnObj);
                    List<FieldErrorVM> fieldErrors = messageSourceReader.processValidationErrors(binddingResult);
                    if (Objects.nonNull(fieldErrors) && fieldErrors.size() > 0) {
                        returnObj.setErrorMessage(
                            messageSourceReader.resolveLocalizedMessage(
                                MessageKeys.FILE_ROW_WRONG_TYPE,
                                new Object[] { String.valueOf(returnObj.getLineNumber()), fieldErrors.get(0).getMessageDetail() }
                            )
                        );
                    }
                }
            } else {
                returnObj.setErrorMessage(
                    messageSourceReader.resolveLocalizedMessage(
                        MessageKeys.FILE_ROW_INVALID,
                        new Object[] { String.valueOf(returnObj.getLineNumber()) }
                    )
                );
            }

            data.add(returnObj);
        }
    }

    private String parseCellValue(Cell cell, boolean isDouble) {
        String cellValue = null;
        if (null == cell || null == cell.getCellStyle()) return cellValue;
        switch (cell.getCellType()) {
            case STRING:
                String value = cell.getRichStringCellValue().getString();
                cellValue = StringUtils.hasText(value) ? value.strip() : "";
                break;
            case NUMERIC:
                if (DateUtil.isCellDateFormatted(cell)) {
                    if (cell.getLocalDateTimeCellValue().getYear() == 1899) {
                        cellValue = ConvertUtil.localTimeToString(
                            cell.getLocalDateTimeCellValue().toLocalTime(),
                            PatternConstants.LOCAL_TIME_FORMAT
                        );
                    } else {
                        cellValue = ConvertUtil.datetimeToString(cell.getLocalDateTimeCellValue(), PatternConstants.LOCAL_DATE_FORMAT_2);
                    }
                } else {
                    if (isDouble) {
                        cellValue = String.valueOf((double) cell.getNumericCellValue());
                    } else {
                        cellValue = String.valueOf((int) cell.getNumericCellValue());
                    }
                }
                break;
            case BOOLEAN:
                cellValue = cell.getBooleanCellValue() + "";
                break;
            default:
        }

        return cellValue;
    }

    /**
     * Parse CSV to list of record models.
     */
    private void parseCSV() {
        CSVReader csvReader = null;
        BufferedReader bufferedReader = null;

        try {
            // Create CSV reader
            InputStreamReader in = new InputStreamReader(file.getInputStream(), FileReader.CSV_CHARSET_UTF8);
            bufferedReader = new BufferedReader(in);

            CSVParser parser = new CSVParserBuilder().withSeparator(CSV_SEPARATOR_COMMA).withIgnoreQuotations(true).build();

            csvReader = new CSVReaderBuilder(bufferedReader).withSkipLines(START_ROW - 1).withCSVParser(parser).build();

            // Validate header
            String[] headers = csvReader.readNext();
            headers = headers == null ? null : Arrays.stream(headers).filter(StringUtils::hasLength).toArray(String[]::new);

            if (Objects.isNull(headers) || headers.length == 0) {
                throw new ValidationGenericException("Invalid CSV header", MessageKeys.FILE_HEADER_INVALID);
            }

            readCSVData(csvReader, headers);
        } catch (ValidationGenericException e) {
            throw e;
        } catch (Throwable ex) {
            ex.printStackTrace();
            log.debug("CSV parsed exception : {}", ex.getClass().getSimpleName());
            throw new ValidationGenericException("System error", MessageKeys.FILE_PARSED_EXCEPTION);
        } finally {
            try {
                if (csvReader != null) {
                    csvReader.close();
                }
                if (bufferedReader != null) {
                    bufferedReader.close();
                }
            } catch (Throwable throwable) {
                throwable.printStackTrace();
                log.debug("CSV parsed exception : {}", throwable.getClass().getSimpleName());
            }
        }
    }

    /**
     * Read CSV data
     */
    private void readCSVData(CSVReader csvReader, String[] headers) throws Exception {
        T returnObj = newInstance();

        final List<String> definedHeaders = returnObj.getHeaders();
        final List<String> definedFieldNames = returnObj.getFields();
        int lineNumber = 2;
        String[] nextLine;

        if (definedHeaders.size() != headers.length) {
            throw new ValidationGenericException("Invalid CSV header", MessageKeys.FILE_HEADER_INVALID);
        }

        // Read and parse line by line in CSV file
        while (Objects.nonNull(nextLine = csvReader.readNext())) {
            // Go to next record
            returnObj = newInstance();
            returnObj.setLineNumber(lineNumber++);

            if (nextLine.length == headers.length) {
                List<String> errorFields = new ArrayList<>();

                // Get data of each column
                for (int i = 0; i < headers.length; i++) {
                    String csvField = headers[i].trim();
                    String fieldName = definedFieldNames.get(i);
                    String errorField = setValueToObject(returnObj, fieldName, nextLine[i]);

                    // Cannot parse to data type of field name
                    if (StringUtils.hasLength(errorField)) {
                        errorFields.add(csvField);
                    }
                }

                if (!errorFields.isEmpty()) {
                    // Format message
                    returnObj.setErrorMessage(
                        messageSourceReader.resolveLocalizedMessage(
                            MessageKeys.FILE_ROW_WRONG_TYPE,
                            new Object[] { String.valueOf(returnObj.getLineNumber()), String.join(",", errorFields) }
                        )
                    );
                } else {
                    BindingResult binddingResult = ValidationUtil.validate(returnObj);
                    List<FieldErrorVM> fieldErrors = messageSourceReader.processValidationErrors(binddingResult);
                    if (Objects.nonNull(fieldErrors) && fieldErrors.size() > 0) {
                        returnObj.setErrorMessage(
                            messageSourceReader.resolveLocalizedMessage(
                                MessageKeys.FILE_ROW_WRONG_TYPE,
                                new Object[] { String.valueOf(returnObj.getLineNumber()), fieldErrors.get(0).getMessageDetail() }
                            )
                        );
                    }
                }
            } else {
                returnObj.setErrorMessage(
                    messageSourceReader.resolveLocalizedMessage(
                        MessageKeys.FILE_ROW_INVALID,
                        new Object[] { String.valueOf(returnObj.getLineNumber()) }
                    )
                );
            }

            data.add(returnObj);
        }
    }

    /**
     * Set the value to object for field name
     */
    private String setValueToObject(Object obj, String fieldName, String value) {
        try {
            if (Objects.isNull(obj) || !StringUtils.hasLength(fieldName)) {
                return null;
            }

            Field f = obj.getClass().getDeclaredField(fieldName);

            f.setAccessible(true);

            try {
                if (Integer.class.isAssignableFrom(f.getType())) {
                    f.set(obj, Integer.valueOf(value));
                } else if (Double.class.isAssignableFrom(f.getType())) {
                    f.set(obj, Double.valueOf(value));
                } else if (BigDecimal.class.isAssignableFrom(f.getType())) {
                    f.set(obj, new BigDecimal(value));
                } else if (Long.class.isAssignableFrom(f.getType())) {
                    f.set(obj, Long.valueOf(value));
                } else if (Float.class.isAssignableFrom(f.getType())) {
                    f.set(obj, Float.valueOf(value));
                } else if (LocalDate.class.isAssignableFrom(f.getType())) {
                    f.set(obj, ConvertUtil.toLocalDate(value, PatternConstants.LOCAL_DATE_FORMAT));
                } else if (LocalTime.class.isAssignableFrom(f.getType())) {
                    f.set(obj, ConvertUtil.toLocalTime(value, PatternConstants.LOCAL_TIME_FORMAT));
                } else if (Boolean.class.isAssignableFrom(f.getType())) {
                    if (!StringUtils.hasText(value)) {
                        f.set(obj, null);
                    } else if (Arrays.asList("1", "0", "true", "false", "x").contains(value.toLowerCase())) {
                        f.set(obj, "1".equals(value) || "true".equals(value.toLowerCase()) || "x".equals(value.toLowerCase()));
                    } else {
                        throw new RuntimeException("Boolean is invalid.[Field：" + fieldName + "; Value：" + value + "]");
                    }
                } else {
                    f.set(obj, !StringUtils.hasText(value) ? null : f.getType().cast(value)); //-->return null to by pass validation
                }
            } catch (Throwable e) {
                if (StringUtils.hasText(value)) {
                    throw e;
                }
            }
        } catch (Throwable e) {
            e.printStackTrace();
            return fieldName;
        }

        return null;
    }

    private void parseFileType() {
        String contentType = file.getContentType();
        if (StringUtils.hasText(contentType)) {
            if (MEDIA_TYPE_APPLICATION_CSV.equals(contentType) || MEDIA_TYPE_TEXT_CSV.equals(contentType)) {
                this.fileType = FileType.csv;
                return;
            } else if (MEDIA_TYPE_APPLICATION_XLSX.equals(contentType)) {
                this.fileType = FileType.xlsx;
                return;
            }
        }

        throw new ValidationGenericException("Upload file: content type does not support", MessageKeys.CONTTENT_TYPE_NOT_SUPPORT);
    }

    /**
     * create a instance
     *
     */
    private T newInstance() {
        return supplier.get();
    }

    /**
     * return temporary directory
     */
    private String getTemporaryDir() {
        return System.getProperty("java.io.tmpdir");
    }
}
