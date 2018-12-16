import java.util.ArrayList;


public class AIBot implements Bot{

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "AlushBenitez";
	}

	@Override
	public String move(int row, int col, int coins, int arenaLen, int[][] botInfo, int[][] coinLocs) {
		// TODO Auto-generated method stub
		ArrayList<String> options = new ArrayList<>();
		options.add("north");
		options.add("south");
		options.add("east");
		options.add("west");
		
		ArrayList<int[]> botsWithMoreCoins = new ArrayList<>();
		for(int i = 0; i < botInfo.length; i++) {
			if(botInfo[i][0] > coins) {
				botsWithMoreCoins.add(botInfo[i]);
			}
		}
		
		for(int i = 0; i < botsWithMoreCoins.size(); i++) {
			if(row + 1 > arenaLen || row + 1 > botsWithMoreCoins.get(i)[1]) {
				options.remove("south");
			}
			
			if(row - 1 < arenaLen || row - 1 < botsWithMoreCoins.get(i)[1]) {
				options.remove("north");
			}
			
			if(col - 1 < arenaLen || row - 1 < botsWithMoreCoins.get(i)[1]) {
				options.remove("west");
			}
			
			if(col + 1 > arenaLen || col + 1 > botsWithMoreCoins.get(i)[2]) {
				options.remove("east");
			}
		}
		
		Boolean greatestCoinCount = true;
		for(int i = 0; i < botInfo.length; i++) {
			if(botInfo[i][0] != row && botInfo[i][1] != col) {
				if(coins < (botInfo[i][2] + 6)) {
					greatestCoinCount = false;
					break;
				}
			}//go aloosh!!!
		}
		
		
		//if(coins == 0 || !greatestCoinCount) {
			ArrayList<Integer> coinOptions = new ArrayList<>();
			int closestIndex = 0;
			for(int coinLoc = 0; coinLoc < coinLocs.length; coinLoc++) {
				int rowDistance = Math.abs(coinLocs[coinLoc][0] - row);
				int colDistance = Math.abs(coinLocs[coinLoc][1] - col);
				if(coinLoc != 0 ) {
					coinOptions.add(rowDistance + colDistance);
					if((rowDistance + colDistance) < coinOptions.get(closestIndex)) {
						closestIndex = coinLoc;
					}
				} else {
					coinOptions.add(rowDistance + colDistance - 7);
					if((rowDistance + colDistance) < coinOptions.get(closestIndex)) {
						closestIndex = coinLoc;
					}
				}
			}

			if(row < coinLocs[closestIndex][0]) {
				return "south";
			}

		
		
			if(row > coinLocs[closestIndex][0]) {
				return "north";
			}
		
		
			if(col > coinLocs[closestIndex][1]) {
				return "west";
			}
		 
		

			if(col < coinLocs[closestIndex][1]) {
				return "east";
			}
			
			return "none";
			
		//} else {
			//Chase after lower coined players
//			ArrayList<Integer> playerOptions = new ArrayList<>();
//			playerOptions.add(1000000);
//			int closestIndexPlayer = 1;
//			for(int botLoc = 0; botLoc < botInfo.length; botLoc++) {
//				if(botInfo[botLoc][0] != row && botInfo[botLoc][1] != col) {
//					int rowDistance = Math.abs(botInfo[botLoc][0] - row);
//					int colDistance = Math.abs(botInfo[botLoc][1] - col);
//					playerOptions.add(rowDistance + colDistance);
//					if((rowDistance + colDistance) < playerOptions.get(closestIndexPlayer)) {					
//						closestIndexPlayer = botLoc;
//					}
//				}
//			}
//			if(row < botInfo[closestIndexPlayer - 1][0]) {
//				return "south";
//			}
//			if(row > botInfo[closestIndexPlayer - 1][0]) {
//				return "north";
//			}
//			if(col > botInfo[closestIndexPlayer - 1][1]) {
//				return "west";
//			}
//			if(col < botInfo[closestIndexPlayer - 1][1]) {
//				return "east";
//			}
//			System.out.print("none");
//			return "none";
		//}
	}

	@Override
	public void died(int moves, int coins, String reason) {
		System.out.println(reason);
		
	}

	@Override
	public void won(int moves, int coins) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void newSimulation() {
		// TODO Auto-generated method stub
		
	}

}
