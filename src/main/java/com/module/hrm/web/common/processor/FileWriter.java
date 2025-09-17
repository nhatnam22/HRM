package com.module.hrm.web.common.processor;

import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.Phrase;
import com.lowagie.text.Table;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfWriter;
import com.module.hrm.web.common.enumeration.FileType;
import com.module.hrm.web.common.enumeration.MessageKeys;
import com.module.hrm.web.common.enumeration.TemplateConstants;
import com.module.hrm.web.common.exception.ValidationGenericException;
import com.module.hrm.web.common.model.AbstractRecordDto;
import com.module.hrm.web.common.model.FileDownloadDto;
import com.module.hrm.web.common.utils.ConvertUtil;
import com.opencsv.CSVWriter;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.OutputStreamWriter;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.function.Supplier;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.core.io.InputStreamResource;
import org.springframework.util.StringUtils;

@Slf4j
public class FileWriter<T extends AbstractRecordDto> {

    private static final String FONT_NAME = "Calibri";
    private static final String SHEET_NAME = "Data";
    private static final String DOT_CHARACTER = ".";

    private final List<T> data;

    private final String fileName;

    private final FileType fileType;

    private final Supplier<T> supplier;

    private FileDownloadDto result = null;

    public FileWriter(List<T> data, String fileName, FileType fileType, Supplier<T> supplier) {
        this.data = data;
        this.fileName = fileName;
        this.fileType = fileType;
        this.supplier = supplier;
    }

    /**
     * Export file based on file type
     */
    public void export() {
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        if (FileType.csv.equals(this.getFileType())) {
            exportCSV(out);
        } else if (FileType.pdf.equals(this.getFileType())) {
            exportPDF(out);
        } else {
            exportExcel(out);
        }

        ByteArrayInputStream inputStream = new ByteArrayInputStream(out.toByteArray());

        this.result = new FileDownloadDto(new InputStreamResource(inputStream), this.getFileName(), out.size());
    }

    /**
     * Get media type for download
     */
    public String getMediaType() {
        if (FileType.csv.equals(this.getFileType())) {
            return FileReader.MEDIA_TYPE_APPLICATION_CSV;
        }

        return FileReader.MEDIA_TYPE_APPLICATION_XLSX;
    }

    /**
     * Get result
     */
    public FileDownloadDto getResult() {
        if (result == null) {
            log.debug("CSV Writer did not execute or parsed error.");
        }

        return this.result;
    }

    public File getFileDir() {
        try {
            String pathTemporaryDir = getTemporaryDir();

            File file = Path.of(pathTemporaryDir, Objects.requireNonNull(getResult().getFileName())).toFile();
            FileUtils.copyInputStreamToFile(getResult().getFile().getInputStream(), file);

            log.debug("Save file to temporary dir: {}", file.getAbsolutePath());
            return file;
        } catch (Exception e) {
            e.printStackTrace();
            log.debug("Write file to temporary dir exception : {}", e.getClass().getSimpleName());
            throw new ValidationGenericException("System error", MessageKeys.FILE_PARSED_EXCEPTION);
        }
    }

    /**
     * return temporary directory
     */
    private String getTemporaryDir() {
        return System.getProperty("java.io.tmpdir");
    }

    private void exportPDF(ByteArrayOutputStream out) {
        Document document = null;

        try {
            // Get headers
            T instanceRecord = data.stream().findAny().orElse(null);
            if (instanceRecord == null) instanceRecord = newInstance();

            final String[] header = instanceRecord.getHeaders().toArray(new String[0]);
            final List<String> definedFieldNames = instanceRecord.getFields();

            document = new Document();
            Table table = new Table(header.length);

            // set total width page
            table.setWidth(580);
            table.setLocked(true);

            // set padding for cell
            table.setPadding(5f);

            Font baseFont = FontFactory.getFont(TemplateConstants.FONT_DIRECTORY + TemplateConstants.FONT_TAHOMA, BaseFont.IDENTITY_H);

            // Write PDF file header
            if (header.length > 0) {
                Font headFont = new Font(baseFont.getBaseFont(), 9, Font.BOLD);

                for (int col = 0; col < header.length; col++) {
                    com.lowagie.text.Cell cellHead = new com.lowagie.text.Cell();
                    Phrase phaser = new Phrase(header[col], headFont);

                    cellHead.add(phaser);
                    cellHead.setHorizontalAlignment(Element.ALIGN_CENTER);

                    table.addCell(cellHead);
                }
            }

            // Write PDF file records
            for (T record : data) {
                String[] arrData = record.toArray();
                Font recordFont = new Font(baseFont.getBaseFont(), 8);

                for (int col = 0; col < arrData.length; col++) {
                    String fieldName = definedFieldNames.get(col);
                    String value = arrData[col];

                    Phrase valuePdf = new Phrase(ConvertUtil.toString(value), recordFont);
                    com.lowagie.text.Cell cell = new com.lowagie.text.Cell();

                    if (record.isDouble(fieldName)) {
                        cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                    }

                    cell.add(valuePdf);
                    table.addCell(cell);
                }
            }

            PdfWriter.getInstance(document, out);
            document.open();
            document.add(table);
        } catch (Exception ex) {
            ex.printStackTrace();
            log.debug("Export PDF exception : {}", ex.getClass().getSimpleName());
            throw new ValidationGenericException("System error", MessageKeys.FILE_READ_EXCEPTION);
        } finally {
            try {
                if (null != document) {
                    document.close();
                }
            } catch (Throwable throwable) {
                throwable.printStackTrace();
                log.debug("Export PDF exception : {}", throwable.getClass().getSimpleName());
            }
        }
    }

    /**
     * Export CSV to file
     */
    private void exportCSV(ByteArrayOutputStream out) {
        BufferedWriter bw = null;
        CSVWriter writer = null;

        try {
            // encoding UTF-8
            out.write(0xef);
            out.write(0xbb);
            out.write(0xbf);

            // Stream to export
            bw = new BufferedWriter(new OutputStreamWriter(out, FileReader.CSV_CHARSET_UTF8));
            writer = new CSVWriter(
                bw,
                FileReader.CSV_SEPARATOR_COMMA,
                CSVWriter.NO_QUOTE_CHARACTER,
                CSVWriter.DEFAULT_ESCAPE_CHARACTER,
                CSVWriter.RFC4180_LINE_END
            );

            // Write CSV file header
            T instanceRecord = data.stream().findAny().orElse(null);
            if (instanceRecord == null) instanceRecord = newInstance();

            String[] header = instanceRecord.getHeaders().toArray(new String[0]);
            if (header.length > 0) {
                writer.writeNext(header);
            }

            // Write records
            for (T record : data) {
                String[] arrData = record.toArray();
                Arrays.stream(arrData).filter(StringUtils::hasText).forEach(n -> n = "");
                writer.writeNext(arrData);
            }

            // flush and close
            writer.close();
        } catch (Throwable ex) {
            ex.printStackTrace();
            log.debug("Export CSV exception : {}", ex.getClass().getSimpleName());
            throw new ValidationGenericException("System error", MessageKeys.FILE_WRITE_EXCEPTION);
        } finally {
            try {
                if (Objects.nonNull(bw)) {
                    bw.close();
                }
            } catch (Throwable throwable) {
                throwable.printStackTrace();
                log.debug("CSV read exception : {}", throwable.getClass().getSimpleName());
            }
        }
    }

    /**
     * Export XLSX to file
     */
    private void exportExcel(ByteArrayOutputStream out) {
        XSSFWorkbook workbook = new XSSFWorkbook();

        try {
            Sheet sheet = workbook.createSheet(SHEET_NAME);

            // Write CSV file header
            T instanceRecord = data.stream().findAny().orElse(null);
            if (instanceRecord == null) instanceRecord = newInstance();

            // Header cell style
            CellStyle headerStyle = workbook.createCellStyle();
            XSSFFont font = workbook.createFont();
            font.setFontName(FONT_NAME);
            font.setBold(true);
            headerStyle.setFont(font);

            // Header
            String[] header = instanceRecord.getHeaders().toArray(new String[0]);
            if (header.length > 0) {
                Row headerRow = sheet.createRow(0);
                //                sheet.autoSizeColumn(0,true);
                for (int col = 0; col < header.length; col++) {
                    Cell cell = headerRow.createCell(col);
                    cell.setCellValue(header[col]);
                    cell.setCellStyle(headerStyle);
                }
            }

            // Write records
            int rowIdx = 1;

            // define number cell
            CellStyle recordStyle = workbook.createCellStyle();
            DataFormat format = workbook.createDataFormat();
            recordStyle.setDataFormat(format.getFormat("#,##0"));
            recordStyle.setAlignment(HorizontalAlignment.RIGHT);

            for (T record : data) {
                Row row = sheet.createRow(rowIdx++);
                String[] arrData = record.toArray();

                for (int col = 0; col < arrData.length; col++) {
                    String fieldName = record.getFields().get(col);
                    String value = arrData[col];
                    Cell cell = row.createCell(col);
                    cell.setCellValue(value);

                    if (isTypeNumber(record, fieldName)) {
                        if (isValueNumber(value)) {
                            cell.setCellStyle(recordStyle);
                            cell.setCellValue(ConvertUtil.toDoubleOrZero(value));
                        }
                    }
                }
            }

            autoSizeColumns(workbook);
            workbook.write(out);
        } catch (Throwable ex) {
            ex.printStackTrace();
            log.debug("Export Excel exception : {}", ex.getClass().getSimpleName());
            throw new ValidationGenericException("System error", MessageKeys.FILE_READ_EXCEPTION);
        } finally {
            try {
                workbook.close();
            } catch (Throwable throwable) {
                throwable.printStackTrace();
                log.debug("Export Excel exception : {}", throwable.getClass().getSimpleName());
            }
        }
    }

    private boolean isValueNumber(String value) {
        try {
            Double.parseDouble(value);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private boolean isTypeNumber(Object obj, String fieldName) {
        try {
            Field f = obj.getClass().getDeclaredField(fieldName);

            if (Integer.class.isAssignableFrom(f.getType())) {
                return true;
            } else if (Double.class.isAssignableFrom(f.getType())) {
                return true;
            } else if (BigDecimal.class.isAssignableFrom(f.getType())) {
                return true;
            } else if (Long.class.isAssignableFrom(f.getType())) {
                return true;
            } else if (Float.class.isAssignableFrom(f.getType())) {
                return true;
            }
        } catch (Exception ex) {
            return false;
        }
        return false;
    }

    private void autoSizeColumns(Workbook workbook) {
        int numberOfSheets = workbook.getNumberOfSheets();
        for (int i = 0; i < numberOfSheets; i++) {
            Sheet sheet = workbook.getSheetAt(i);
            if (sheet.getPhysicalNumberOfRows() > 0) {
                Row row = sheet.getRow(sheet.getFirstRowNum());
                Iterator<Cell> cellIterator = row.cellIterator();
                while (cellIterator.hasNext()) {
                    Cell cell = cellIterator.next();
                    int columnIndex = cell.getColumnIndex();
                    sheet.autoSizeColumn(columnIndex);
                    int currentColumnWidth = sheet.getColumnWidth(columnIndex);
                    sheet.setColumnWidth(columnIndex, currentColumnWidth);
                }
            }
        }
    }

    /**
     * Get file name by file type
     */
    private String getFileName() {
        StringBuilder sb = new StringBuilder(this.fileName);

        sb.append(DOT_CHARACTER);
        sb.append(this.getFileType().toString());

        return sb.toString();
    }

    /**
     * Get file type
     */
    private FileType getFileType() {
        return this.fileType == null ? FileType.xlsx : this.fileType;
    }

    /**
     * create a instance
     */
    private T newInstance() {
        return supplier.get();
    }
}
