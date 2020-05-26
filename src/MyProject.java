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

    /**
     * Compute the number of pixels that change when performing a black flood-fill
     * from the pixel at ( row , col ) in the given image. A flood-fill operation changes
     * the selected pixel and all contiguous pixels of the same colour to the specified colour.
     * A pixel is considered part of a contiguous region of the same colour if it is exactly
     * one pixel up/down/left/right of another pixel in the region.
     *
     * @param image
     * @param row
     * @param col
     * @return number of cells filled
     */
    public int floodFillCount(int[][] image, int row, int col) {
        //black flood fill
        int ff_init = image[row][col];
        //check to see if the initial is the colour wanting to be filled
        // in this case, it's black
        if (ff_init != 0) {
//            return floodFill(image, row, col, 0);
            return floodFillUtil(image, row, col, image[row][col], 0);
        }

        return ff_init;
    }

    static int floodFillUtil(int image[][], int x, int y, int prevC, int newC) {
        int FFCount = 0;
        // Base cases
        if (x < 0 || x >= image.length || y < 0 || y >= image[0].length)
            return 0;
        if (image[x][y] != prevC)
            return 0;

        // Replace the color at (x, y)
        image[x][y] = newC;
        FFCount++;
        // Recur for north, east, south and west
        FFCount += floodFillUtil(image, x + 1, y, prevC, newC);
        FFCount += floodFillUtil(image, x - 1, y, prevC, newC);
        FFCount += floodFillUtil(image, x, y + 1, prevC, newC);
        FFCount += floodFillUtil(image, x, y - 1, prevC, newC);
        return FFCount;
    }

    /**
     * Compute the total brightness of the brightest exactly k*k square that appears in the given image.
     * The total brightness of a square is defined as the sum of its pixel values.
     * You may assume that k is positive, no greater than R or C, and no greater than 2048.
     * @param image grayscale image to find the brightest square
     * @param k the search area (k*k array)
     * @return colour of brightest square
     */
 public int brightestSquare(int[][] image, int k) {
        if (k <= 0)
            throw new ArrayIndexOutOfBoundsException("Chunks must be at least 1x1");
        // Get dimensions of the image
        int length = image.length; //this is the rows
        int height = image[0].length; //this is the columns

        // Initialise variable to hold the highest value found in the
        // square
        int highest = 0;

        // We now iterate through the square searching
        // the area defined by K.
        // it wil only check the squares it can fit in,
        // thus increasing efficiency.
        for (int xpos = 0; xpos < length; xpos++) {
            for (int ypos = 0; ypos < height; ypos++) {
                if (k + xpos <= length && k + ypos <= height) {
                    int summ = 0;
                    for (int x = xpos; x < k + xpos; x++) {
                        for (int y = ypos; y < k + ypos; y++) {
                            summ += image[x][y];
                        }
                    }
                    if (summ > highest) highest = summ;
                }
            }
        }
        //return the highest value sum from the squares.
        return highest;
    }

    /**
     * Compute the maximum brightness that MUST be encountered when drawing a path from the pixel at (ur, uc) to the pixel at (vr, vc).
     * The path must start at (ur, uc) and end at (vr, vc), and may only move one pixel up/down/left/right at a time in between.
     * The brightness of a path is considered to be the value of the brightest pixel that the path ever touches.
     * This includes the start and end pixels of the path.
     *
     * To do this, we had to construct a basic path finding algorithm between two points
     * and compute it from there.
     * @param image
     * @param ur
     * @param uc
     * @param vr
     * @param vc
     * @return The minimum brightness of any path from (ur, uc) to (vr, vc)
     */
    public int darkestPath(int[][] image, int ur, int uc, int vr, int vc) {

        int row = ur;
        int col = uc;
        int dr = vr;
        int dc = vc;
        int rowDiff = dr - row;
        int colDiff = dc - col;

        int[][] subArray = new int[Math.abs(rowDiff) + 1][Math.abs(colDiff) + 1];
        int rx = 0;


        // The following check to see the direction of the path (N, S, E, W, NW, NE, SW, SE)
        // and flip it appropriately to fit into a top-left bottom-right array for the path
        // finding algorithm, and creates a child array which holds the values only between the
        // two points.
        if (rowDiff >= 0 && colDiff >= 0) {
            //rd +ve
            for (int i = row; i <= dr; i++) {
                int cx = 0;
                //cd+ve
                for (int c = col; c <= dc; c++) {
                    subArray[rx][cx] = image[i][c];
                    cx++;
                }
                rx++;
            }
        } else if (rowDiff >= 0 && colDiff < 0) {
            //rd +ve
            for (int i = row; i <= dr; i++) {
                int cx = 0;
                //cd -ve
                for (int c = col; c >= dc; c--) {
                    subArray[rx][cx] = image[i][c];
                    cx++;
                }
                rx++;
            }
        } else if (rowDiff < 0 && colDiff < 0) {
            //rd -ve
            for (int i = row; i >= dr; i--) {
                int cx = 0;
                //cd -ve
                for (int c = col; c >= dc; c--) {
                    subArray[rx][cx] = image[i][c];
                    cx++;
                }
                rx++;
            }
        } else if (rowDiff < 0 && colDiff >= 0) {
            //rd -ve
            for (int i = row; i >= dr; i--) {
                int cx = 0;
                //cd -ve
                for (int c = col; c <= dc; c++) {
                    subArray[rx][cx] = image[i][c];
                    cx++;
                }
                rx++;
            }
        }

        int m = subArray.length; //height;
        int n = subArray[0].length; //width
        int maxLengthOfPath = m + n - 1;
        constructMatrix(subArray, m, n, 0, 0, new int[maxLengthOfPath], 0);

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

    // We are using a Global variable due to the nature
    // of the function and the inability to operate an efficient
    // callback
    public static List<int[]> ls = new ArrayList<>();


    /**
     * This function takes the input matrix (in this case top left to bottom right)
     * of any path pattern, and construct every path between the two points.
     * @param array
     * @param m
     * @param n
     * @param i
     * @param j
     * @param path
     * @param idx
     */
    private static void constructMatrix(int array[][], int m, int n,
                                    int i, int j, int path[], int idx) {
        path[idx] = array[i][j];
        // Reached the bottom of the matrix so we are left with
        // only option to move right
        if (i == m - 1) {

            // we must use a manual array copy
            // due to the nature of the slave and master arrays
            for (int k = j + 1; k < n; k++) {
                path[idx + k - j] = array[i][k];
            }

            int[] temp = new int[idx + n - j + 1];
            for (int l = 0; l < idx + n - j; l++) {
                temp[l] = path[l];
            }
            ls.add(temp);
            return;
        }

        // Reached the right corner of the matrix we are left with
        // only the downward movement.
        if (j == n - 1) {
            for (int k = i + 1; k < m; k++) {
                path[idx + k - i] = array[k][j];

            }
            int[] temp = new int[idx + n - j + 1];

            for (int l = 0; l < idx + m - i; l++) {
                temp[l] = path[l];
            }
            ls.add(temp);
            return;
        }

        //search paths moving right and down
        constructMatrix(array, m, n, i + 1, j, path, idx + 1);
        constructMatrix(array, m, n, i, j + 1, path, idx + 1);

    }

    /**
     * This is a simple function which finds the Minimum
     * value from an Integer List<>
     * @param list input integer list
     * @return the minimum value
     */
    public static Integer findMin(List<Integer> list) {
        // check list is empty or not, and return
        // the placeholder MAX_VALUE if necessary.
        // This is mainly for test environment execution,
        // but it allows the function to return something to work with.
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

    /**
     * Compute the results of a list of queries on the given image.
     * Each query will be a three-element int array {r, l, u} defining a row segment. You may assume l < u.
     * A row segment is a set of pixels (r, c) such that r is as defined, l <= c, and c < u.
     * For each query, find the value of the brightest pixel in the specified row segment.
     * Return the query results in the same order as the queries are given.
     * @param image image to pass to function
     * @param queries the query to use to get the segment of the row
     * @return array with the brightest pixels from query
     */
    public int[] brightestPixelsInRowSegments(int[][] image, int[][] queries) {
        int[] returns = new int[queries.length];
        // Initialise index in order to iterate through each query and
        // add it to the return array in the appropriate position.
        int ix = 0;
        for (int[] query : queries) {
            int r = query[0];
            int l = query[1];
            int u = query[2];
            List<Integer> ls = new ArrayList<Integer>();
            for (int i = l; i < u; i++) {
                ls.add(image[r][i]);
            }
            List<Integer> sorted = new ArrayList<>(ls);
            Collections.sort(sorted);
            returns[ix] = sorted.get(sorted.size() - 1);
            ix++;
        }
        return returns;
    }

}
