package sample;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.*;
import com.itextpdf.text.pdf.parser.PdfReaderContentParser;
import com.itextpdf.text.pdf.parser.SimpleTextExtractionStrategy;
import com.itextpdf.text.pdf.parser.TextExtractionStrategy;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Controller {

    @FXML
    private Button registerClass, registerStudent;

    public void Action(ActionEvent event) throws IOException {

        Button b = (Button) event.getSource();
        if (b.equals(registerClass)) {
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.close();

            FXMLLoader loader = new FXMLLoader(getClass().getResource("sample1.fxml"));
            Parent root = loader.load();

            Scene scene = new Scene(root);
            Stage stage1 = new Stage();
            stage1.setTitle("Academico");
            stage1.setScene(scene);
            stage1.setOnCloseRequest(e -> {
                savePDF();
            });
            stage1.show();
        } else if (b.equals(registerStudent)) {
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.close();

            FXMLLoader loader = new FXMLLoader(getClass().getResource("sample2.fxml"));
            Parent root = loader.load();

            Scene scene = new Scene(root);
            Stage stage1 = new Stage();
            stage1.setTitle("Academico");
            stage1.setScene(scene);
            stage1.setOnCloseRequest(e -> {
                savePDF();
            });
            stage1.show();
        } else {
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.close();

            FXMLLoader loader = new FXMLLoader(getClass().getResource("sample3.fxml"));
            Parent root = loader.load();

            Scene scene = new Scene(root);
            Stage stage1 = new Stage();
            stage1.setTitle("Academico");
            stage1.setScene(scene);
            stage1.setOnCloseRequest(e -> {
                savePDF();
            });
            stage1.show();
        }
    }

    public static void savePDF() {

        if (!Controller1.allData.isEmpty()) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy_HH.mm.ss");
            String date  = dateFormat.format(new Date());

            Document document = new Document();
            try {
                PdfWriter writer = PdfWriter.getInstance(
                        document,
                        new FileOutputStream("UPDATE" + date + ".pdf")
                );
                document.open();
                Image image = Image.getInstance("image.png");
                image.setAbsolutePosition(400f, 730f);
                image.scaleAbsolute(172, 91);
                document.add(image);
                document.add(new Paragraph(" "));
                document.add(new Paragraph(" "));
                document.add(new Paragraph(" "));
                document.add(new Paragraph(" "));
                String last = "";
                for (Student student : Controller1.allData) {
                    String now = student.getCourseName() + student.getClassName();
                    if (!last.equals(now)) {
                        document.add(new Paragraph(now));
                        PdfPTable head = new PdfPTable(6);
                        head.addCell("Nome");
                        head.addCell("Nota 1");
                        head.addCell("Nota 2");
                        head.addCell("Nota 3");
                        head.addCell("Exame");
                        head.addCell("Nota Final");
                        document.add(head);
                        PdfPTable body = new PdfPTable(6);
                        body.addCell(student.getName());
                        body.addCell(Double.toString(student.getN1()));
                        body.addCell(Double.toString(student.getN2()));
                        body.addCell(Double.toString(student.getN3()));
                        body.addCell(Double.toString(student.getExame()));
                        body.addCell(Double.toString(student.getFinalGrade()));
                        document.add(body);
                        last = now;
                    } else {
                        PdfPTable body = new PdfPTable(6);
                        body.addCell(student.getName());
                        body.addCell(Double.toString(student.getN1()));
                        body.addCell(Double.toString(student.getN2()));
                        body.addCell(Double.toString(student.getN3()));
                        body.addCell(Double.toString(student.getExame()));
                        body.addCell(Double.toString(student.getFinalGrade()));
                        document.add(body);
                    }
                }
                document.close();
                writer.close();
            } catch (DocumentException e) {
                e.printStackTrace();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
