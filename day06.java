import java.io.*;
import java.util.*;

public class day06 {
public static double evaluateExpression(String expr) {
    if (expr.contains("*")) {
        String[] parts = expr.split("\\*");
        double result = 1;
        for (String p : parts) {
            result *= Double.parseDouble(p);
        }
        return result;
    } else if (expr.contains("+")) {
        String[] parts = expr.split("\\+");
        double result = 0;
        for (String p : parts) {
            result += Double.parseDouble(p);
        }
        return result;
    }
    return Double.parseDouble(expr);
}

    public static void main(String[] args) throws Exception {

        // ---- CONFIGURE THIS ----
        final int NUM_NUMBER_ROWS = 4;  // number of rows before the +/* grid starts
        // -------------------------

        List<int[]> numberRows = new ArrayList<>();
        List<char[]> opRows = new ArrayList<>();

        BufferedReader br = new BufferedReader(new FileReader("input.txt"));

        String line;
        int rowCount = 0;

        while ((line = br.readLine()) != null) {
            line = line.trim();
            if (line.isEmpty()) continue;

            // numbers row
            if (rowCount < NUM_NUMBER_ROWS) {
                String[] parts = line.split("\\s+");
                int[] row = new int[parts.length];
                for (int i = 0; i < parts.length; i++) {
                    row[i] = Integer.parseInt(parts[i]);
                }
                numberRows.add(row);
            }
            // ops row
            else {
                // remove spaces, keep only + or *
                line = line.replace(" ", "");
                opRows.add(line.toCharArray());
            }

            rowCount++;
        }
        br.close();

        // convert to arrays
        int[][] numbers = numberRows.toArray(new int[0][]);
        char[][] ops = opRows.toArray(new char[0][]);

        
// iterate through each column
double totalSum = 0;

int rows = numbers.length;
int cols = numbers[0].length;

for (int col = 0; col < cols; col++) {

    char op = ops[0][col]; 

    StringBuilder expr = new StringBuilder();

    for (int row = 0; row < rows; row++) {
        expr.append(numbers[row][col]);
        if (row < rows - 1) {
            expr.append(op); 
        }
    }

    String expression = expr.toString();
    double result = evaluateExpression(expression);
    totalSum += result;      
}

System.out.println("TOTAL OF ALL RESULTS = " + totalSum);

}
    }
