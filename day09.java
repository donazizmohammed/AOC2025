import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class day09 {
    public static void main(String[] args) {
         ArrayList<String> fileData = getFileData("src/data");
         long maxArea = 0;
         for (int i = 0; i < fileData.size(); i++) {
             for (int j = i + 1; j < fileData.size(); j++) {
                 String a = fileData.get(i);
                 String b = fileData.get(j);
                 int commaA = a.indexOf(",");
                 int commaB = b.indexOf(",");
                 int ax = Integer.parseInt(a.substring(0, commaA));
                 int ay = Integer.parseInt(a.substring(commaA + 1));
                 int bx = Integer.parseInt(b.substring(0, commaB));
                 int by = Integer.parseInt(b.substring(commaB + 1));
                 long area = getLargestCombination(a, b);
                 if (area > maxArea) maxArea = area;
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