package sk.mimac.benchroom.web.export;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import sk.mimac.benchroom.api.dto.impl.BenchmarkRunDto;
import sk.mimac.benchroom.web.tag.RunResultPrinter;

/**
 *
 * @author Milan Fabian
 */
public class ExcelExporter {

    private final String sheetName;
    private final List<BenchmarkRunDto> runs;
    private final int parameterCount;
    private final int monitorCount;

    public ExcelExporter(List<BenchmarkRunDto> runs, String sheetName) {
        this.runs = runs;
        this.sheetName = sheetName;
        parameterCount = runs.get(0).getBenchmarkParameters().size();
        monitorCount = runs.get(0).getResults().size();
    }

    public void export(OutputStream outputStream) throws IOException {
        XSSFWorkbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet(sheetName);
        createHeader(sheet, runs.get(0), createHeaderCellStyle(workbook));
        addData(sheet);
        autoSizeColumns(sheet);
        workbook.write(outputStream);
    }

    private void addData(Sheet sheet) {
        for (int i = 0; i < runs.size(); i++) {
            BenchmarkRunDto run = runs.get(i);
            Row row = sheet.createRow(i + 1);
            for (int y = 0; y < parameterCount; y++) {
                row.createCell(y).setCellValue(run.getBenchmarkParameters().get(y).getName());
            }
            for (int y = 0; y < monitorCount; y++) {
                Cell cell = row.createCell(y + parameterCount);
                cell.setCellValue(RunResultPrinter.printResult(run.getResults().get(y)));
            }
        }
    }

    private void createHeader(Sheet sheet, BenchmarkRunDto run, CellStyle cellStyle) {
        Row header = sheet.createRow(0);
        for (int i = 0; i < monitorCount; i++) {
            Cell cell = header.createCell(i + parameterCount);
            cell.setCellStyle(cellStyle);
            cell.setCellValue(run.getResults().get(i).getMonitorName());
        }
    }

    private CellStyle createHeaderCellStyle(Workbook workbook) {
        CellStyle cellStyle = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setBoldweight(Font.BOLDWEIGHT_BOLD);
        cellStyle.setFont(font);
        return cellStyle;
    }

    private void autoSizeColumns(Sheet sheet) {
        for (int i = 0; i < monitorCount + parameterCount; i++) {
            sheet.autoSizeColumn(i);
        }
    }
}
