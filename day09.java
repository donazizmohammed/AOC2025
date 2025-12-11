import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class day09 {
    static record P(long x, long y) {}

    public static void main(String[] args) {
         ArrayList<String> fileData = getFileData("src/data");
         ArrayList<P> poly = parsePoints(fileData);

         long maxArea = 0;
         for (int i = 0; i < fileData.size(); i++) {
             for (int j = i + 1; j < fileData.size(); j++) {
                 P a = parsePoint(fileData.get(i));
                 P b = parsePoint(fileData.get(j));
                 long area = getLargestCombination(fileData.get(i), fileData.get(j));
                 if (area <= maxArea) continue;
                 if (rectangleIsInsidePolygon(a, b, poly)) {
                     if (area > maxArea) maxArea = area;
                 }
             }
         }
         System.out.println(maxArea);
    }

    public static long getLargestCombination(String nums1, String nums2) {
       int comma1 = nums1.indexOf(",");
       long x1 = Long.parseLong(nums1.substring(0, comma1));
       long y1 = Long.parseLong(nums1.substring(comma1 + 1));

       int comma2 = nums2.indexOf(",");
       long x2 = Long.parseLong(nums2.substring(0, comma2));
       long y2 = Long.parseLong(nums2.substring(comma2 + 1));

       // coordinates are inclusive — add 1 to include both corner tiles
       long L = Math.abs(x2 - x1) + 1;
       long W = Math.abs(y2 - y1) + 1;

       return L * W;
    }

    // parse helpers
    static ArrayList<P> parsePoints(ArrayList<String> lines) {
        ArrayList<P> pts = new ArrayList<>();
        for (String ln : lines) pts.add(parsePoint(ln));
        return pts;
    }
    static P parsePoint(String s) {
        int c = s.indexOf(',');
        long x = Long.parseLong(s.substring(0,c));
        long y = Long.parseLong(s.substring(c+1));
        return new P(x,y);
    }

    // rectangle inclusion test for rectilinear polygon built from given points in order
    static boolean rectangleIsInsidePolygon(P a, P b, ArrayList<P> poly) {
        long x1 = Math.min(a.x(), b.x()), x2 = Math.max(a.x(), b.x());
        long y1 = Math.min(a.y(), b.y()), y2 = Math.max(a.y(), b.y());

        // if any polygon segment crosses the rectangle interior -> reject
        int n = poly.size();
        for (int i = 0; i < n; i++) {
            P p1 = poly.get(i);
            P p2 = poly.get((i+1) % n);
            if (segmentCrossesInterior(p1, p2, x1, x2, y1, y2)) return false;
        }

        // pick a representative point that lies inside the rectangle (prefer integer tile)
        // use midpoint so we avoid picking a boundary point when possible
        long rx = x1 + (x2 - x1) / 2;
        long ry = y1 + (y2 - y1) / 2;
        // if rectangle is 1x1 the representative is the single tile (which is red and on boundary)
        // point-in-polygon that treats boundary as inside:
        return pointInOrOnPolygon(rx, ry, poly);
    }

    static boolean segmentCrossesInterior(P p1, P p2, long rx1, long rx2, long ry1, long ry2) {
        if (p1.y() == p2.y()) { // horizontal
            long y = p1.y();
            if (y > ry1 && y < ry2) { // strictly inside vertical span -> crossing interior if x ranges strictly overlap
                long sx1 = Math.min(p1.x(), p2.x());
                long sx2 = Math.max(p1.x(), p2.x());
                if (sx2 > rx1 && sx1 < rx2) return true; // strict overlap only
            }
        } else if (p1.x() == p2.x()) { // vertical
            long x = p1.x();
            if (x > rx1 && x < rx2) { // strictly inside horizontal span
                long sy1 = Math.min(p1.y(), p2.y());
                long sy2 = Math.max(p1.y(), p2.y());
                if (sy2 > ry1 && sy1 < ry2) return true; // strict overlap only
            }
        }
        return false;
    }

    // ray-casting point-in-polygon; returns true if point strictly inside or on polygon boundary
    static boolean pointInOrOnPolygon(long px, long py, ArrayList<P> poly) {
        int n = poly.size();
        boolean inside = false;
        for (int i = 0; i < n; i++) {
            P a = poly.get(i);
            P b = poly.get((i+1)%n);
            // check boundary (segment contains point)
            if (onSegment(a, b, px, py)) return true;

            // standard ray-casting crossing test (ray to +x)
            long ay = a.y(), by = b.y();
            long ax = a.x(), bx = b.x();
            // consider edges where py is between ay and by (half-open on top)
            boolean intersect = ((ay > py) != (by > py)) &&
                                (px < (double)(bx - ax) * (py - ay) / (double)(by - ay) + ax);
            if (intersect) inside = !inside;
        }
        return inside;
    }

    static boolean onSegment(P a, P b, long px, long py) {
        long ax = a.x(), ay = a.y(), bx = b.x(), by = b.y();
        if (ax == bx) { // vertical
            if (px != ax) return false;
            long y1 = Math.min(ay, by), y2 = Math.max(ay, by);
            return py >= y1 && py <= y2;
        } else if (ay == by) { // horizontal
            if (py != ay) return false;
            long x1 = Math.min(ax, bx), x2 = Math.max(ax, bx);
            return px >= x1 && px <= x2;
        }
        return false;
    }

   public static ArrayList<String> getFileData(String fileName) {
        ArrayList<String> fileData = new ArrayList<String>();
        try {
            File f = new File(fileName);
            Scanner s = new Scanner(f);
            while (s.hasNextLine()) {
                String line = s.nextLine();
                if (!line.equals(""))
                    fileData.add(line);
            }
            s.close();
            return fileData;
        }
        catch (FileNotFoundException e) {
            return fileData;
        }
    }
}