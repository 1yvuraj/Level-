import java.util.Arrays;

public class l001_leetcode {
    // 62
    public int uniquePaths(int n, int m) {
        int ER = n - 1, EC = m - 1;
        int[][] dp = new int[n][m];
        int[][] dir = { { -1, 0 }, { 0, -1 } };
        for (int er = 0; er <= ER; er++) {
            for (int ec = 0; ec <= EC; ec++) {
                if (er == 0 && ec == 0) {
                    dp[er][ec] = 1;
                    continue;
                }

                int count = 0;
                for (int d = 0; d < dir.length; d++) {
                    int r = er + dir[d][0];
                    int c = ec + dir[d][1];
                    if (r >= 0 && c >= 0 && r < n && c < m) {
                        count += dp[r][c];
                    }
                }
                dp[er][ec] = count;
            }
        }

        return dp[ER][EC];
    }

    // 63
    public int uniquePathsWithObstacles(int[][] grid) {
        int n = grid.length, m = grid[0].length, ER = n - 1, EC = m - 1;
        if (grid[0][0] == 1 || grid[ER][EC] == 1)
            return 0;
        int[][] dp = new int[n][m];
        int[][] dir = { { -1, 0 }, { 0, -1 } };
        for (int er = 0; er <= ER; er++) {
            for (int ec = 0; ec <= EC; ec++) {
                if (er == 0 && ec == 0) {
                    dp[er][ec] = 1;
                    continue;
                }

                int count = 0;
                for (int d = 0; d < dir.length; d++) {
                    int r = er + dir[d][0];
                    int c = ec + dir[d][1];
                    if (r >= 0 && c >= 0 && r < n && c < m && grid[r][c] == 0) {
                        count += dp[r][c];
                    }
                }
                dp[er][ec] = count;
            }
        }

        return dp[ER][EC];
    }

    // 396
    public int maxRotateFunction(int[] nums) {
        int sum = 0, n = nums.length, maxSum = 0, sumSF = 0;
        for (int i = 0; i < n; i++) {
            sum += nums[i];
            sumSF += i * nums[i];
        }

        maxSum = sumSF;
        for (int i = 0; i < n; i++) {
            sumSF = sumSF - sum + n * nums[i];
            maxSum = Math.max(maxSum, sumSF);
        }

        return maxSum;
    }

    // 64
    public int minPathSum(int[][] grid) {
        int n = grid.length, m = grid[0].length, ER = n - 1, EC = m - 1;
        int[][] dp = new int[n][m];
        int[][] dir = { { -1, 0 }, { 0, -1 } };
        for (int er = 0; er <= ER; er++) {
            for (int ec = 0; ec <= EC; ec++) {
                if (er == 0 && ec == 0) {
                    dp[er][ec] = grid[er][ec];
                    continue;
                }

                int min = (int) 1e9;
                for (int d = 0; d < dir.length; d++) {
                    int r = er + dir[d][0];
                    int c = ec + dir[d][1];
                    if (r >= 0 && c >= 0 && r < n && c < m) {
                        min = Math.min(min, dp[r][c]);
                    }
                }
                dp[er][ec] = min + grid[er][ec];
            }
        }

        return dp[ER][EC];
    }

    // https://practice.geeksforgeeks.org/problems/gold-mine-problem2608/1/

    public static int goldmine_memo(int sr, int sc, int er, int ec, int[][] arr, int[][] dp, int[][] dir) {
        if (sc == ec) {
            return dp[sr][sc] = arr[sr][sc];
        }

        if (dp[sr][sc] != -1)
            return dp[sr][sc];

        int maxCoin = 0;
        for (int d = 0; d < dir.length; d++) {
            int r = sr + dir[d][0], c = sc + dir[d][1];
            if (r >= 0 && c >= 0 && r < dp.length && c < dp[0].length) {
                maxCoin = Math.max(maxCoin, goldmine_memo(r, c, er, ec, arr, dp, dir) + arr[sr][sc]);
            }
        }

        return dp[sr][sc] = maxCoin;
    }

    public static int maxGold(int n, int m, int[][] arr) {
        int[][] dir = { { 0, 1 }, { 1, 1 }, { -1, 1 } };
        int[][] dp = new int[n][m];
        for (int[] d : dp)
            Arrays.fill(d, -1);
        int maxCoin = 0;
        for (int r = 0; r < arr.length; r++) {
            maxCoin = Math.max(maxCoin, goldmine_memo(r, 0, n - 1, m - 1, arr, dp, dir));
        }

        return maxCoin;
    }

    // https://www.geeksforgeeks.org/count-the-number-of-ways-to-divide-n-in-k-groups-incrementally/

}
//bfs
class Solution {

    public class pair {
        int x = 0;
        int y = 0;
        int move = 1;

        pair(int x, int y, int move) {
            this.x = x;
            this.y = y;
            this.move = move;
        }
    }

    public int shortestPathBinaryMatrix(int[][] grid) {
        int[][] dir = { { -1, 0 }, { -1, 1 }, { 0, 1 }, { 1, 1 }, { 1, 0 }, { 1, -1 }, { 0, -1 }, { -1, -1 } };
        if (grid[0][0] == 1 || grid[grid.length - 1][grid[0].length - 1] == 1) return -1;
        int ans = shortesPath(grid.length - 1, grid[0].length - 1, grid, dir);
        return ans;
    }

    // pair<int,string>
    public int shortesPath(int er, int ec, int[][] mat, int[][] dir) {
        LinkedList<pair> list = new LinkedList<>();
        list.addLast(new pair(0, 0, 1));
        while (list.size() > 0) {
            pair ans = list.removeFirst();
            int x = ans.x;
            int y = ans.y;
            int move = ans.move;

            for (int d = 0; d < dir.length; d++) {
                if (x == er && y == ec) return ans.move;
                int r = x + dir[d][0];
                int c = y + dir[d][1];

                if (r >= 0 && c >= 0 && r <= er && c <= ec && mat[r][c] == 0) {
                    list.addLast(new pair(r, c, move + 1));
                    mat[r][c] = 1;
                }
            }
        }
        return -1;
    }
}
//queue se 
 class Solution {
     class Pair{
    int x;
    int y;
    int count;
    Pair(int x, int y, int count){
        this.x = x;
        this.y = y;
        this.count = count;
    }
}
public int shortestPathBinaryMatrix(int[][] grid) {
    return BFS(grid, 0, 0, grid.length-1, grid[0].length-1);
}
public int BFS(int grid[][], int start_x, int start_y , int target_x, int target_y){
    Queue<Pair> q = new LinkedList<>();
    q.add(new Pair(start_x, start_y, 1));
    while(q.size()>0){
        Pair rem = q.remove();
        int x = rem.x;
        int y = rem.y;
        int count = rem.count;
        if(x>=0 && y>=0 && x<grid.length && y<grid[0].length && grid[x][y]!=1 ){
        grid[x][y] = 1;
        if(x==target_x && y== target_y)
            return rem.count;
        q.add(new Pair(x-1, y, count+1 ));
        q.add(new Pair(x-1, y+1, count+1));
        q.add(new Pair(x, y+1 , count+1));
        q.add(new Pair(x+1, y+1, count+1));
        q.add(new Pair(x+1, y, count+1));
        q.add(new Pair(x+1, y-1, count+1));
        q.add(new Pair(x, y-1, count+1));
        q.add(new Pair(x-1, y-1, count+1));
      }
   }
    return -1;
}
}
//dfs but tle 
class Solution {

    public class pairSP {
        int len = (int) 1e9;
    }

    public int shortestPathBinaryMatrix(int[][] grid) {
        int[][] dir={{-1,0},{-1,1},{0,1},{1,1},{1,0},{1,-1},{0,-1},{-1,-1}};
        if (grid[0][0] == 1 || grid[grid.length - 1][grid[0].length - 1] == 1) return -1;
        pairSP ans = shortesPath(0, 0, grid.length - 1, grid[0].length - 1, grid, dir);
        if (ans.len == (int) 1e9) return -1;
        return ans.len;
    }

    // pair<int,string>
    public pairSP shortesPath(int sr, int sc, int er, int ec, int[][] mat, int[][] dir) {
        if (sr == er && sc == ec) {
            pairSP base = new pairSP();
            base.len = 1;
            return base;
        }
        pairSP ans = new pairSP();
        mat[sr][sc] = 1; // block
        for (int d = 0; d < dir.length; d++) {
            int r = sr + dir[d][0];
            int c = sc + dir[d][1];

            if (r >= 0 && c >= 0 && r <= er && c <= ec && mat[r][c] == 0) {
                pairSP recAns = shortesPath(r, c, er, ec, mat, dir);
                if (recAns.len + 1 < ans.len) {
                    ans.len = recAns.len + 1;
                }
            }
        }

        mat[sr][sc] = 0; // unblock
        return ans;
    }
}
//arr se
class Solution {
    public int shortestPathBinaryMatrix(int[][] grid) {
        int[][] dir = { { -1, 0 }, { -1, 1 }, { 0, 1 }, { 1, 1 }, { 1, 0 }, { 1, -1 }, { 0, -1 }, { -1, -1 } };
        Queue<int[]> ans = new LinkedList<>();
        if (grid[0][0] == 1 || grid[grid.length - 1][grid[0].length - 1] == 1) return -1;
        ans.add(new int[] { 0, 0, 1 });
        grid[0][0] = 1;
        while (ans.size() > 0) {
            int[] arr = ans.poll();
            for (int d = 0; d < dir.length; d++) {
                if (arr[0] == grid.length - 1 && arr[1] == grid[0].length - 1) return arr[2];
                int r = arr[0] + dir[d][0];
                int c = arr[1] + dir[d][1];
                if (r >= 0 && c >= 0 && r <= grid.length - 1 && c <= grid[0].length - 1 && grid[r][c] == 0) {
                    ans.add(new int[] { r, c, arr[2] + 1 });
                    grid[r][c] = 1;
                }
            }
        }
        return -1;
    }
}
