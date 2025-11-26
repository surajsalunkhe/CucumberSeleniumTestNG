package UnitTestTry;
//create a function which will calcualte the area of pentagaon
public class AreaCalculator {
    public AreaCalculator(){
    }
    public double calculatePentagonArea(double side){
        return (5 * side * side) / (4 * Math.tan(Math.PI / 5));
    }
    public static void main(String[] args) {
        AreaCalculator areaCalculator = new AreaCalculator();
        double area = areaCalculator.calculatePentagonArea(5);
        System.out.println("Area of Pentagon: " + area);
    }
}
