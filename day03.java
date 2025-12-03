import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
public class day03{
    public static class Day03Template {
        public static void main(String[] args) {

            // **** DO NOT EDIT ANYTHING HERE ****
            ArrayList<String> fileData = getFileData("src/data");
            int partOneAnswer = 0;
            for (String batteries : fileData) {
                int voltage = getLargestCombination(batteries);
                partOneAnswer += voltage;
            }

            System.out.println("Part one answer: " + partOneAnswer);
        }

        // COMPLETE THIS METHOD!
        public static int getLargestCombination(String batteries) {

            int largestNumber = 0;
            for (int i = 0; i < batteries.length()-1; i++) {
                int digit = Character.getNumericValue(batteries.charAt(i));
                largestNumber = Math.max(largestNumber, digit);
            }

            int index = batteries.indexOf(String.valueOf(largestNumber));

            String tail = batteries.substring(index);

            int secondLargest = 0;
            for (int i = 1; i < tail.length(); i++) {
                int digit = Character.getNumericValue(tail.charAt(i));
                secondLargest = Math.max(secondLargest, digit);
            }
            ArrayList<String> ans = new ArrayList<>();

            ans.add(String.valueOf(largestNumber) +  String.valueOf(secondLargest));
            int sum = 0;
            for (int i = 0; i < ans.size(); i++)
                sum += Integer.parseInt(ans.get(i));
            return sum;
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
                return fileData;
            }
            catch (FileNotFoundException e) {
                return fileData;
            }
        }
    }
}
