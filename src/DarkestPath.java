
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class DarkestPath {
    /* mat:  Pointer to the starting of mXn matrix
      i, j: Current position of the robot (For the first call use 0,0)
      m, n: Dimentions of given the matrix
      pi:   Next index to be filed in path array
      *path[0..pi-1]: The path traversed by robot till now (Array to hold the
                     path need to have space for at least m+n elements) */

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
                System.out.print(path[l] + " ");
            }
            ls.add(temp);
            System.out.println();
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
                System.out.print(path[l] + " ");
            }
            ls.add(temp);
            return;
        }

        // Print all the paths that are possible after moving down
        printMatrix(mat, m, n, i + 1, j, path, idx + 1);

        // Print all the paths that are possible after moving right
        printMatrix(mat, m, n, i, j + 1, path, idx + 1);

    }

    // Driver code
    public static void main(String[] args)
    {

        int mat[][] = {
                { 0, 0, 1, 1, 1 },
                { 0, 1, 1, 2, 2 },
                { 2, 3, 3, 0, 2 },
                { 2, 2, 2, 2, 2 }
        };

        int row = 1; int col = 0;
        int dr = 0; int dc = 1;
        int rowDiff = dr - row;
        int colDiff = dc - col;

        System.out.println("rowdiff: "+ rowDiff + " coldiff: " + colDiff);

        int[][] subArray = new int[Math.abs(rowDiff) + 1][Math.abs(colDiff) + 1];
        int rx = 0;

        if(rowDiff >= 0 && colDiff >= 0){
            //rd +ve
            for (int i = row; i <= dr; i++) {
                int cx = 0;
                //cd+ve
                for(int c = col; c <= dc; c++ ){
                    System.out.print(mat[i][c] + " ");
                    subArray[rx][cx] = mat[i][c];
                    cx++;
                }
                System.out.println();
                rx++;
            }
        } else if(rowDiff >= 0 && colDiff < 0) {
            //rd +ve
            for (int i = row; i <= dr; i++) {
                int cx = 0;
                //cd -ve
                for(int c = col; c >= dc; c-- ){
                    System.out.print(mat[i][c] + " ");
                    subArray[rx][cx] = mat[i][c];
                    cx++;
                }
                System.out.println();
                rx++;
            }
        } else if (rowDiff < 0 && colDiff < 0){
            //rd -ve
            for (int i = row; i >= dr; i--) {
                int cx = 0;
                //cd -ve
                for(int c = col; c >= dc; c-- ){
                    System.out.print(mat[i][c] + " ");
                    subArray[rx][cx] = mat[i][c];
                    cx++;
                }
                System.out.println();
                rx++;
            }
        } else if (rowDiff < 0 && colDiff >= 0){
            //rd -ve
            for (int i = row; i >= dr; i--) {
                int cx = 0;
                //cd -ve
                for(int c = col; c <= dc; c++ ){
                    System.out.print(mat[i][c] + " ");
                    subArray[rx][cx] = mat[i][c];
                    cx++;
                }
                System.out.println();
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
        System.out.println(findMin(yee));

    }

    public static void log(Object message){
        System.out.println(message);
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

}


// A directed graph using
// adjacency list representation
class Graph {

    // No. of vertices in graph
    private int v;

    // adjacency list
    private ArrayList<Integer>[] adjList;

    //Constructor
    public Graph(int vertices){

        //initialise vertex count
        this.v = vertices;

        // initialise adjacency list
        initAdjList();
    }

    // utility method to initialise
    // adjacency list
    @SuppressWarnings("unchecked")
    private void initAdjList()
    {
        adjList = new ArrayList[v];

        for(int i = 0; i < v; i++)
        {
            adjList[i] = new ArrayList<>();
        }
    }

    // add edge from u to v
    public void addEdge(int u, int v)
    {
        // Add v to u's list.
        adjList[u].add(v);
    }

    // Prints all paths from
    // 's' to 'd'
    public void printAllPaths(int s, int d)
    {
        boolean[] isVisited = new boolean[v];
        ArrayList<Integer> pathList = new ArrayList<>();

        //add source to path[]
        pathList.add(s);

        //Call recursive utility
        printAllPathsUtil(s, d, isVisited, pathList);
    }

    // A recursive function to print
    // all paths from 'u' to 'd'.
    // isVisited[] keeps track of
    // vertices in current path.
    // localPathList<> stores actual
    // vertices in the current path
    private void printAllPathsUtil(Integer u, Integer d,
                                   boolean[] isVisited,
                                   List<Integer> localPathList) {

        // Mark the current node
        isVisited[u] = true;

        if (u.equals(d))
        {
            System.out.println(localPathList);
            // if match found then no need to traverse more till depth
            isVisited[u]= false;
            return ;
        }

        // Recur for all the vertices
        // adjacent to current vertex
        for (Integer i : adjList[u])
        {
            if (!isVisited[i])
            {
                // store current node
                // in path[]
                localPathList.add(i);
                printAllPathsUtil(i, d, isVisited, localPathList);

                // remove current node
                // in path[]
                localPathList.remove(i);
            }
        }

        // Mark the current node
        isVisited[u] = false;
    }

    // Driver program
    public static void main(String[] args)
    {
        // Create a sample graph
        Graph g = new Graph(4);

        int arr[][] = {
                { 0, 0, 1, 1, 1 },
                { 0, 1, 1, 2, 2 },
                { 2, 3, 3, 0, 2 },
                { 2, 2, 2, 2, 2 },
        };

        int ix = 0;
        for (int[] ints : arr) {
            int ixA = 0;
            for(int intA : ints){
                g.addEdge(ix, intA);
                ixA++;
            }
            ix++;
        }

//        g.addEdge(0,1);
//        g.addEdge(0,2);
//        g.addEdge(0,3);
//        g.addEdge(2,0);
//        g.addEdge(2,1);
//        g.addEdge(1,3);
        System.out.println(g);
        // arbitrary source
        int s = 2;

        // arbitrary destination
        int d = 3;

        System.out.println("Following are all different paths from "+s+" to "+d);
        g.printAllPaths(s, d);

    }
}

// This code is contributed by Himanshu Shekhar.