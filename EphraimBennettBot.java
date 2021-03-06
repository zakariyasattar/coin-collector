public class EphraimBennettBot implements Bot {

	private int[] cCoin;

	private int[][] botInfo;

    //returns the name of the Bot
    public String getName() {
        return getClass().getName();
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

        int choice = -1;
        this.cCoin = findClosestCoin(coinLocs, row, col);

        this.botInfo = botInfo;

        int[] closestBot = findClosestBot(botInfo, row, col);

        boolean shouldGrab = shouldGrabCoin(closestBot, row, col);

        if (shouldGrab) {
        	//do that
        }

        choice = getDirection(row, col);

        switch(choice) {
            case 0: return "north";
            case 1: return "east";
            case 2: return "south";
            case 3: return "west";
            default: return "none";
        }
    }

    private int getDirection(int row, int col) {

    	int moveLeft = 0;
    	int moveDown = 0;

    	int yDis = row - cCoin[0];
		int xDis = col - cCoin[1];

		if (xDis > 0) {
			moveLeft = 1;
		} else if (xDis < 0) {
			moveLeft = -1;
		}

		if (yDis > 0) {
			moveDown = 1;
		} else if (yDis < 0) {
			moveDown = -1;
		}

		if (moveLeft == 1) {
			return 3;
		} else if (moveLeft == -1) {
			return 1;
		}

		if (moveDown == 1) {
			return 0;
		} else if (moveDown == -1) {
			return 2;
		}
		return -1;

    }

    private int[] findClosestCoin(int[][] coinLocs, int row, int col) {

    	int[] closestCoin = {1000, 1000};

    	for (int[] r : coinLocs) {
    		int x = r[0];
    		int y = r[1];

    		int xDistance = Math.abs(row - x);
    		int yDistance = Math.abs(col - y);
    		int totalDistance = xDistance + yDistance;

    		int closestCoinXDistance = Math.abs(row - closestCoin[0]);
    		int closestCoinYDistance = Math.abs(col - closestCoin[1]);
    		int closestCoinTotalDistance = closestCoinXDistance + closestCoinYDistance;

    		if (totalDistance < closestCoinTotalDistance) {
    			closestCoin = r;
    		}
    	}

		return closestCoin;
	}

    private int[] findClosestBot(int[][] botInfo, int row, int col) {

		return null;
	}

	private boolean shouldGrabCoin(int[] closestBot, int row, int col) {

		boolean isNextToBot = isNextToBot(row, col);

		return false;
	}

	private boolean isNextToBot(int row, int col) {

		return true;
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
