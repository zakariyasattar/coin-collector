// public class CashBot_ChristianMontgomery implements Bot {
//
//     //returns the name of the Bot
//     public String getName() {
//         return getClass().getName();
//     }
//
//     //returns the next direction the Bot should move
//     //@param row        the row that the bot is in
//     //@param col        the col that the bot is in
//     //@param coins        the number of coins the bot has
//     //@param arenaLen    the rows and columns in the square arena
//     //@param botInfo    2D array of bot info
//     //            Each row contains 3 pieces of info about a bot
//     //                The first two values will be the row and col position
//     //                The last value will be the number of coins the bot has
//     //            Ex: [[0,5,4],[4,7,1],[7,4,12]]
//     //                The first row means there is a bot at row 0, col 5, with 4 coins
//     //                Your own bot will NOT be in this array
//     //@param coinLocs    2D array of coin locations.  The first location will always be the Gold coin
//     //            Ex: [[0, 1],[5,4],[3,2],[6,1],[7,2]]
//     //                The first row means there is a Gold coin at row 0, column 1
//     //                The second row means there is a Silver coin at row 5, column 4
//     //                The last three rows are for three more Silver coins
//     //@return        One of "north", "east", "south", "west", "none"
//
//     public int distance(int row, int col, int[][] coinLocs, int coin) {
//     	return Math.abs(coinLocs[coin][0] - row) + Math.abs(coinLocs[coin][1] - col);
//     }
//     public int botDistance(int row, int col, int[][] botInfo, int bot) {
//     	return Math.abs(botInfo[bot][0] - row) + Math.abs(botInfo[bot][1] - col);
//     }
//
//     public int getCoin(int row, int col, int[][] coinLocs, int[][] botInfo, int coins) {
//     	int closest = 0;
//     	for(int i = 0; i<coinLocs.length; i++) {
//     		if((distance(row, col, coinLocs, i) < distance(row, col, coinLocs, closest)) || (distance(row, col, coinLocs, i) == distance(row, col, coinLocs, closest) && i == 0)) {
//     			closest = i;
//     		}
//     	}
//     	int closestBot = 0;
//     	Boolean foundBot = false;
//     	for(int i = 0; i<botInfo.length; i++) {
//     		if((botDistance(row, col, botInfo, i) < botDistance(row, col, botInfo, closestBot)) && botInfo[i][2] < coins) {
//     			closestBot = i;
//     			foundBot = true;
//     		}
//     	}
//     	if(botDistance(row, col, botInfo, closestBot) - (coins - botInfo[closestBot][2])< distance(row, col, coinLocs, closest) && foundBot) {
//         	return closestBot;
//     	} else {
//     		return closest;
//     	}
//     }
//
//     public String move(int row, int col, int coins, int arenaLen, int[][] botInfo, int[][] coinLocs) {
//
//     	if(coinLocs[getCoin(row, col, coinLocs, botInfo, coins)][0] < row) {
//         	return "north";
//         } else if(coinLocs[getCoin(row, col, coinLocs, botInfo, coins)][0] > row) {
//         	return "south";
//         } else if (coinLocs[getCoin(row, col, coinLocs, botInfo, coins)][1] > col) {
//         	return "east";
//         } else if(coinLocs[getCoin(row, col, coinLocs, botInfo, coins)][1] < col) {
//         	return "west";
//         }
//
//     	int choice = (int)(Math.random()*5);
//         switch(choice) {
//             case 0:
// 	        	if(row != 0) {
// 	    			return "north";
// 	    		} else {
// 	    			return "south";
// 	    		}
//             case 1:
//             	if(col != 0) {
//         			return "east";
//         		} else {
//         			return "west";
//         		}
//             case 2:
//             	if(row != arenaLen) {
//             		return "south";
//             	} else {
//             		return "north";
//             	}
//             case 3:
//         		if(col != arenaLen) {
//         			return "west";
//         		} else {
//         			return "east";
//         		}
//             default: return "none";
//         }
//     }
//
//     //informs the player they died in one simulation of the game
//     //@param moves      the moves it took to die
//     //@param coins      the number of coins the Bot had when it died
//     //@param reason     why you died
//     public void died(int moves, int coins, String reason) {
//
//     }
//
//     //informs the player they died in the current simulation
//     //@param moves      the moves it took to die
//     //@param coins      the number of coins the Bot had when it died
//     public void won(int moves, int coins) {
//
//     }
//
//     //informs the player that we are at the beginning of a new simulation
//     public void newSimulation() {
//
//     }
//
// }
