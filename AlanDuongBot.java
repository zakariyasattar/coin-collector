


public class AlanDuongBot implements Bot {

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
	        int choice = (int)(Math.random()*5);
	        int maxCoins = 0;
	        boolean goat = false;
	        for(int i = 0; i<botInfo.length; i++) {
	        	int selCoins = botInfo [i][2];
	        	if(selCoins > maxCoins) {
	        		maxCoins = selCoins;
	        	}
	        }
	        if(coins>10) {
	        	goat = true;
	        	choice = attack(row, col, coins, arenaLen, botInfo, coinLocs);
	        }
	        else if(coins < 5000) {
	        	choice = collectAllCoins(row, col, coins, arenaLen, botInfo, coinLocs);
	        }
	        switch(choice) {
	            case 0: return "north";
	            case 1: return "east";
	            case 2: return "south";
	            case 3: return "west";
	            default: return "none";
	        }

	        //board is 30x30
	    }

	    //informs the player they died in one simulation of the game
	    //@param moves      the moves it took to die
	    //@param coins      the number of coins the Bot had when it died
	    //@param reason     why you died
	    public int collectAllCoins(int row, int col, int coins, int arenaLen, int[][] botInfo, int[][] coinLocs) {
	    	int choice = 0;
	    	int desiredCoin = 0;
	        double minDist = 100;

	        for(int i = 0; i< 5; i++) {
	        	int colCurr = coinLocs[i][1];
	        	int rowCurr = coinLocs[i][0];
	        	int movesToCoin = Math.abs(colCurr-col)+Math.abs(rowCurr-row);
	        	if(movesToCoin<minDist) {
	        		minDist = movesToCoin;
	        		desiredCoin = i;
	        	}
	        }
	        int columnDesiredCoin = coinLocs[desiredCoin][1];
	        int rowDesiredCoin = coinLocs [desiredCoin][0];
	        if(columnDesiredCoin>col) {
	        	choice = 1;
	        }
	        else if(col>columnDesiredCoin) {
	        	choice = 3;
	        }
	        else if(col == columnDesiredCoin) {
	        	if(rowDesiredCoin>row) {
		        	choice = 2;
		        }
		        else if(rowDesiredCoin<row) {
		        	choice = 0;
		        }
	        }
	    	return choice;
	    }
	    public int collectGold(int row, int col, int coins, int arenaLen, int[][] botInfo, int [][] coinLocs) {
	    	int choice = (int)(Math.random()*5);
			int rowCoin = coinLocs[0][0];
			int columnCoin = coinLocs[0][1];
			if(columnCoin > col) {
				choice = 1;
			}
			else if(col>columnCoin) {
				choice = 3;
			}
			else if (col == columnCoin) {
				if(rowCoin > row) {
					choice = 2;
				}
				else if (rowCoin<row) {
					choice = 0;
				}
			}
			return choice;
	    }
	    public int attack(int row, int col, int coins, int arenaLen, int[][] botInfo, int[][] coinLocs) {
	    	int choice = 0;
	    	int minDistanceToBot = 100;
		        int coinsNearestBot = 0;
		        int indexOfNearestBot = 0;
		        int colSelectedBot = 0;
		        int rowSelectedBot = 0;
		        for(int i = 0; i < botInfo.length; i++) {
		        	int currCol = botInfo[i][1];
		        	int currRow = botInfo[i][0];
		        	int distanceToAlan = Math.abs(currCol-col)+Math.abs(currRow-row);
		        	if(distanceToAlan<minDistanceToBot) {
		        		minDistanceToBot = distanceToAlan;
		        		indexOfNearestBot = i;
		        		coinsNearestBot = botInfo[i][2];
		        		colSelectedBot = botInfo[i][1];
		        		rowSelectedBot = botInfo[i][0];
		        	}
		        	if(coins>coinsNearestBot) {
		        		 if(colSelectedBot>col) {
		     	        	choice = 1;
		     	        }
		     	        else if(col>colSelectedBot) {
		     	        	choice = 3;
		     	        }
		     	        else if(col == colSelectedBot) {
		     	        	if(rowSelectedBot>row) {
		     		        	choice = 2;
		     		        }
		     		        else if(rowSelectedBot<row) {
		     		        	choice = 0;
		     		        }
		     	        }
		        	}
		        }
	    	return choice;
	    }
	    public int evade (int row, int col, int coins, int arenaLen, int[][] botInfo, int[][] coinLocs) {
	    	int choice = 0;
	    	int minDistanceToBot = 100;
		        int coinsNearestBot = 0;
		        int indexOfNearestBot = 0;
		        int colSelectedBot = 0;
		        int rowSelectedBot = 0;
		        for(int i = 0; i < botInfo.length; i++) {
		        	int currCol = botInfo[i][1];
		        	int currRow = botInfo[i][0];
		        	int distanceToAlan = Math.abs(currCol-col)+Math.abs(currRow-row);
		        	if(distanceToAlan<minDistanceToBot) {
		        		minDistanceToBot = distanceToAlan;
		        		indexOfNearestBot = i;
		        		coinsNearestBot = botInfo[i][2];
		        		colSelectedBot = botInfo[i][1];
		        		rowSelectedBot = botInfo[i][0];
		        	}
		        	if(coins<coinsNearestBot) {
		        		 if(colSelectedBot>col) {
		     	        	choice = 3;
		     	        }
		     	        else if(col>colSelectedBot) {
		     	        	choice = 1;
		     	        }
		     	        else if(col == colSelectedBot) {
		     	        	if(rowSelectedBot>row) {
		     		        	choice = 0;
		     		        }
		     		        else if(rowSelectedBot<row) {
		     		        	choice = 2;
		     		        }
		     	        }
		        	}
		        }
	    	return choice;
	    }
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
