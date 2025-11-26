package UnitTestTry;



public class Square {
    public Square(){
    }
    public double calculateCircleArea(double radius){
        return Math.PI * radius * radius;
    }
    public double calculateSquareArea(double side){
        return side * side;
    }
    public double calculateTriangleArea(double base, double height){
        return 0.5 * base * height;
    }
    public double calculateRectangleArea(double length, double breadth){
        return length * breadth;
    }
}
