package sample;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Controller2 {

    @FXML
    private Button registerStudent, back;

    @FXML
    private TextField className, courseName, name, n1, n2, n3, exame;

    public static FileInputStream file;
    public static XSSFWorkbook workbook;
    static {
        try {
            file = new FileInputStream(new File("arq.xlsx"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
    static {
        try {
            workbook = new XSSFWorkbook(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void action(ActionEvent event) throws IOException {

        Button b = (Button) event.getSource();

        if (b.equals(registerStudent)) {

            double n1Double = Double.parseDouble(n1.getText());
            double n2Double = Double.parseDouble(n2.getText());
            double n3Double = Double.parseDouble(n3.getText());
            double exameDouble = Double.parseDouble(exame.getText());

            Student s = new Student(name.getText(), n1Double, n2Double, n3Double, exameDouble, courseName.getText(), className.getText());

            Controller1.allData.add(s);

            addToSheet(s);

            className.setText("");
            courseName.setText("");
            name.setText("");
            n1.setText("");
            n2.setText("");
            n3.setText("");
            exame.setText("");

        } else if (b.equals(back)) {

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.close();

            FXMLLoader loader = new FXMLLoader(getClass().getResource("sample.fxml"));
            Parent root = loader.load();

            Scene scene = new Scene(root);
            Stage stage1 = new Stage();
            stage1.setTitle("Academico");
            stage1.setScene(scene);
            stage1.setOnCloseRequest(e -> {
                Controller.savePDF();
            });
            stage1.show();
        }
    }

    public void addToSheet(Student s) {

        XSSFSheet sheet = workbook.getSheet(courseName.getText() + " - " + className.getText());
        Map<String, Object[]> data = new HashMap();

        Row row = sheet.createRow(0);
        Cell cell = row.createCell(0);

        cell.setCellValue("Nome");
        cell = row.createCell(1);
        cell.setCellValue("Nota 1");
        cell = row.createCell(2);
        cell.setCellValue("Nota 2");
        cell = row.createCell(3);
        cell.setCellValue("Nota 3");
        cell = row.createCell(4);
        cell.setCellValue("Exame");
        cell = row.createCell(5);
        cell.setCellValue("Nota Final");

        data.put(s.getName(), new Object[]{s.getN1(), s.getN2(), s.getN3(), s.getExame(), s.getFinalGrade()});

        int rowsNum = sheet.getPhysicalNumberOfRows() + 1;

        row = sheet.createRow(rowsNum);
        Object[] objArr = data.get(s.getName());
        cell = row.createCell(0);

        cell.setCellValue(s.getName());


        int cellnum = 1;

        for (Object obj : objArr) {

            cell = row.createCell(cellnum++);
            if (obj instanceof String)
                cell.setCellValue((String) obj);
            else if (obj instanceof Double)
                cell.setCellValue((Double) obj);
        }

        try {
            FileOutputStream out = new FileOutputStream(new File("arq.xlsx"));
            workbook.write(out);
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
