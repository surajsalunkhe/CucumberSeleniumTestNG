package UnitTestTry;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class Line {
    private Point3D start;
    private Point3D end;
    
    /**
     * INLINE EDIT 11: Calculate length of line using Point objects
     * Demonstrates calling methods on passed Point objects
     */
    public double getLength() {
        return start.calculateDistance(end);
    }
    
    /**
     * INLINE EDIT 12: Get midpoint of line
     * Demonstrates Point object being returned from another class
     */
    public Point3D getMidpoint() {
        return start.calculateMidpoint(end);
    }
    
    /**
     * INLINE EDIT 13: Get slope of line
     * Demonstrates accessing Point methods through composition
     */
    public double getSlope() {
        return start.calculateSlope(end);
    }
    
    /**
     * INLINE EDIT 14: Check if line is horizontal
     */
    public boolean isHorizontal() {
        return start.getY() == end.getY();
    }
    
    /**
     * INLINE EDIT 15: Check if line is vertical
     */
    public boolean isVertical() {
        return start.getX() == end.getX();
    }
    
    /**
     * INLINE EDIT 16: Translate entire line by offsets
     * Demonstrates modifying Point objects within another class
     */
    public Line translate(double dx, double dy) {
        this.start = start.translatedCopy(dx, dy);
        this.end = end.translatedCopy(dx, dy);
        return this;
    }
    
    /**
     * INLINE EDIT 17: Check if point lies on this line
     * Demonstrates complex interaction between Point and Line objects
     */
    public boolean containsPoint(Point3D point) {
        double dist1 = start.calculateDistance(point);
        double dist2 = end.calculateDistance(point);
        double lineLength = getLength();
        return Math.abs(dist1 + dist2 - lineLength) < 0.0001;
    }
}
