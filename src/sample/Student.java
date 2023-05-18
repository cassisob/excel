package sample;

import java.io.Serializable;

public final class Student implements Serializable {
    private String name;
    private double n1;
    private double n2;
    private double n3;
    private double exame;
    private double finalGrade;
    private String courseName;
    private String className;

    public Student(String name, double n1, double n2, double n3, double exame, String courseName, String className) {
        this.name = name;
        this.n1 = n1;
        this.n2 = n2;
        this.n3 = n3;
        this.exame = exame;
        this.finalGrade = (n1 + n2 + n3) / 3;
        this.courseName = courseName;
        this.className = className;

        if (this.exame != 0) {
            this.finalGrade = (this.finalGrade * 0.6) + (this.exame * 0.4);
        }
    }

    public String getName() {
        return name;
    }

    public double getN1() {
        return n1;
    }

    public double getN2() {
        return n2;
    }

    public double getN3() {
        return n3;
    }

    public double getExame() {
        return exame;
    }

    public double getFinalGrade() {
        return finalGrade;
    }

    public String getCourseName() {
        return courseName;
    }

    public String getClassName() {
        return className;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setN1(double n1) {
        this.n1 = n1;
    }

    public void setN2(double n2) {
        this.n2 = n2;
    }

    public void setN3(double n3) {
        this.n3 = n3;
    }

    public void setExame(double exame) {
        this.exame = exame;
    }

    public void setFinalGrade(double finalGrade) {
        this.finalGrade = finalGrade;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Student student = (Student) o;
        return name.equals(student.name);
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
}