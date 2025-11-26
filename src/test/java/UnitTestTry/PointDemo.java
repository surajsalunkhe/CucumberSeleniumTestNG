package UnitTestTry;

/**
 * PointDemo class demonstrating how to pass Point objects between classes.
 * This class shows practical examples of:
 * 1. Creating Point objects
 * 2. Passing Point objects to methods
 * 3. Returning Point objects from methods
 * 4. Using Point objects with Line and Shape classes
 * 5. Working with collections of Point objects
 * 
 * @author Copilot
 * @version 1.0
 */
public class PointDemo {
    
    public static void main(String[] args) {
        System.out.println("=== Point Object Passing Demo ===\n");
        
        // DEMO 1: Creating Point objects
        Point3D p1 = new Point3D(3.0, 4.0);
        Point3D p2 = new Point3D(6.0, 8.0);
        Point3D p3 = Point3D.builder().x(0.0).y(0.0).build();
        
        System.out.println("Created Points:");
        System.out.println("  p1: " + p1);
        System.out.println("  p2: " + p2);
        System.out.println("  p3: " + p3);
        
        // DEMO 2: Passing Point object to another Point's method
        double distance = p1.calculateDistance(p2);
        System.out.println("\n✓ Distance from p1 to p2: " + distance);
        
        // DEMO 3: Method returning Point object
        Point3D midpoint = p1.calculateMidpoint(p2);
        System.out.println("✓ Midpoint between p1 and p2: " + midpoint);
        
        
        
        
        // DEMO 8: Point transformations
        Point3D rotated = p1.rotate(Math.PI / 4); // 45 degrees
        System.out.println("\n✓ Rotated p1 by 45°: " + rotated);
        
        Point3D translated = p1.translatedCopy(10, 20);
        System.out.println("✓ Translated p1 by (10, 20): " + translated);
        
        // DEMO 9: Point comparisons
        boolean isClose = p1.isCloseTo(new Point3D(3.01, 4.01), 0.1);
        System.out.println("\n✓ Is p1 close to (3.01, 4.01)? " + isClose);
        

        System.out.println("\n=== Demo Complete ===");
    }
    
    /**
     * INLINE EDIT 31: Example method accepting Point and returning modified Point
     */
    public Point3D processPoint(Point3D  input) {
        // Demonstrates: receiving Point, modifying, returning new Point
        return input.translatedCopy(input.getX() * 0.1, input.getY() * 0.1);
    }
    
    /**
     * INLINE EDIT 32: Example method accepting multiple Points
     */
    public double calculateTotalDistance(Point3D start, Point3D... waypoints) {
        double totalDistance = 0;
        Point3D current = start;
        
        for (Point3D waypoint : waypoints) {
            totalDistance += current.calculateDistance(waypoint);
            current = waypoint;
        }
        return totalDistance;
    }
    
    /**
     * INLINE EDIT 33: Example method working with Point in different contexts
     */
    public String analyzePoint(Point3D point) {
        StringBuilder analysis = new StringBuilder();
        analysis.append("Point Analysis: ").append(point).append("\n");
        analysis.append("  Distance from origin: ").append(point.distanceFromOrigin()).append("\n");
        analysis.append("  Quadrant: ").append(point.getQuadrant()).append("\n");
        
        // Create related points
        Point3D origin = new Point3D(0, 0);
        analysis.append("  Distance to origin: ").append(point.calculateDistance(origin)).append("\n");
        
        return analysis.toString();
    }
}
