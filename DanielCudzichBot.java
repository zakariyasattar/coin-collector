public class DanielCudzichBot implements Bot {

	public String getName() {
        return getClass().getName();
    }

	//ok, so this is what Fahrenfatherfigure said about the AI for it to function
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
		String movement = "none";

		//establishing how to detect boundaries: DEFUNCT
		/*String[] currentDirections = {"north", "east", "south", "west"};
		int index = 0;
		if(
		(row == 0 && currentDirections[index].equals("north"))
		|| (col == 10 && currentDirections[index].equals("east"))
		|| (row == 10 && currentDirections[index].equals("south"))
		|| (col == 0 && currentDirections[index].equals("west"))) {
			index++;
			if(index >= 4) {
				index = 0;
			}
		}
		System.out.println(arenaLen);
		System.out.println(col);
		return currentDirections[index];*/

		//just get to the nearest coin
		int[] goal = new int[2];
		goal = getTarget(row, col, coinLocs, arenaLen);
		if(col - goal[1] > 0) {
			movement = "west";
		}
		if (col - goal[1] < 0) {
			movement = "east";
		}
		if (row - goal[0] > 0) {
			movement = "north";
		}
		if (row - goal[0] < 0) {
			movement = "south";
		}

		if(checkCoin(row, col, coinLocs, goal, botInfo, coins) == true) {
			return "none";
		}

		//is there a silver coin right next to me? if so, GRAB IT
		//provided that no one else is next to it
		for(int i = 1; i < coinLocs.length; i++) {
			if((Math.abs(col - coinLocs[i][1]) == 1 && row == coinLocs[i][0]) || (Math.abs(row - coinLocs[i][0]) == 1 && col == coinLocs[i][1])) {
				if(checkCoin(row, col, coinLocs, goal, botInfo, coins) == false) {
					//now moveate
					if(col - coinLocs[i][1] == 1) {
						return "west";
					}
					if(col - coinLocs[i][1] == -1) {
						return "east";
					}
					if(row - coinLocs[i][0] == 1) {
						return "north";
					}
					if(row - coinLocs[i][0] == -1) {
						return "south";
					}
				}
			}
		}

		//general enemy avoidance - delete in test versions
		//I think this is leading to cases where I'm trying to check outside of bounds for an enemy by a coin at the corner
		//scroll through the list of bots first
		for(int i = 0; i < botInfo.length; i++) {
			if(botInfo[i][2] >= coins) {
				//check where he is relative to me
				int colDistance = col-botInfo[i][1];
				int rowDistance = row-botInfo[i][0];
				if(movement == "west" && col + 2 == botInfo[i][1] && row == botInfo[i][0] && botInfo[i][2] >= coins) {
					System.out.println("wary - stopped");
					return "none";
				}
				if(movement == "east" && col - 2 == botInfo[i][1] && row == botInfo[i][0] && botInfo[i][2] >= coins) {
					System.out.println("wary - stopped");
					return "none";
				}
				if(movement == "north" && row - 2 == botInfo[i][0] && col == botInfo[i][1] && botInfo[i][2] >= coins) {
					System.out.println("wary - stopped");
					return "none";
				}
				if(movement == "south" && row + 2 == botInfo[i][0] && col == botInfo[i][1] && botInfo[i][2] >= coins) {
					System.out.println("wary - stopped");
					return "none";
				}
				//now diagonal cases
				if(movement == "west" && col +1 == botInfo[i][1] && Math.abs(rowDistance) == 1) {
					System.out.println("wary - stopped");
					return "none";
				}
				if(movement == "east" && col -1 == botInfo[i][1] && Math.abs(rowDistance) == 1) {
					System.out.println("wary - stopped");
					return "none";
				}
				if(movement == "north" && row -1 == botInfo[i][0] && Math.abs(colDistance) == 1) {
					System.out.println("wary - stopped");
					return "none";
				}
				if(movement == "south" && row + 1 == botInfo[i][0] && Math.abs(colDistance) == 1) {
					System.out.println("wary - stopped");
					return "none";
				}
				//UNCOMMENT IF THIS DOES NOT WORK IN COMPETITION - SUBMIT IN CLASS
				//other ideas for winning:
				// reduce distance to satisfy gold coin vs. silver coin dispute
				//if i find myself adjacent to a silver coin, and there's no one else to take it, go for it
				//what else dammit
			}
		}

		return movement;
    }

	public int[] getTarget(int row, int col, int[][] coinLocs, int arenaLen) {
		int[] target = new int[2];
		double[] distances = new double[coinLocs.length];
		for(int i = 0; i < coinLocs.length; i++) {
			distances[i] = Math.sqrt((col-coinLocs[i][1])*(col-coinLocs[i][1]) + (row-coinLocs[i][0])*(row-coinLocs[i][0]));
		}
		double minimum = arenaLen*arenaLen;
		int indexDistance = 0;
		for(int i = 0; i < distances.length; i++) {
			if(distances[i] < minimum) {
				minimum = distances[i];
				indexDistance = i;
			}
		}
		//try and check if the distance to a coin is the same as a gold coin, prioritize that
		if(distances[0] == minimum) {
			target = coinLocs[0];
			return target;
		}
		//check if distance to a gold coin is within reason to go for that instead of the closest silver coin
		if(distances[0] - minimum < 6) {
			target = coinLocs[0];
			return target;
		}
		target[0] = coinLocs[indexDistance][0];
		target[1] = coinLocs[indexDistance][1];
		return target;
	}

	public boolean checkCoin(int row, int col, int[][] coinLocs, int[] goal, int[][] botInfo, int coins) {
		//enemy detection
				if((Math.abs(goal[1]-col) == 1 && Math.abs(goal[0]-row) == 0) || (Math.abs(goal[1]-col) == 0 && Math.abs(goal[0]-row) == 1)) {
					for(int i = 0; i < botInfo.length; i++) {
						//is he next to me at the coin? CHECK HOW MUCH HE HAS TOO
						if(col-goal[1]==1 && botInfo[i][1]-goal[1]==-1 && botInfo[i][0] == goal[0] && botInfo[i][2] >= coins) {
							System.out.println("stopped at coin");
							return true;
						}
						if(col-goal[1]==-1 && botInfo[i][1]-goal[1]==1 && botInfo[i][0] == goal[0] && botInfo[i][2] >= coins) {
							System.out.println("stopped at coin");
							return true;
						}
						if(row-goal[0]==1 && botInfo[i][0]-goal[0]==-1 && botInfo[i][1] == goal[1] && botInfo[i][2] >= coins) {
							System.out.println("stopped at coin");
							return true;
						}
						if(row-goal[0]==-1 && botInfo[i][0]-goal[0]==1 && botInfo[i][1] == goal[1] && botInfo[i][2] >= coins) {
							System.out.println("stopped at coin");
							return true;
						} //I can only do none or there will be cases where I kill myself at the boundary
					}
				}
			return false;
	}



	//informs the player they died in one simulation of the game
    //@param moves      the moves it took to die
    //@param coins      the number of coins the Bot had when it died
    //@param reason     why you died
	public void died(int moves, int coins, String reason) {
		//have reason for death printed out at each death
		System.out.println(reason);
    }

	//informs the player they died in the current simulation, but this description is confusing
    //@param moves      the moves it took to die
    //@param coins      the number of coins the Bot had when it died
	public void won(int moves, int coins) {
	    //print statement displaying strategies used?
		System.out.println("i win");
    }

	//informs the player that we are at the beginning of a new simulation
    public void newSimulation() {

    }

}
