public class MoPatelBot implements Bot {
	private int xCoin;
	private int yCoin;
	private int tempxCoin;
	private int tempyCoin;
	private int xEnemy;
	private int yEnemy;
	private int tempxEnemy;
	private int tempyEnemy;
	private int enemyCoins;
	private int tempenemyCoins;
	private int bigbotX;
	private int bigbotY;
	private int hypeCoin;
	private int temphypeCoin;
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

    	/*for(int[] botLocs1 : botInfo) {
    		if(botLocs1[2]>coins) {
    			bigbotX = botLocs1[0];
    			bigbotY = botLocs1[1];
    			if(row<bigbotX && ((bigbotX-row)==2)) {
    				return "north";
    			}
    			if(row>bigbotX && ((bigbotX-row)==-2)) {
    				return "south";
    			}
    			if(col<bigbotX && ((bigbotX-row)==2)) {
    				return "west";
    			}
    			if(col>bigbotX && ((bigbotX-row)==-2)) {
    				return "east";
    			}
    		}
    	}*/

    	//CODE TO ATTACK WEAKER BOT IN 1V1


    	if(botInfo.length<=2) {
    		xEnemy = botInfo[1][0];
    		yEnemy = botInfo[1][1];
    		enemyCoins = botInfo[1][2];
    		if(enemyCoins<coins && botInfo.length<=2){
    		if(row<xEnemy && row!=arenaLen) {
    			return "south";
    		}
    		if(row>xEnemy && row!=0) {
    			return "north";
    		}
    		if(col<yEnemy && col!=arenaLen) {
    			return "east";
    		}
    		if(col>yEnemy && col!=0) {
    			return "west";
    		}
    		}

    	}

    	//current code for closes coin
    	int i = 0;
    	if(botInfo.length>2 || (botInfo.length<=2 && coins<=enemyCoins)) {
    		for(int[] coinPlace : coinLocs) {
    			if(i==0) {
    				xCoin = Math.abs(coinPlace[0]-row);
    				yCoin = Math.abs(coinPlace[1]-col);
    				hypeCoin = (xCoin*2)+(yCoin*2);
    				i++;
    			}
    			tempxCoin = Math.abs(coinPlace[0]-row);
				tempyCoin = Math.abs(coinPlace[1]-col);
				temphypeCoin = (tempxCoin*2)+(tempyCoin*2);
				if(temphypeCoin<=hypeCoin) {
	    			xCoin = coinPlace[0];
	    			yCoin = coinPlace[1];
	    		}
    		}

    		if(row<xCoin && row!=arenaLen) {
    			return "south";
    		}
    		if(row>xCoin && row!=0) {
    			return "north";
    		}
    		if(col<yCoin && col!=arenaLen) {
    			return "east";
    		}
    		if(col>yCoin && col!=0) {
    			return "west";
    		}

    	}
    /*	if(botInfo.length>1) {
    	int i1 = 0;
    	for(int[] coinPlace : coinLocs) {
    		if(i1 == 0) {
    		xCoin = coinPlace[0];
    		yCoin = coinPlace[1];
    		i++;
    		}
    		tempxCoin = coinPlace[0];
    		tempyCoin = coinPlace[1];
    		if(tempxCoin<xCoin && tempyCoin<yCoin) {
    			xCoin = tempxCoin;
    			yCoin = tempyCoin;
    		}
    	}

    	if(row<xCoin && row!=arenaLen) {
    		return "south";
    	}

    	if(row>xCoin && row!=0) {
    		return "north";
    	}

    	if (col<yCoin && col!=arenaLen) {
    		return "east";
    	}

    	if(col>yCoin && col!=0) {
    		return "west";

    	}
    	}*/

            return "none";
            //return "east";
            //return "south";
            //return "west";
            //return "none";

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
