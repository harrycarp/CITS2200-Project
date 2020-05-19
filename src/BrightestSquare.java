import java.util.*;
import java.lang.*;
import java.io.*;

/* Name of the class has to be "Main" only if the class is public. */
class Ideone {
    private static Random generator = new Random();

    public static void main(String[] args) throws java.lang.Exception {
        double[][] mainArray = new double[][]{
                {0, 0, 1, 1, 1},
                {0, 1, 1, 2, 2},
                {2, 3, 3, 0, 2},
                {2, 2, 2, 2, 2}
        };

        double highest = 0;

        List<double[][]> l = chunks(mainArray, 4);
        double sumr = 0;
        for (double[][] chunk : l) {

            for (double[] doubles : chunk) {
                System.out.println(Arrays.toString(doubles));
                for (double aDouble : doubles) {
                    sumr += aDouble;
                }

            }
            if (sumr > highest) highest = sumr;
            System.out.println("sumr " + sumr);
        }
    }

    public static List<double[][]> chunks(double[][] larger, int chunksize) throws
            ArrayIndexOutOfBoundsException, NullPointerException {
        if (chunksize <= 0)
            throw new ArrayIndexOutOfBoundsException("Chunks must be atleast 1x1");
        List<double[][]> subArrays = new ArrayList<>();

        int length = larger.length;
        int height = larger[0].length;

        int[] initpos = new int[]{0, 0};
        int highest = 0;
        int max_x = chunksize;
        int max_y = chunksize;
        for(int xpos = 0; xpos < length; xpos++){
            for(int ypos = 0; ypos < height; ypos ++){
                if (max_x + xpos <= length && max_y + ypos <= height) {
                    int summ = 0;
                    for (int x = xpos; x < max_x + xpos; x++) {
                        for (int y = ypos; y < max_y + ypos; y++) {
                            summ += larger[x][y];
                            System.out.println(larger[x][y]);
                        }
                    }
                    System.out.println("sum = " + summ);
                    if(summ > highest) highest = summ;
                    System.out.println();
                } else {
                    System.out.println("won't fit");
                }
            }
        }


        return subArrays;
    }
}