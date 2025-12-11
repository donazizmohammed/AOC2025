import java.io.*;
import java.util.*;
import java.math.BigInteger;

public class day06 {

    public static void main(String[] args) throws Exception {

        // ---- CONFIGURE THIS ----
        final int NUM_NUMBER_ROWS = 4;  // number of rows before the +/* grid starts (digits rows)
        // -------------------------

        List<String> lines = new ArrayList<>();
        BufferedReader br = new BufferedReader(new FileReader("src/data"));
        String line;
        while ((line = br.readLine()) != null) {
            // preserve spaces — we need column positions
            lines.add(line);
        }
        br.close();

        if (lines.size() <= NUM_NUMBER_ROWS) {
            System.out.println("0");
            return;
        }

        // normalize line lengths
        int rowsTotal = lines.size();
        int cols = 0;
        for (String s : lines) if (s.length() > cols) cols = s.length();
        for (int i = 0; i < lines.size(); i++) {
            String s = lines.get(i);
            if (s.length() < cols) s = s + " ".repeat(cols - s.length());
            lines.set(i, s);
        }

        BigInteger grandTotal = BigInteger.ZERO;

        int c = 0;
        while (c < cols) {
            // check if this column is a separator (all spaces in the digit rows + operator row)
            boolean colHasChar = false;
            for (int r = 0; r <= NUM_NUMBER_ROWS; r++) { // include operator row index = NUM_NUMBER_ROWS
                if (lines.get(r).charAt(c) != ' ') { colHasChar = true; break; }
            }
            if (!colHasChar) { c++; continue; }

            // find group [start..end] of contiguous non-empty columns
            int start = c;
            int end = c;
            while (end + 1 < cols) {
                boolean nextHas = false;
                for (int r = 0; r <= NUM_NUMBER_ROWS; r++) {
                    if (lines.get(r).charAt(end + 1) != ' ') { nextHas = true; break; }
                }
                if (!nextHas) break;
                end++;
            }

            // find operator in operator row inside group (operator row is index NUM_NUMBER_ROWS)
            char op = '+';
            for (int cc = start; cc <= end; cc++) {
                char ch = lines.get(NUM_NUMBER_ROWS).charAt(cc);
                if (ch != ' ') { op = ch; break; }
            }

            // collect numbers: columns read right-to-left
            BigInteger groupResult = (op == '*') ? BigInteger.ONE : BigInteger.ZERO;
            for (int cc = end; cc >= start; cc--) {
                StringBuilder sb = new StringBuilder();
                for (int r = 0; r < NUM_NUMBER_ROWS; r++) {
                    char ch = lines.get(r).charAt(cc);
                    if (ch != ' ') sb.append(ch);
                }
                BigInteger value = sb.length() == 0 ? BigInteger.ZERO : new BigInteger(sb.toString());
                if (op == '*') groupResult = groupResult.multiply(value);
                else groupResult = groupResult.add(value);
            }

            grandTotal = grandTotal.add(groupResult);

            c = end + 1;
        }

        System.out.println(grandTotal);
    }

    // kept for compatibility (not used by the new main)
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
}
