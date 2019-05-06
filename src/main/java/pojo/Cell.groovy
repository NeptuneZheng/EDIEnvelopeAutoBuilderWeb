package pojo

class Cell {
    private int x
    private int y
    private Object value
    private double grade

    Cell() {}

    Cell(int x, int y, Object value, double grade) {
        this.x = x
        this.y = y
        this.value = value
        this.grade = grade
    }

    Cell(int x, int y) {
        this.x = x
        this.y = y
    }

    int getX() {
        return x
    }

    void setX(int x) {
        this.x = x
    }

    int getY() {
        return y
    }

    void setY(int y) {
        this.y = y
    }

    Object getValue() {
        return value
    }

    void setValue(Object value) {
        this.value = value
    }

    double getGrade() {
        return grade
    }

    void setGrade(double grade) {
        this.grade = grade
    }

    @Override
    String toString() {
        return "[(${x}, ${y}),(${value}: ${grade}]"
    }
}
