// Harry Carpenter (22723303)
//
// Developed using IntelliJ IDEA 2020.1
// with Open JDK 14 & Java JDK 13.0.1

import java.math.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class MyProject {

    public MyProject() {
        // Empty initializer
    }

     public int floodFillCount(int[][] image, int row, int col){
        //black flood fill
        int ff_init = image[row][col];
        //check to see if the initial is the colour wanting to be filled
         // in this case, it's black
         if(ff_init != 0){
             int x = 2, y = 0, newC = 0;
             return floodFill(image, row, col, newC);
         }

         return ff_init;
    }

    static int floodFillUtil(int screen[][], int x, int y,
                             int prevC, int newC)
    {
        int FFCount = 0;
        // Base cases
        if (x < 0 || x >= screen.length || y < 0 || y >= screen[0].length)
            return 0;
        if (screen[x][y] != prevC)
            return 0;

        // Replace the color at (x, y)
        screen[x][y] = newC;
        FFCount++;
        // Recur for north, east, south and west
        FFCount += floodFillUtil(screen, x+1, y, prevC, newC);
        FFCount += floodFillUtil(screen, x-1, y, prevC, newC);
        FFCount += floodFillUtil(screen, x, y+1, prevC, newC);
        FFCount += floodFillUtil(screen, x, y-1, prevC, newC);
        return FFCount;
    }

    // It mainly finds the previous color on (x, y) and
// calls floodFillUtil()
    static int floodFill(int screen[][], int x, int y, int newC)
    {
        int prevC = screen[x][y];
        return floodFillUtil(screen, x, y, prevC, newC);

    }

    public int brightestSquare(int[][] image, int k){
        if (k <= 0)
            throw new ArrayIndexOutOfBoundsException("Chunks must be atleast 1x1");

        int length = image.length;
        int height = image[0].length;

        int highest = 0;

        for(int xpos = 0; xpos < length; xpos++){
            for(int ypos = 0; ypos < height; ypos ++){
                if (k + xpos <= length && k + ypos <= height) {
                    int summ = 0;
                    for (int x = xpos; x < k + xpos; x++) {
                        for (int y = ypos; y < k + ypos; y++) {
                            summ += image[x][y];
                        }
                    }
                    if(summ > highest) highest = summ;
                }
            }
        }
        return highest;
    }


    public int darkestPath(int[][] image, int ur, int uc, int vr, int vc){

        int row = ur; int col = uc;
        int dr = vr; int dc = vc;
        int rowDiff = dr - row;
        int colDiff = dc - col;

        int[][] subArray = new int[Math.abs(rowDiff) + 1][Math.abs(colDiff) + 1];
        int rx = 0;

        if(rowDiff >= 0 && colDiff >= 0){
            //rd +ve
            for (int i = row; i <= dr; i++) {
                int cx = 0;
                //cd+ve
                for(int c = col; c <= dc; c++ ){
                    subArray[rx][cx] = image[i][c];
                    cx++;
                }
                rx++;
            }
        } else if(rowDiff >= 0 && colDiff < 0) {
            //rd +ve
            for (int i = row; i <= dr; i++) {
                int cx = 0;
                //cd -ve
                for(int c = col; c >= dc; c-- ){
                    subArray[rx][cx] = image[i][c];
                    cx++;
                }
                rx++;
            }
        } else if (rowDiff < 0 && colDiff < 0){
            //rd -ve
            for (int i = row; i >= dr; i--) {
                int cx = 0;
                //cd -ve
                for(int c = col; c >= dc; c-- ){
                    subArray[rx][cx] = image[i][c];
                    cx++;
                }
                rx++;
            }
        } else if (rowDiff < 0 && colDiff >= 0){
            //rd -ve
            for (int i = row; i >= dr; i--) {
                int cx = 0;
                //cd -ve
                for(int c = col; c <= dc; c++ ){
                    subArray[rx][cx] = image[i][c];
                    cx++;
                }
                rx++;
            }
        }

        int m = subArray.length; //height;
        int n = subArray[0].length; //width
        int maxLengthOfPath = m + n - 1;
        printMatrix(subArray, m, n, 0, 0, new int[maxLengthOfPath], 0);
        System.out.println();

        List<Integer> yee = new ArrayList<Integer>();
        for (int[] l : ls) {
            int largest = 0;
            for (int i : l) {
                if (i > largest) largest = i;
            }
            yee.add(largest);
        }
        return findMin(yee);
    }


    public static List<int[]> ls = new ArrayList<>();
    private static void printMatrix(int mat[][], int m, int n,
                                    int i, int j, int path[], int idx)
    {
        path[idx] = mat[i][j];
        // Reached the bottom of the matrix so we are left with
        // only option to move right
        if (i == m - 1)
        {
            for (int k = j + 1; k < n; k++)
            {
                path[idx + k - j] = mat[i][k];
            }

            int[] temp = new int[idx+n-j+1];
            for (int l = 0; l < idx + n - j; l++)
            {
                temp[l] = path[l];
            }
            ls.add(temp);
            return;
        }

        // Reached the right corner of the matrix we are left with
        // only the downward movement.

        if (j == n - 1)
        {
            for (int k = i + 1; k < m; k++)
            {
                path[idx + k - i] = mat[k][j];

            }
            int[] temp = new int[idx+n-j+1];

            for (int l = 0; l < idx + m - i; l++)
            {
                temp[l] = path[l];
            }
            ls.add(temp);
            return;
        }

        // Print all the paths that are possible after moving down
        printMatrix(mat, m, n, i + 1, j, path, idx + 1);

        // Print all the paths that are possible after moving right
        printMatrix(mat, m, n, i, j + 1, path, idx + 1);

    }

    public static Integer findMin(List<Integer> list)
    {
        // check list is empty or not
        if (list == null || list.size() == 0) {
            return Integer.MAX_VALUE;
        }

        // create a new list to avoid modification
        // in the original list
        List<Integer> sortedlist = new ArrayList<>(list);

        // sort list in natural order
        Collections.sort(sortedlist);

        // first element in the sorted list
        // would be minimum
        return sortedlist.get(0);
    }

    public int[] brightestPixelsInRowSegments(int[][] image, int[][] queries){
        int[] returns = new int[queries.length];
        int ix = 0;
        for (int[] query : queries) {
            int r = query[0]; int l = query[1]; int u = query[2];
            List<Integer> ls = new ArrayList<Integer>();
            for(int i = l; i < u; i++){
                ls.add(image[r][i]);
            }
            List<Integer> sorted = new ArrayList<>(ls);
            Collections.sort(sorted);
            returns[ix] = sorted.get(sorted.size()-1);
            ix++;
        }
        return returns;
    }

}
