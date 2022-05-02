import com.codeborne.pdftest.PDF;
import com.codeborne.xlstest.XLS;
import com.opencsv.CSVReader;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.InputStreamReader;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import static org.assertj.core.api.Assertions.assertThat;

public class ReadingFromFile {
    @Test
    @DisplayName("Проверяем PDF файл")
    void pdfCheckTest() throws Exception {
        try (ZipFile zipFile = new ZipFile("src/test/resources/zip/pdf-example.zip")) {
            ZipEntry entry = zipFile.getEntry("pdf-example.pdf");
            PDF pdf = new PDF(zipFile.getInputStream(entry));
            assertThat(pdf.text.isEmpty()).isFalse();
            assertThat(pdf.text).contains("Lorem ipsum dolor sit amet, consectetur adipiscing");
            assertThat(pdf.encrypted).isFalse();
        }
    }
    @Test
    @DisplayName("Проверяем XLS файл")
    void xlsCheckTest() throws Exception {
        try (ZipFile zipFile = new ZipFile("src/test/resources/zip/xls-example.zip")) {
            ZipEntry entry = zipFile.getEntry("xls-example.xls");
            XLS xls = new XLS(zipFile.getInputStream(entry));
            assertThat(xls.excel
                    .getSheetAt(0)
                    .getRow(7)
                    .getCell(1)
                    .getStringCellValue()).contains("Etta");
        }
    }

    @Test
    @DisplayName("Проверяем CSV файл")
    void csvCheckTest() throws Exception{
        try(ZipFile zipFile = new ZipFile("src/test/resources/zip/csv-example.zip")) {
            ZipEntry entry = zipFile.getEntry("csv-example.csv");
            CSVReader csv = new CSVReader(new InputStreamReader(zipFile.getInputStream(entry)));
            List<String[]> csvContent = csv.readAll();
            assertThat(csvContent.isEmpty()).isFalse();
            assertThat(csvContent.get(0))
                    .contains(
                            "1",
                            "Eldon Base for stackable storage shelf, platinum",
                            "Muhammed MacIntyre",
                            "3","-213.25",
                            "38.94",
                            "35",
                            "Nunavut",
                            "Storage & Organization",
                            "0.8");
        }
    }
}
