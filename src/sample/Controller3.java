package sample;

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

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

public class Controller3 {

    @FXML
    private Button back, search;

    @FXML
    private TextField className, courseName;

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

        if (b.equals(search)) {

            tableRegisterData.clear();

            ArrayList<String> students = searchStudents();

            students.remove(0);
            students.remove(0);
            students.remove(0);
            students.remove(0);
            students.remove(0);

            for (int x = 1; x <= students.size() - 6;){

                String name = students.get(x);
                double n1Double = Double.parseDouble(students.get(x + 1));
                double n2Double = Double.parseDouble(students.get(x + 2));
                double n3Double = Double.parseDouble(students.get(x + 3));
                double exameDouble = Double.parseDouble(students.get(x + 4));

                Student s = new Student(name, n1Double, n2Double, n3Double, exameDouble, name, name);


                tableRegisterData.add(new TableRegister(s.getName(), Double.toString(s.getN1()), Double.toString(s.getN2()), Double.toString(s.getN3()), Double.toString(s.getExame()), Double.toString(s.getFinalGrade())));

                x = x + 6;
            }


        } else if (b.equals(back)) {

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

    public ArrayList<String> searchStudents() {
        ArrayList<String> students = new ArrayList<>();

        try {
            FileInputStream file = new FileInputStream(new File("arq.xlsx"));

            XSSFWorkbook workbook = new XSSFWorkbook(file);

            XSSFSheet sheet = workbook.getSheet(courseName.getText() + " - " + className.getText());

            Iterator<Row> rowIterator = sheet.iterator();

            while (rowIterator.hasNext()) {
                Row row = rowIterator.next();

                Iterator<Cell> cellIterator = row.cellIterator();

                while (cellIterator.hasNext()) {
                    Cell cell = cellIterator.next();

                    switch (cell.getCellType()) {
                        case Cell.CELL_TYPE_NUMERIC:
                            double number = cell.getNumericCellValue();
                            students.add(Double.toString(number));
                            break;
                        case Cell.CELL_TYPE_STRING:
                            students.add(cell.getStringCellValue());
                            break;
                    }
                }
            }
            file.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return students;
    }


}
