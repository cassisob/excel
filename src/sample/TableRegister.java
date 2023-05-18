package sample;

import java.io.Serializable;
import java.util.Objects;

public class TableRegister implements Serializable {

    private String name;
    private String n1;
    private String n2;
    private String n3;
    private String exame;
    private String finalGrade;

    public TableRegister(String name, String n1, String n2, String n3, String exame, String finalGrade) {
        this.name = name;
        this.n1 = n1;
        this.n2 = n2;
        this.n3 = n3;
        this.exame = exame;
        this.finalGrade = finalGrade;


    }

    public void setName(String name) {
        this.name = name;
    }

    public void setN1(String n1) {
        this.n1 = n1;
    }

    public void setN2(String n2) {
        this.n2 = n2;
    }

    public void setN3(String n3) {
        this.n3 = n3;
    }

    public void setExame(String exame) {
        this.exame = exame;
    }

    public void setFinalGrade(String finalGrade) {
        this.finalGrade = finalGrade;
    }

    public String getName() {
        return name;
    }

    public String getN1() {
        return n1;
    }

    public String getN2() {
        return n2;
    }

    public String getN3() {
        return n3;
    }

    public String getExame() {
        return exame;
    }

    public String getFinalGrade() {
        return finalGrade;
    }

    @Override
    public String toString() {
            return "tableRegister{" +
                "name='" + name + '\'' +
                ", n1='" + n1 + '\'' +
                ", n2='" + n2 + '\'' +
                ", n3='" + n3 + '\'' +
                ", exame='" + exame + '\'' +
                ", finalGrade=" + finalGrade +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TableRegister tableRegister = (TableRegister) o;
        return Objects.equals(name, tableRegister.name) &&
                Objects.equals(n1, tableRegister.n1) &&
                Objects.equals(n2, tableRegister.n2) &&
                Objects.equals(n3, tableRegister.n3) &&
                Objects.equals(exame, tableRegister.exame) &&
                Objects.equals(finalGrade, tableRegister.finalGrade);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, n1, n2, n3, exame, finalGrade);
    }
}
