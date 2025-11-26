package UnitTestTry;


public class GeometryCalculator {
    
    /**
     * INLINE EDIT 24: Calculate area of triangle using three points
     * Demonstrates passing multiple Point objects to static method
     */
    public static double triangleArea(Point3D p1, Point3D p2, Point3D p3) {
        return Math.abs(
            (p1.getX() * (p2.getY() - p3.getY()) +
             p2.getX() * (p3.getY() - p1.getY()) +
             p3.getX() * (p1.getY() - p2.getY())) / 2.0
        );
    }
    
    /**
     * INLINE EDIT 25: Check if three points are collinear
     * Demonstrates complex logic with Point objects
     */
    public static boolean areCollinear(Point3D p1, Point3D p2, Point3D p3) {
        return Math.abs(triangleArea(p1, p2, p3)) < 0.0001;
    }
    
    /**
     * INLINE EDIT 26: Calculate angle between three points (p2 is vertex)
     * Returns angle in radians
     */
    public static double angleAtVertex(Point3D p1, Point3D vertex, Point3D p3) {
        double dist1 = vertex.calculateDistance(p1);
        double dist2 = vertex.calculateDistance(p3);
        double dist3 = p1.calculateDistance(p3);
        
        // Using law of cosines
        double cosAngle = (dist1 * dist1 + dist2 * dist2 - dist3 * dist3) / (2 * dist1 * dist2);
        return Math.acos(cosAngle);
    }
    
    /**
     * INLINE EDIT 27: Find closest point to target from array of points
     * Demonstrates working with Point arrays
     */
    public static Point3D findClosest(Point3D target, Point3D[] points) {
        if (points == null || points.length == 0) return null;
        
        Point3D closest = points[0];
        double minDistance = target.calculateDistance(closest);
        
        for (int i = 1; i < points.length; i++) {
            double distance = target.calculateDistance(points[i]);
            if (distance < minDistance) {
                minDistance = distance;
                closest = points[i];
            }
        }
        return closest;
    }
    
    /**
     * INLINE EDIT 28: Check if two lines intersect
     * Demonstrates passing Line objects (which contain Point objects)
     */
    public static boolean doLinesIntersect(Line line1, Line line2) {
        Point3D p1 = line1.getStart();
        Point3D p2 = line1.getEnd();
        Point3D p3 = line2.getStart();
        Point3D p4 = line2.getEnd();
        
        double d = (p1.getX() - p2.getX()) * (p3.getY() - p4.getY()) - 
                   (p1.getY() - p2.getY()) * (p3.getX() - p4.getX());
        
        return Math.abs(d) > 0.0001; // Not parallel
    }
    
    /**
     * INLINE EDIT 29: Calculate perimeter of polygon from points array
     */
    public static double calculatePerimeter(Point3D[] points) {
        if (points == null || points.length < 2) return 0;
        
        double perimeter = 0;
        for (int i = 0; i < points.length; i++) {
            Point3D current = points[i];
            Point3D next = points[(i + 1) % points.length];
            perimeter += current.calculateDistance(next);
        }
        return perimeter;
    }
    
    /**
     * INLINE EDIT 30: Create regular polygon with n sides centered at origin
     * Demonstrates creating and returning array of Point objects
     */
    public static Point3D[] createRegularPolygon(int sides, double radius) {
        Point3D[] points = new Point3D[sides];
        double angleStep = 2 * Math.PI / sides;
        
        for (int i = 0; i < sides; i++) {
            double angle = i * angleStep;
            points[i] = new Point3D(
                radius * Math.cos(angle),
                radius * Math.sin(angle)
            );
        }
        return points;
    }
}
