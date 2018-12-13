
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

    	if(coins > 10) {
    		int j = 0;
    		for (int [] botLocs : botInfo){
    			if(j==0) {
    				xEnemy = botLocs[0];
    				yEnemy = botLocs[1];
    				j++;
    			}
    			tempxEnemy = botLocs[0];
    			tempyEnemy = botLocs[1];
    			tempenemyCoins = botLocs[2];
    			if(tempenemyCoins>enemyCoins && tempenemyCoins<coins) {
    				enemyCoins = tempenemyCoins;
    				xEnemy = botLocs[0];
    				yEnemy = botLocs[1];

    			}
    		}

    	}
    	int i = 0;
    	for(int[] coinPlace : coinLocs) {
    		if(i == 0) {
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

    	if(row<xCoin) {
    		return "south";
    	}




    	if(row>xCoin) {
    		return "north";
    	}

    	if (col<yCoin) {
    		return "east";
    	}

    	if(col>yCoin) {
    		return "west";

    	}
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
