public class ZakSattarBot implements Bot {

    //returns the name of the Bot
    public String getName() {
        return getClass().getName();
    }

    public String move(int row, int col, int coins, int arenaLen, int[][] botInfo, int[][] coinLocs) {

      if(row == coinLocs[0][0] && col == coinLocs[0][1]){
        return "none";
      }

      else {
        int goldXPos = coinLocs[0][0];
        int goldYPos = coinLocs[0][1];

        System.out.println("xPoss" + goldXPos+ "  " + row);
        System.out.println("yPoss" + goldYPos+ "  " + col);

        
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
