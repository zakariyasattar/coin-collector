public class OmerBot implements Bot {

    //returns the name of the Bot
    public String getName() {
        return getClass().getName();
    }

    public int distanceFromCoin(int row, int col, int coinY, int coinX) {
    	int ver = row - coinY;
    	int hor = col - coinX;
    	int distance = Math.abs(hor + ver);
    	return distance;
    }

    public int[] getClosestCoin(int row, int col, int[][] coinLocs) {
    	int[] closestCoin = new int[3];
    	for(int i = 0; i < coinLocs.length; i++) {
    		int curDist = distanceFromCoin(row, col, coinLocs[i][0], coinLocs[i][1]);
    		if(closestCoin[2] == 0 || curDist < closestCoin[2] && (closestCoin[0] != coinLocs[i][0] && closestCoin[1] != coinLocs[i][1])) {
    			closestCoin[0] = coinLocs[i][0];
    			closestCoin[1] = coinLocs[i][1];
    			closestCoin[2] = curDist;
    		}
    	}
    	return closestCoin;
    }

    //returns the next direction the Bot should move
    //@param row        the row that the bot is in
    //@param col        the col that the bot is in
    //@param coins        the number of coins the bot has
    //@param arenaLen    the rows and columns in the square arena
    //@param botInfo    2D array of bot info
    //            Each row contains 3 pieces of info about a bot
    //                The first two values will be the row and col position
    //                The last value will be the number of coins the bot has
    //            Ex: [[0,5,4],[4,7,1],[7,4,12]]
    //                The first row means there is a bot at row 0, col 5, with 4 coins
    //                Your own bot will NOT be in this array
    //@param coinLocs    2D array of coin locations.  The first location will always be the Gold coin
    //            Ex: [[0, 1],[5,4],[3,2],[6,1],[7,2]]
    //                The first row means there is a Gold coin at row 0, column 1
    //                The second row means there is a Silver coin at row 5, column 4
    //                The last three rows are for three more Silver coins
    //@return        One of "north", "east", "south", "west", "none"
    public String move(int row, int col, int coins, int arenaLen, int[][] botInfo, int[][] coinLocs) {
    	/*
 		Get position of all coins
    	Get position of all players
    	Get distance from each player
    	Get distance from all coins
    	---
    	Choose closest coin (done)
    	Check each players distance from said coin (done)
    	If closest, move toward that coin (done)
    	If tied, get coins of player (done)
    		If more coins, move towards coin (done)
    		If less coins, do nothing (done)
    	Repeat for all coins (done)
    	If not closets, and distance != 1 from player, don't move (done)
    	If not closets, and distance == 1 from player, and less coins, move away from player
    	*/
    	String up = "north";
    	String down = "south";
    	String left = "west";
    	String right = "east";
    	String none = "none";

    	//Find the closest coin
    	int[] closestCoin = new int[3];
    	int[] closestEnemyPos = new int[2];
    	boolean enemyFree = false;
    	int distanceFromClosestEnemy = 9999999;
    	int coinCount = 0;

    	while(!enemyFree && coinCount < 5) {
    		closestCoin = getClosestCoin(row, col, coinLocs);

        	//See closest players to closest coin
        	for(int i = 0; i < botInfo.length; i++) {
        		int enemyDist = distanceFromCoin(botInfo[i][0], botInfo[i][1], closestCoin[0], closestCoin[1]);
        		int tempEnemyDist = distanceFromCoin(botInfo[i][0], botInfo[i][1], row, col);
        		if(distanceFromClosestEnemy > tempEnemyDist) {
        			distanceFromClosestEnemy = tempEnemyDist;
        		}
        		if(enemyDist <= closestCoin[2] && botInfo[i][2] >= coins) {
        			enemyFree = false;
        		} else {
        			enemyFree = true;
        			closestEnemyPos[0] = botInfo[i][0];
        			closestEnemyPos[1] = botInfo[i][1];
        		}
        	}

        	coinCount++;
    	}

    	if(coinCount == 5 && distanceFromClosestEnemy > 1) {
    		return none;
    	}
    	else if(!enemyFree) {
    		if(closestEnemyPos[0] > row && row > 0) {
    			return up;
    		}
    		else if(closestEnemyPos[0] < row && row < arenaLen-1) {
    			return down;
    		}
    		else if(closestEnemyPos[1] > col && col > 0) {
    			return left;
    		}
    		else if(closestEnemyPos[1] < col && col < arenaLen-1){
    			return right;
    		}
    		else {
    			return none;
    		}
    	}
    	else if(enemyFree) {
    		if(row > closestCoin[0]) {
    			return up;
    		}
    		else if(row < closestCoin[0]) {
    			return down;
    		}
    		else if(col > closestCoin[1]) {
    			return right;
    		}
    		else if(col < closestCoin[1]) {
    			return left;
    		}
    		else {
    			return none;
    		}
    	}

    	return none;
    }

    //informs the player they died in one simulation of the game
    //@param moves      the moves it took to die
    //@param coins      the number of coins the Bot had when it died
    //@param reason     why you died
    public void died(int moves, int coins, String reason) {

    }

    //informs the player they died in the current simulation
    //@param moves      the moves it took to die
    //@param coins      the number of coins the Bot had when it died
    public void won(int moves, int coins) {

    }

    //informs the player that we are at the beginning of a new simulation
    public void newSimulation() {

    }

}
