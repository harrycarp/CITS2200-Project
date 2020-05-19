public class FloodFill {
    static int M = 4;
    static int N = 5;

    static int count = 0;

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

    // Driver code
    public static void main(String[] args)
    {
        int screen[][] = {
                { 0, 0, 1, 1, 1 },
                { 0, 1, 1, 2, 2 },
                { 2, 3, 3, 0, 2 },
                { 2, 2, 2, 2, 2 },
        };

        int x = 2, y = 0, newC = 0;
        int c = floodFill(screen, x, y, newC);

        System.out.println("Updated screen after call to floodFill: ");
        for (int i = 0; i < M; i++)
        {
            for (int j = 0; j < N; j++)
                System.out.print(screen[i][j] + " ");
            System.out.println();
        }
        System.out.println(c);
    }
}

