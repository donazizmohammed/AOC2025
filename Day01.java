import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;


//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Day01 {
   public static int x = 50;
   public static void main(String[] args) {
       //TIP Press <shortcut actionId="ShowIntentionActions"/> with your caret at the highlighted text
       // to see how IntelliJ IDEA suggests fixing it.
       ArrayList<String> lines = getFileData("src/data");


       int partOneAnswer = 0;
       int partTwoAnswer = 0;
       for (int i = 0; i < lines.size(); i++) {
           partOneAnswer += getPartOneNumber(lines.get(i));
           partTwoAnswer += getPartTwoNumber(lines.get(i));
       }


       System.out.println("Part one answer: " + partOneAnswer);
       System.out.println("Part two answer: " + partTwoAnswer);
   }


   public static int getPartOneNumber(String line) {
       int counter = 0;
       String number = line.substring(1);
       int number2 = Integer.parseInt(number);
       number2 = number2 % 100;
       if (line.substring(0,1).equals("L")) {
           line = "-" + number2;
       }
       if (line.substring(0,1).equals("R")) {
           line = "+" + number2;
       }
//        line = line.replace("L", "-");
//        line = line.replace("R","+");
       x += Integer.parseInt(line);
       if(x < 0){
           x = 100 - (x * -1);
           counter++;
       }
       if (x > 99){
           x = x - 100;
           counter++;
       }
       if (x == 0){
           counter++;
       }
       System.out.println(x);
       System.out.println(line);
       return counter;
   }


   // one99one
   public static int getPartTwoNumber(String line) {
       int count = 0;


       char dir = line.charAt(0);
       int amount = Integer.parseInt(line.substring(1));


       // For movement, only amount % 100 affects final position
       int moveSteps = amount % 100;


       // For counting passes, we must simulate the full amount
       for (int i = 0; i < amount; i++) {
           if (dir == 'L') x = (x + 99) % 100;
           else x = (x + 1) % 100;


           if (x == 0) count++;
       }


       return count;
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

