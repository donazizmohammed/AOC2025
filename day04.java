import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class day04{
    public static void main(String[] args) {
        ArrayList<String> fileData = getFileData("src/data");

        // build 2D Array
        // with a border around it so we dont go out of bounds
        String[][] grid = get2DArray(fileData);

        int partOneAnswer = 0;
        // go through the grid one by one
        // look for a paper roll
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                String e = grid[i][j];
                if (e.equals("@")) {
                    int count = countAdjacent(grid, i, j);
                    if (count < 4) {
                        partOneAnswer++;
                    }
                }
            }
        }
        System.out.println("Part one answer: " + partOneAnswer);

    }

    // COMPLETE THIS METHOD! This method should see how many paper rolls are surrounding
    // the roll at position "row" and "col"
    // You should never go out of bounds because I created a border around the 2D grid
    // to prevent this
     public static int countAdjacent(String[][] grid, int row, int col) {
        int count = 0;
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                if (i == 0 && j == 0) {
                continue; 
            }
                if (grid[row + i][col + j].equals("@")) {
                    count++;
                }
            }
        }
        return count;
    }

    public static String[][] get2DArray(ArrayList<String> fileData) {

        String borderRow = "";
        for (int i = 0; i < fileData.get(0).length(); i++) {
            borderRow += ".";
        }

        fileData.add(0, borderRow);
        fileData.add(borderRow);

        for (int i = 0; i < fileData.size(); i++) {
            String s = fileData.get(i);
            s = "." + s + ".";
            fileData.set(i, s);
        }

        int rows = fileData.size();
        int cols = fileData.get(0).length();
        String[][] grid = new String[rows][cols];


        for (int i = 0; i < fileData.size(); i++) {
            String row = fileData.get(i);
            for (int j = 0; j < row.length(); j++) {
                String entry = row.substring(j, j+1);
                grid[i][j] = entry;
            }
        }

        return grid;
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