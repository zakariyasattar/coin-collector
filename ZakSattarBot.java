public class ZakSattarBot implements Bot {

    //returns the name of the Bot
    public String getName() {
        return getClass().getName();
    }

    public String move(int row, int col, int coins, int arenaLen, int[][] botInfo, int[][] coinLocs) {

      int smallI = 0;
      int smallest = 10000000;
      int[][] diffs = new int[coinLocs.length][3];

      int goldXPos = coinLocs[0][1];
      int goldYPos = coinLocs[0][0];

      for(int i = 0; i < coinLocs.length; i++){
        int currCoin = coinLocs[i][0] + coinLocs[i][1];
        int currPos = row + col;

        diffs[i][0] = Math.abs(currCoin - currPos);
        diffs[i][1] = coinLocs[i][0];
        diffs[i][2] = coinLocs[i][1];
      }

      for(int i = 0; i < diffs.length; i++){
        if(diffs[i][0] < smallest){
          smallI = i;
        }
      }

      // System.out.println(diffs[smallI][1] + " " + diffs[smallI][2] + "--------" + row + " " + col);

      if(row != diffs[smallI][1]){
        if(row > diffs[smallI][1]){
          return "north";
        }
        else if(row < diffs[smallI][1]){
          return "south";
        }
      }

      if(col != diffs[smallI][2]){
        if(col > diffs[smallI][2]){
          return "west";
        }
        else if(col < diffs[smallI][2]){
          return "east";
        }
      }

      return "none";
    }



    public void died(int moves, int coins, String reason) {
      System.out.println("You had " + moves + " moves, " + coins + " coins" );
      System.out.println(reason);
    }

    public void won(int moves, int coins) {
      System.out.println("You won" + moves + " " + coins);
    }


    public void newSimulation() {
      System.out.println("New Simulation!");
    }
}
