// import java.util.ArrayList;
// import java.util.Arrays;
// import java.util.Comparator;
// import java.util.HashSet;
//
// class Pair {
//     int x;
//     int y;
//
//     // Constructor
// public Pair(int x, int y)
//     {
//         this.x = x;
//         this.y = y;
//     }
// }
// class Compare {
//
//     static void compare(Pair arr[], int n)
//     {
//         // Comparator to sort the pair according to second element
//         Arrays.sort(arr, new Comparator<Pair>() {
//             @Override public int compare(Pair p1, Pair p2)
//             {
//                 return p1.x - p2.x;
//             }
//         });
//
//     }
// }// from Geeksforgeeks
// public class EmilyBot implements Bot {
//
//     //returns the name of the Bot
//     public String getName() {
//         return "EmilyRaucciBot";
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
//     boolean set = false;
//     int trueval = 0;
//     int simulations = 0;
//     boolean isfirst = false;
//     boolean okright = true;
//     boolean okleft = true;
//     boolean okup = true;
//     boolean okdown = true;
//     boolean okright2 = true;
//     boolean okleft2 = true;
//     boolean okup2 = true;
//     boolean okdown2 = true;
//     int currrow;
//     int currcol;
//     int[][] botInfo2;
//     int latest = 0;
//     int currcoins = 0;
//     double prob = .5;
//     double prob2 = .5;
//     double prob3 = .5;
//     int lastcoins = 0;
//     boolean searchmode = false;
//     public int bestcoin(int currind, ArrayList<Integer> rows, ArrayList<Integer> cols, HashSet set, int traveled, int lastrow, int lastcol, ArrayList<Integer> coinvals){
//     	if (set.contains(currind)){
//     		return traveled;
//     	}
//     	set.add(currind);
//         int thesum = -coinvals.get(currind);
//     	for (int i=0; i<rows.size(); i++){
//     		int ro = rows.get(i);
//     		int co = cols.get(i);
//     		int mydist = Math.abs(ro-lastrow)+Math.abs(co-lastcol)+traveled;
//     		boolean good = true;
//     		for (int j=0; j<botInfo2.length; j++){
//     			int botrow = botInfo2[j][0];
//     			int botcol = botInfo2[j][1];
//     			int coins = botInfo2[j][2];
//     			if (botrow-currrow == 0 && botcol-currcol == 0){
//     				continue;
//     			}
//     			if (Math.abs(ro-botrow)+Math.abs(co-botcol) < mydist){
//         				good = false;
//         				break;
//
//     			}
//     		}
//     		if (good){
//     			thesum += bestcoin(i, rows, cols, set, mydist, ro, co, coinvals);
//     		}
//     	}
//     	return thesum;
//
//     }
//     boolean did = false;
//     boolean did2 = false;
//     boolean did3 = false;
//     public String move(int row, int col, int coins, int arenaLen, int[][] botInfo, int[][] coinLocs) {
//
//         okright = true;
//         okleft = true;
//         okup = true;
//         okdown = true;
//         okright2 = true;
//         okleft2 = true;
//         okup2 = true;
//         okdown2 = true;
//         trueval = arenaLen;
//         botInfo2 = botInfo;
//         currrow = row;
//         currcol = col;
//         currcoins = coins;
//         if (did){
//         	if (coins-lastcoins == 0){
//         		prob -= .05;
//         	}else{
//         		prob += .05;
//         		System.out.println("work");
//         	}
//         }
//         if (did2){
//         	if (coins-lastcoins == 0){
//         		prob2 -= .05;
//         	}else{
//         		prob2 += .05;
//         		System.out.println("work2");
//         	}
//         }
//         if (did3){
//         	if (coins-lastcoins == 0){
//         		prob3 -= .05;
//         	}else{
//         		prob3 += .05;
//         		System.out.println("work3");
//         	}
//         }
//         did2 = false;
//         did = false;
//         did3 = false;
//         if (row >= trueval-1) {
//         	okdown = false;
//         }
//         if (col >= trueval-1) {
//         	okright = false;
//         }
//         if (row <= 0) {
//         	okup = false;
//         }
//         if (col <= 0) {
//         	okleft = false;
//         }
//         // move left
//         int leftrow = row;
//         int leftcol = col-1;
//         int rightrow = row;
//         int rightcol = col+1;
//         int uprow = row-1;
//         int upcol = col;
//         int downrow = row+1;
//         int downcol = col;
//         int downs = 0;
//         int ups = 0;
//         int rights = 0;
//         int lefts = 0;
//         double percentbelow = 0;
//         for (int i=0; i<botInfo.length; i++) {
//         	int coin = botInfo[i][2];
//         	int ro = botInfo[i][0];
//         	int co = botInfo[i][1];
//
//         	if (coin < coins) {
//         		percentbelow++;
//         		continue;
//         	}
//         	if (ro-row == 0 && co-col == 0){
//         		continue;
//         	}
//         	if ((Math.abs(ro-leftrow) == 1 && Math.abs(co-leftcol) == 0) || (Math.abs(ro-leftrow) == 0 && Math.abs(co-leftcol) == 1) ||(Math.abs(ro-leftrow) == 0 && Math.abs(co-leftcol) == 0) ) {
//         		okleft2 = false;
//         		lefts++;
//         	}
//         	if ((Math.abs(ro-rightrow) == 1 && Math.abs(co-rightcol) == 0) || (Math.abs(ro-rightrow) == 0 && Math.abs(co-rightcol) == 1) || (Math.abs(ro-rightrow) == 0 && Math.abs(co-rightcol) == 0)) {
//         		okright2 = false;
//         		rights++;
//         	}
//         	if ((Math.abs(ro-uprow) == 1 && Math.abs(co-upcol) == 0) || (Math.abs(ro-uprow) == 0 && Math.abs(co-upcol) == 1) || (Math.abs(ro-uprow) == 0 && Math.abs(co-upcol) == 0)) {
//         		okup2 = false;
//         		ups++;
//         	}
//         	if ((Math.abs(ro-downrow) == 1 && Math.abs(co-downcol) == 0) || (Math.abs(ro-downrow) == 0 && Math.abs(co-downcol) == 1) || (Math.abs(ro-downrow) == 0 && Math.abs(co-downcol) == 0)) {
//         		okdown2 = false;
//         		downs++;
//         	}
//         }
//
//         percentbelow /= botInfo.length;
//
//
//         int bestrow = 0;
//         int bestcol = 0;
//         int closest = 10000;
//         int numones = 0;
//         ArrayList<Integer> goodcoinsr = new ArrayList<Integer>();
//         ArrayList<Integer> goodcoinsc = new ArrayList<Integer>();
//         ArrayList<Integer> coinvals = new ArrayList<Integer>();
//         boolean hasgold = false;
//         // if all are equal choose closest
//         for (int i=0; i<coinLocs.length; i++) {
//         	int ro = coinLocs[i][0];
//         	int co = coinLocs[i][1];
//         	int myloc = Math.abs(ro-row)+Math.abs(co-col);
//         	boolean good = true;
//         	for (int j=0; j<botInfo.length; j++) {
//         		int tro = botInfo[j][0];
//         		int tco = botInfo[j][1];
//         		if (tro == row && tco == col){
//         			continue;
//         		}
//         		// see if this coin is the one with the smallest distance to the bot and also
//         		if (Math.abs(ro-tro)+Math.abs(co-tco) < myloc || Math.abs(ro-tro)+Math.abs(co-tco) == myloc && botInfo[j][2] >= coins) {// || Math.abs(ro-tro)+Math.abs(co-tco) == myloc && botInfo[j][2] >= coins
//
//         				good = false;
//         				break;
//
//         		}
//
//         	}
//
//         	if (good) {
//         		if (myloc < closest) {
//
//         			closest = myloc;
//         			bestrow = ro;
//         			bestcol = co;
//         			hasgold = true;
//         			break;
//
//         		}
//         		if (i == 0){
//     				coinvals.add(5);
//     			}else{
//     				coinvals.add(1);
//     			}
//         		goodcoinsr.add(ro);
//     			goodcoinsc.add(co);
//
//         		numones++;
//         	}
//         }
//
//
//         	if (!hasgold){
//         		int highest = 10000;
//
//             	// find lowest dist with more coins
//             	for (int i=0; i<goodcoinsr.size(); i++){
//             		int ress = bestcoin(i, goodcoinsr, goodcoinsc, new HashSet(), 0, row, col, coinvals);
//             		if (ress <= highest){
//
//                 			bestrow = goodcoinsr.get(i);
//                 			bestcol = goodcoinsc.get(i);
//                 			highest = ress;
//
//             		}
//             	}
//             	//System.out.println(highest);
//         	}
//
//
//
//         //System.out.println(closest);
//
//         if (closest == 10000 && !searchmode) {
//         	for (int x=0; x<1; x++) {
// 	        	int cnt2 = 0;
// 	        	for (int i=0; i<botInfo.length; i++) {
// 	        		int ro = botInfo[i][0];
// 	            	int co = botInfo[i][1];
// 	            	if (Math.abs(ro-row) <= 2 && Math.abs(co-col) <= 2) {
// 	            		cnt2++;
// 	            	}
//
// 	        	}
// 	        	if (cnt2 >= 3) {
// 	        		// right = 1, left = 2, up = 3, down = 4
// 	        		Pair arr[] = new Pair[4];
// 	        		arr[0] = new Pair(rights, 1);
// 	        		arr[1] = new Pair(lefts, 2);
// 	        		arr[2] = new Pair(ups, 3);
// 	        		arr[3] = new Pair(downs, 4);
// 	        		searchmode = true;
// 	        		Compare obj = new Compare();
// 	        		obj.compare(arr, 5);
// 	        		for (int i=0; i<4; i++) {
// 	        			if (arr[i].y == 1) {
// 	        				if (okright && okright2) {
// 	        					bestrow = trueval/2;
//         						bestcol = trueval-1;
// 	        				}else {
// 	        					if (i == 1) {
// 	        						bestrow = trueval/2;
// 	        						bestcol = trueval-1;
// 	        					}
// 	        				}
// 	        			}else if (arr[i].y == 2) {
// 	        				if (okleft && okleft2) {
// 	        					bestrow = trueval/2;
//         						bestcol = 0;
// 	        				}else {
// 	        					if (i == 1) {
// 	        						bestrow = trueval/2;
// 	        						bestcol = 0;
// 	        					}
// 	        				}
// 	        			}else if (arr[i].y == 3) {
// 	        				if (okup && okup2) {
// 	        					bestrow = 0;
//         						bestcol = trueval/2;
// 	        				}else {
// 	        					if (i == 1) {
// 	        						bestrow = 0;
// 	        						bestcol = trueval/2;
// 	        					}
// 	        				}
// 	        			}else {
// 	        				if (okdown && okdown2) {
// 	        					bestrow = trueval-1;
// 	        					bestcol = trueval/2;
// 	        				}else {
// 	        					if (i == 1) {
// 	        						bestrow = trueval-1;
// 	        						bestcol = trueval/2;
// 	        					}
// 	        				}
// 	        			}
// 	        		}
// 	        		break;
// 	        	}
// 	        	int cnt = 0;
// 	        	for (int i=0; i<botInfo.length; i++){
// 	        		int ro = botInfo[i][0];
// 	            	int co = botInfo[i][1];
// 	            	int coin = botInfo[i][2];
// 	        		if (ro-row == 0 && co-col == 0){
// 	            		continue;
// 	            	}
// 	        		if (Math.abs(ro-row)+Math.abs(co-col) == 1 && coin < coins){
// 	        			cnt++;
// 	        		}
// 	        		if (Math.abs(ro-row)+Math.abs(co-col) == 1 && coin > coins){
// 	        			cnt = 0;
// 	        			break;
// 	        		}
// 	        	}
// 	        	if (cnt >= 3){
// 	        		double det = Math.random();
// 	        		if (det <= prob){
// 	        			did = true;
// 	        			lastcoins = coins;
//
// 	        			return "none";
// 	        		}
// 	        	}
// 	        	int highest = 10000;
//
// 	        	// find lowest dist with more coins
// 	        	ArrayList<Integer> allrows = new ArrayList<Integer>();
// 	        	ArrayList<Integer> allcols = new ArrayList<Integer>();
// 	        	ArrayList<Integer> allcoins = new ArrayList<Integer>();
// 	        	for (int i=0; i<coinLocs.length; i++){
// 	        		allrows.add(coinLocs[i][0]);
// 	        		allcols.add(coinLocs[i][1]);
// 	        		if (i == 0){
// 	        			allcoins.add(5);
// 	        		}else{
// 	        			allcoins.add(1);
// 	        		}
// 	        	}
// 	        	for (int i=0; i<allrows.size(); i++){
// 	        		int ress = bestcoin(i, allrows, allcols, new HashSet(), 0, row, col, allcoins);
// 	        		if (ress <= highest){
//
//
//
// 	            			bestrow = allrows.get(i);
// 	            			bestcol = allcols.get(i);
// 	            			highest = ress;
// 	            			//System.out.println(ress+" "+lowestdist);
//
// 	        		}
// 	        	}
//
// 	        	int index = -1;
// 	        	int index2 = -1;
// 	        	for (int i=0; i<botInfo.length; i++){
// 	        		int ro = botInfo[i][0];
// 	            	int co = botInfo[i][1];
// 	            	int coin = botInfo[i][2];
// 	        		if (ro-row == 0 && co-col == 0){
// 	            		continue;
// 	            	}
// 	        		if (Math.abs(ro-row)+Math.abs(co-col) == 1 && coin < coins){
// 	        			int thecnt = 0;
// 	        			int thecnt2 = 0;
// 	        			for (int j=0; j<botInfo.length; j++){
// 	                		int ro2 = botInfo[j][0];
// 	                    	int co2 = botInfo[j][1];
// 	                    	int coin2 = botInfo[j][2];
// 	                		if (ro2-row == 0 && co2-col == 0){
// 	                    		continue;
// 	                    	}
// 	                		if (ro2-ro == 0 && co2-co == 0){
// 	                    		continue;
// 	                    	}
// 	                		if (Math.abs(ro-ro2)+Math.abs(co-co2) == 1 && coin2 >= coin){
// 	                			thecnt++;
// 	                			thecnt2++;
// 	                		}
// 	                		if (Math.abs(ro-row)+Math.abs(co-col) == 1 && coin > coins){
// 	                			thecnt = 0;
//
// 	                		}
// 	        			}
// 	        			if (thecnt == 3){
// 	        				index = i;
//
// 	        			}
// 	        			if (thecnt2 == 3){
// 	        				index2 = i;
//
// 	        			}
// 	        		}
//
// 	        	}
// 	        	if (index != -1){
// 	        		double det = Math.random();
// 	        		if (det <= prob2){
// 	        			did2 = true;
// 	        			lastcoins = coins;
// 	        			return "none";
// 	        		}
// 	        	}
// 	        	if (index2 != -1){
// 	        		double det = Math.random();
// 	        		if (det <= prob3){
// 	        			did3 = true;
// 	        			lastcoins = coins;
// 	        			bestrow = botInfo[index][0];
// 	        			bestcol = botInfo[index][1];
// 	        		}
// 	        	}
//         	}
//         }else {
//         	searchmode = false;
//         }
//         if (searchmode) {
//         	// right = 1, left = 2, up = 3, down = 4
//     		Pair arr[] = new Pair[4];
//     		arr[0] = new Pair(rights, 1);
//     		arr[1] = new Pair(lefts, 2);
//     		arr[2] = new Pair(ups, 3);
//     		arr[3] = new Pair(downs, 4);
//     		Compare obj = new Compare();
//     		obj.compare(arr, 5);
//     		for (int i=0; i<4; i++) {
//     			if (arr[i].y == 1) {
//     				if (okright && okright2) {
//     					bestrow = trueval/2;
// 						bestcol = trueval-1;
//     				}else {
//     					if (i == 1) {
//     						bestrow = trueval/2;
//     						bestcol = trueval-1;
//     					}
//     				}
//     			}else if (arr[i].y == 2) {
//     				if (okleft && okleft2) {
//     					bestrow = trueval/2;
// 						bestcol = 0;
//     				}else {
//     					if (i == 1) {
//     						bestrow = trueval/2;
//     						bestcol = 0;
//     					}
//     				}
//     			}else if (arr[i].y == 3) {
//     				if (okup && okup2) {
//     					bestrow = 0;
// 						bestcol = trueval/2;
//     				}else {
//     					if (i == 1) {
//     						bestrow = 0;
//     						bestcol = trueval/2;
//     					}
//     				}
//     			}else {
//     				if (okdown && okdown2) {
//     					bestrow = trueval-1;
//     					bestcol = trueval/2;
//     				}else {
//     					if (i == 1) {
//     						bestrow = trueval-1;
//     						bestcol = trueval/2;
//     					}
//     				}
//     			}
//     		}
//         }
//         lastcoins = coins;
//         if (bestrow <= row && bestcol <= col) {
//         	if (!(okup && okup2) && !(okleft && okleft2)) {
//
//         			if (okright && okright2){
//
//         				return "east";
//         			}
//         			if (okdown && okdown2){
//
//         				return "south";
//         			}
//
//         			return "none";
//
//         	}else {
//         		if (bestrow == row){
//         			if (okleft && okleft2){
//
//         				return "west";
//         			}else{
//
//         				return "north";
//         			}
//         		}else{
//         			if (okup && okup2){
//
//         				return "north";
//         			}else{
//
//         				return "west";
//         			}
//         		}
//         	}
//
//         }else if (bestrow <= row && bestcol >= col) {
//         	if (!(okup && okup2) && !(okright && okright2)) {
//
//         		if (okleft && okleft2){
//
//     				return "west";
//     			}
//     			if (okdown && okdown2){
//
//     				return "south";
//     			}
//
//     			return "none";
//
//         	}else {
//         		if (bestrow == row){
//         			if (okright && okright2){
//
//         				return "east";
//         			}else{
//
//         				return "north";
//         			}
//         		}else{
//         			if (okup && okup2){
//
//         				return "north";
//         			}else{
//
//         				return "east";
//         			}
//         		}
//         	}
//
//         }else if (bestrow >= row && bestcol <= col) {
//         	if (!(okdown && okdown2) && !(okright && okright2)) {
//
//         		if (okleft && okleft2){
//
//     				return "west";
//     			}
//     			if (okup && okup2){
//
//     				return "north";
//     			}
//
//     			return "none";
//         	}else {
//         		if (bestrow == row){
//         			if (okright && okright2){
//
//         				return "east";
//         			}else{
//
//         				return "south";
//         			}
//         		}else{
//         			if (okdown && okdown2){
//
//         				return "south";
//         			}else{
//
//         				return "east";
//         			}
//         		}
//         	}
//
//         }else if (bestrow >= row && bestcol >= col) {
//         	if (!(okdown && okdown2) && !(okleft && okleft2)) {
//
//         		if (okright && okright2){
//
//     				return "east";
//     			}
//     			if (okup && okup2){
//
//     				return "north";
//     			}
//
//     			return "none";
//
//         	}else {
//         		if (bestrow == row){
//         			if (okleft && okleft2){
//
//         				return "west";
//         			}else{
//
//         				return "south";
//         			}
//         		}else{
//         			if (okdown && okdown2){
//
//         				return "south";
//         			}else{
//
//         				return "west";
//         			}
//         		}
//         	}
//
//         }else {
//
//         	return "none";
//
//         }
//     }
//
//     //informs the player they died in one simulation of the game
//     //@param moves      the moves it took to die
//     //@param coins      the number of coins the Bot had when it died
//     //@param reason     why you died
//     public void died(int moves, int coins, String reason) {
//     	/*
//     	System.out.println(okright2 && okright);
//     	System.out.println(okleft2 && okleft);
//     	System.out.println(okdown2 && okdown);
//     	System.out.println(okup2 && okup);
//     	System.out.println(latest);
//     	System.out.println(reason);*/
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
//     	simulations++;
//
//     }
//
// }
