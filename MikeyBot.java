public class MikeyBot implements Bot {

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "AI_2-Michael_F";
	}

	@Override
	public String move(int row, int col, int coins, int arenaLen, int[][] botInfo, int[][] coinLocs) {
		double[][] boardValues = new double[arenaLen][arenaLen];
		int[][] adjBotInfo = new int[botInfo.length-1][3];
		int index=0;
		for(int i=0;i<botInfo.length;i++) {
			int[] bot = botInfo[i];
			if(bot[0]!=row || bot[1]!=col) {
				adjBotInfo[index]=bot;
				index++;
			}
		}
		botInfo=adjBotInfo;

		for(int[] bot : botInfo) {
			if(bot[2]>=coins) {
				boardValues = updateBoard(bot[0],bot[1],row,col,coins,arenaLen,(-10000000),boardValues,botInfo,false,"enemy");
			}
		}

		for(int i=0; i<coinLocs.length; i++) {
			if(i == 0) {
				boardValues = updateBoard(coinLocs[i][0],coinLocs[i][1],row,col,coins,arenaLen,500000,boardValues,botInfo,true,"coin");
			}else {
				boardValues = updateBoard(coinLocs[i][0],coinLocs[i][1],row,col,coins,arenaLen,1000,boardValues,botInfo,false,"coin");
			}
		}

		double[] moveOptions = {-Float.MAX_VALUE,-Float.MAX_VALUE,-Float.MAX_VALUE,-Float.MAX_VALUE,-Float.MAX_VALUE};

		if(row-1 >= 0) {
			moveOptions[0] = boardValues[row-1][col];	//0
		}
		if(row+1 < arenaLen) {
			moveOptions[1] = boardValues[row+1][col]; 	//1
		}
		if(col+1 < arenaLen) {
			moveOptions[2]  = boardValues[row][col+1]; 	//2
		}
		if(col-1 >= 0) {
			moveOptions[3]  = boardValues[row][col-1];	//3
		}
		moveOptions[4] = boardValues[row][col];

		double max = moveOptions[4];
		int maxLoc = 4;
		for(int i=0; i<moveOptions.length; i++) {
			if(moveOptions[i]>max) {
				max = moveOptions[i];
				maxLoc = i;
			}
		}

		switch(maxLoc) {
	        case 0: return "north";
	        case 1: return "south";
	        case 2: return "east";
	        case 3: return "west";
	        default: return "none";
		}
	}

	private double[][] updateBoard(int row,int col,int selfRow,int selfCol,int selfCoin,int arenaLen, double coinVal, double[][] boardValues,int[][] botInfo,boolean gold,String keypath){
		boolean count = true;
		if(gold) {
			int playerDif =  Math.abs(selfRow-row) + Math.abs(selfCol-col);
			for(int bot[]:botInfo) {
				if( (Math.abs(bot[0]-row) + Math.abs(bot[1]-col)) < playerDif) {
					count = false;
				}else if( (Math.abs(bot[0]-row) + Math.abs(bot[1]-col)) == playerDif){
					if(bot[2]>selfCoin) {
						count = false;
					}
				}
			}
		}
		if(count) {
			for(int rowVal=0;rowVal<arenaLen;rowVal++) {
				for(int colVal=0;colVal<arenaLen;colVal++) {
					int dif = Math.abs(rowVal-row) + Math.abs(colVal-col);
					if(keypath.equals("coin")) {
						boardValues[rowVal][colVal] += coinVal/(dif+1);
					}
					else if(keypath.equals("enemy")){
						if(dif==1||dif==0) {
							boardValues[rowVal][colVal]=-Float.MAX_VALUE;
						}
					}
				}
			}
		}
		return boardValues;
	}

	@Override
	public void died(int moves, int coins, String reason) {
		// TODO Auto-generated method stub
		System.out.println(reason+"  "+getName()+" coins "+coins);
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
