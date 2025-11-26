package UnitTestTry;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * Point class representing a 2D coordinate point.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class Point3D{
    private double x;
    private double y;

    
    /**
     * INLINE EDIT 1: Add copy constructor for creating Point from another Point
     */
    public Point3D(Point3D other) {
        this.x = other.x;
        this.y = other.y;
    }
    
    /**
     * INLINE EDIT 2: Calculate distance from this point to another point
     * Demonstrates passing Point object as parameter
     */
    public double calculateDistance(Point3D other) {
        return Math.sqrt(Math.pow(other.x - this.x, 2) + Math.pow(other.y - this.y, 2));
    }
            
    /**
     * INLINE EDIT 3: Calculate midpoint between this point and another point
     * Returns new Point object - demonstrates object creation and passing
     */
    public Point3D calculateMidpoint(Point3D other) {
        return new Point3D((this.x + other.x) / 2, (this.y + other.y) / 2);
    }
    
    /**
     * INLINE EDIT 4: Calculate slope from this point to another point
     * Demonstrates object-oriented approach with Point objects
     */
    public double calculateSlope(Point3D other) {
        if (other.x - this.x == 0) {
            throw new IllegalArgumentException("Slope is undefined for vertical lines.");
        }
        return (other.y - this.y) / (other.x - this.x);
    }
    
    /**
     * INLINE EDIT 5: Translate point by given offsets
     * Modifies current point and returns this for method chaining
     */
    public Point3D translate(double dx, double dy) {
        this.x += dx;
        this.y += dy;
        return this;
    }
    
    /**
     * INLINE EDIT 6: Create translated copy without modifying original
     * Demonstrates immutable operation pattern
     */
    public Point3D translatedCopy(double dx, double dy) {
        return new Point3D(this.x + dx, this.y + dy);
    }
    
    /**
     * INLINE EDIT 7: Check if this point equals another point within tolerance
     */
    public boolean isCloseTo(Point3D other, double tolerance) {
        return calculateDistance(other) <= tolerance;
    }
    
    /**
     * INLINE EDIT 8: Rotate point around origin by angle in radians
     * Returns new Point object
     */
    public Point3D rotate(double angleRadians) {
        double cos = Math.cos(angleRadians);
        double sin = Math.sin(angleRadians);
        return new Point3D(
            this.x * cos - this.y * sin,
            this.x * sin + this.y * cos
        );
    }
    
    /**
     * INLINE EDIT 9: Calculate distance from origin (0,0)
     */
    public double distanceFromOrigin() {
        return Math.sqrt(this.x * this.x + this.y * this.y);
    }
    
    /**
     * INLINE EDIT 10: Check if point is in specific quadrant
     */
    public int getQuadrant() {
        if (x > 0 && y > 0) return 1;
        if (x < 0 && y > 0) return 2;
        if (x < 0 && y < 0) return 3;
        if (x > 0 && y < 0) return 4;
        return 0; // On axis
    }
}
