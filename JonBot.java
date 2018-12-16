import java.util.ArrayList;
import java.util.Arrays;

public class JonBot implements Bot {

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "JohnTruongBot";
	}

	@Override
	public String move(int row, int col, int coins, int arenaLen, int[][] botInfo, int[][] coinLocs) {
		// TODO Auto-generated method stub
		int[] loc= {row,col};
		// Finds nearest coin and goes for it ~4000 coins in 100 rounds
		int[] targetCoin=fNO(loc, coinLocs, true);  // Finding Nearest Coin
		boolean hh=true;
		if(hh) {
		if(row > targetCoin[0] && row>0)
			return "north";
		else if(row < targetCoin[0] && row < arenaLen)
			return "south";
		else if(col > targetCoin[1] && col >0)
			return "west";
		else if(col < targetCoin[1] && col <arenaLen)
			return "east";
		else							//Unnessecary
			return "none";
		}

		else {
		 //Goes for gold coin ~ 4500 coins in 100 rounds
				if(row > coinLocs[0][0] && row>0)
					return "north";
				else if(row < coinLocs[0][0] && row < arenaLen)
					return "south";
				else if(col > coinLocs[0][1] && col >0)
					return "west";
				else if(col < coinLocs[0][1] && col <arenaLen)
					return "east";
				else
					return "none";
		}

		// find distances between all (coins + other bots)


		// optimize pathing

	}
	
	private int findDist(int[] ob1, int[] ob2) {
		return (Math.abs(ob1[0]-ob2[0])+Math.abs(ob1[1]-ob2[1]));
	}

	private int coinIndex(int[] coin, int[][] coinLoc) {
		for (int i = 0; i< coinLoc.length; i++) {
			if(Arrays.equals(coin, coinLoc[i]))
				return i;
		}
		return -1;
	}
	// Find Nearest Object(Coin or bot)
	private int[] fNO(int[] loc, int[][] objLoc , boolean gcoin) {

		int closest=Integer.MAX_VALUE;
		int indexO=0;
		for(int i=0; i<objLoc.length;i++) {

			int dist= findDist(objLoc[i],loc);
			
			if(indexO==0 && gcoin) {  //Optimizes for gold coin
				if(dist<closest +5) {
					closest= dist;
					indexO=i;
				}
				
			}
			else if(dist<closest) {
				closest= dist;
				indexO=i;
			}

		}
		return objLoc[indexO];	
	}


	// Specifically for fNCBots    ??? Redo
	private int[] fNC(int[] loc, int[][] coinLoc, ArrayList<Integer> dis) {

		int closest=Integer.MAX_VALUE;
		int indexC=0;
		for(int i=0; i<coinLoc.length;i++) {
			for(int d=0;d<dis.size();d++) {
				if(i==dis.get(d)) {
					i++;
				}
			}
			if(i>coinLoc.length)
				break;
			int dist= Math.abs(coinLoc[i][1]-loc[1])+Math.abs(coinLoc[i][0]-loc[0]);
			
			if(dist<closest) {
				closest= Math.abs(coinLoc[i][1]-loc[1])+Math.abs(coinLoc[i][0]-loc[0]);
				indexC=i;
			}

		}
		return coinLoc[indexC];	
	}

	private int[] fNCBots(int[] loc, int[][] botInfo, int[][] coinLoc) {
		ArrayList<Integer> imposC = new ArrayList<Integer>();
		
		int[] coin= fNO(loc, coinLoc, true);
		
		for(int i=0; i<botInfo.length; i++) {
			
			int cDist= findDist(loc,fNO(loc,coinLoc, true));
			
			int cIndex= coinIndex(coin,coinLoc);
			
			
			for( int d= 0; d< coinLoc.length;d++) {
				int botDist=findDist(botInfo[i],coinLoc[cIndex]);
				if(botDist<cDist) {
					imposC.add(cIndex);
				}
				
			}
			
		}
		return fNC(loc, coinLoc, imposC);	

	}
	


	@Override
	public void died(int moves, int coins, String reason) {
		// TODO Auto-generated method stub
		System.out.println(reason+"  "+getName());
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
