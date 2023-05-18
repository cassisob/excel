package sample;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
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
import java.util.Set;

public class Controller1 {


    @FXML
    private Button registerClass, addStudent;

    @FXML
    private TextField className, courseName, name, n1, n2, n3, exame;

    @FXML
    private TableView<TableRegister> grades;
    @FXML
    private TableColumn<TableRegister, String> nameview;
    @FXML
    private TableColumn<TableRegister, String> n1view;
    @FXML
    private TableColumn<TableRegister, String> n2view;
    @FXML
    private TableColumn<TableRegister, String> n3view;
    @FXML
    private TableColumn<TableRegister, String> exameview;
    @FXML
    private TableColumn<TableRegister, String> finalview;

    public static ObservableList<TableRegister> tableRegisterData = FXCollections.observableArrayList();

    public static ObservableList<Student> allData = FXCollections.observableArrayList();

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

    @FXML
    private void initialize() {

        grades.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        nameview.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));
        n1view.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getN1()));
        n2view.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getN2()));
        n3view.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getN3()));
        exameview.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getExame()));
        finalview.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getFinalGrade()));

        grades.setItems(tableRegisterData);
    }


    public void action(ActionEvent event) throws IOException {

        Button b = (Button) event.getSource();

        if (b.equals(addStudent)) {

            double n1Double = Double.parseDouble(n1.getText());
            double n2Double = Double.parseDouble(n2.getText());
            double n3Double = Double.parseDouble(n3.getText());
            double exameDouble = Double.parseDouble(exame.getText());

            Student s = new Student(name.getText(), n1Double, n2Double, n3Double, exameDouble, courseName.getText(), className.getText());

            allData.add(s);
            tableRegisterData.add(new TableRegister(s.getName(), Double.toString(s.getN1()), Double.toString(s.getN2()), Double.toString(s.getN3()), Double.toString(s.getExame()), Double.toString(s.getFinalGrade())));

            name.setText("");
            n1.setText("");
            n2.setText("");
            n3.setText("");
            exame.setText("");

        } else if (b.equals(registerClass)) {
            newSheet();
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

        } else {

            tableRegisterData.clear();

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


    public void newSheet() {

        XSSFSheet sheet = workbook.createSheet(courseName.getText() + " - " + className.getText());
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

        for (TableRegister student : tableRegisterData) {
            data.put(student.getName(), new Object[]{student.getN1(), student.getN2(), student.getN3(), student.getExame(), student.getFinalGrade()});
        }

        Set<String> keyset = data.keySet();
        int rownum = 1;

        for (String key : keyset) {
            row = sheet.createRow(rownum++);
            Object[] objArr = data.get(key);
            cell = row.createCell(0);

            cell.setCellValue(key);
            int cellnum = 1;
            for (Object obj : objArr) {
                cell = row.createCell(cellnum++);
                if (obj instanceof String)
                    cell.setCellValue((String) obj);
                else if (obj instanceof Double)
                    cell.setCellValue((Double) obj);
            }
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
