public class Ex14
{  

	// Runtime: O(logn + k), k is the number of instances of x in a
	// Memory: O(1) - because we used only contstant number of variables
	public static int count(int[] a, int x) {
		int count = 0;

		int ind = binarySearch(a, x);
		if(ind == -1) // not found
			return count;

		// Else - count the number of times in the array
		// We do 2 loops because 'ind' should x, but the middle one
		boolean stop = false; // helps to stop the loop when we are not on x anymore
		
		// First loop goes left from the founded index
		for(int i = ind; i >= 0 && !stop; i--) {
			if(a[i] == x)
				count++;
			else
				stop = true;
		}
		
		stop = false;
		// Second loop goes right from the founded index
		for(int i = ind+1; i < a.length && !stop; i++) {
			if(a[i] == x)
				count++;
			else
				stop = true;
		}

		return count;
	}

	// This function is BinarySearch
	// It returns the index of 'x' in 'a', -1 if not found
	// Runtime: O(logn) - binary search
	// Memory: O(1) - because we used only contstant number of variables
	public static int binarySearch(int [] a, int x){
		int left = 0;
		int right = a.length- 1;
		int middle;

		while (left <= right) {
			middle = ((left + right)/2);
			if (a[middle] == x)
				return middle;

			if (a[middle] < x)
				left = middle + 1;
			else
				right = middle-1;

		}
		return -1;

	}

	// Runtime: O(n) - we run over string of length n only one time, and the rest is code with O(1) runtime
	// Memory: O(1) - we only use 2 variables, constant number of additional memory
	public static int alternating (String s){
		int countIf1InStart = 0; // number of swaps if s should be 101010...
		int countIf0InStart = 0; // number of swaps if s should be 010101...

		// Run over the string to check num of switches
		for(int i = 0; i < s.length(); i++) {
			if(i%2 == 0 && s.charAt(i) != '1') // if even index and we have there '0' - should switch to get 1010..
				countIf1InStart++;
			if(i%2 == 0 && s.charAt(i) != '0') // if even index and we have there '1' - should switch to get 0101..
				countIf0InStart++;
			if(i%2 == 1 && s.charAt(i) != '0') // if odd index and we have there '1' - should switch to get 1010..
				countIf1InStart++;
			if(i%2 == 1 && s.charAt(i) != '1') // if odd index and we have there '0' - should switch 0101...
				countIf0InStart++;
		}
		
		// Divide by 2 because we alternate bits, and we counted number of flips
		countIf1InStart = countIf1InStart/2;
		countIf0InStart = countIf0InStart/2;

		// return the minimal number of switches
		if(countIf1InStart < countIf0InStart)
			return countIf1InStart;
		else
			return countIf0InStart;
	}

	public static boolean  isWay(int []a){
		int low=0;
		int[] flags = new int[a.length]; // flags[i] == 0 means we didn't visit in a[i] yet, flags == 1 means we visited
		return isWayHelp(a, flags, low);
	}

	public static boolean isWayHelp(int [] a, int[] flags, int i){
		// If we arrived the last cell in array - success
		if(i==a.length-1)
			return true;

		// If we are not in legal index - false
		if(i >= a.length || i < 0)
			return false;

		flags[i] = 1; // visited

		// Check 2 options in recursion
		if (i + a[i] < a.length && flags[i + a[i]] == 0 && isWayHelp( a, flags, a[i] + i ))
			return true;
		else if(i - a[i] >= 0 && flags[i - a[i]] == 0 && isWayHelp( a, flags, i - a[i] ))
			return true;
		else
			return false;

	}

	public static void printPath(int [][]a){
		int x=0, y =0 ; 
		String path = printPathHelp(a, x, y);
		System.out.println(path);
	}

	public static String printPathHelp(int a [][], int x  , int y){
		if(!isLegalIndices(a, x, y))
			return "";

		// Current cell
		String current = "("+x+","+y+")";

		// If current cell is givaa - return the current cell
		if(IsGivaa(a, x, y) == true)
			return current;

		String result = "";

		// Check all 4 options to go, if needed
		
		// Go down
		if(isLegalIndices(a, x+1, y) && a[x+1][y] > a[x][y]) {
			result = printPathHelp(a, x+1, y);
			if(!result.equals("")) {
				return current + result;
			}
		}

		// Go up
		if(isLegalIndices(a, x-1, y) && a[x-1][y] > a[x][y]) {
			result = printPathHelp(a, x-1, y);
			if(!result.equals("")) {
				return current + result;
			}
		}

		// Go right
		if(isLegalIndices(a, x, y+1) && a[x][y+1] > a[x][y]) {
			result = printPathHelp(a, x, y+1);
			if(!result.equals("")) {
				return current + result;
			}
		}

		// Go left
		if(isLegalIndices(a, x, y-1) && a[x][y-1] > a[x][y]) {
			result = printPathHelp(a, x, y-1);
			if(!result.equals("")) {
				return current + result;
			}
		}
		
		return "";
	}

	public static boolean IsGivaa(int[][] a, int x, int y) {
		// Check if x,y bigger then all 4 adjacent cells (if they exist)
		
		if(x + 1 < a.length && y < a[0].length && x + 1 >= 0 && y >= 0)
			if(a[x][y] <= a[x+1][y])
				return false;

		if(x - 1 < a.length && y < a[0].length && x - 1 >= 0 && y >= 0)
			if(a[x][y] <= a[x-1][y])
				return false;

		if(x < a.length && y + 1 < a[0].length && x >= 0 && y + 1 >= 0)
			if(a[x][y] <= a[x][y+1])
				return false;

		if(x < a.length && y - 1 < a[0].length && x >= 0 && y - 1 >= 0)
			if(a[x][y] <= a[x][y-1])
				return false;

		return true;
	}    

	public static boolean isLegalIndices(int[][] a, int x, int y) {
		// Check if legal index x,y
		if(x < 0 || x >= a.length || y < 0 || y >= a[0].length)
			return false;

		return true;
	}
}