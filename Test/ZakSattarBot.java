public class ZakSattarBot implements Bot {

    //returns the name of the Bot
    public String getName() {
        return getClass().getName();
    }

    public boolean botIsThere(int[][] botInfo, int[][] coinLocs) {
      for(int i = 0; i < botInfo.length; i++){
        if(botInfo[i][0] - coinLocs[0][0] != 1 && botInfo[i][1] - coinLocs[0][0] != 1){
          return false;
        }
      }
      return true;
    }

    public String go(int row, int col, int[][] botInfo, int coins, int arenaLen) {
      boolean north = false;
      boolean south = false;
      boolean east = false;
      boolean west = false;

      for(int i = 0; i < botInfo.length; i++){
        if(botInfo[i][0] != (row - 1) && Math.abs(botInfo[i][1] - col) > 1){
          north = true;
        }
        else if(botInfo[i][0] != row + 1 && Math.abs(botInfo[i][1] - col) > 1){
          south = true;
        }
        else if(botInfo[i][1] != col - 1 && Math.abs(botInfo[i][0] - row) > 1){
          west = true;
        }
        else if(botInfo[i][1] != col + 1 && Math.abs(botInfo[i][0] - row) > 1){
          east = true;
        }
      }

      if(north) {
        return "north";
      }
      else if(south){
        return "south";
      }
      else if(west) {
        return "west";
      }
      else if(east) {
        return "east";
      }
      return "none";
    }

    public String move(int row, int col, int coins, int arenaLen, int[][] botInfo, int[][] coinLocs) {

      int smallI = 0;
      int smallest = 100000000;
      int[][] diffs = new int[coinLocs.length][3];

      for(int i = 0; i < coinLocs.length; i++){
        int currBot = Math.abs(coinLocs[i][0] - row);
        int currPos = Math.abs(coinLocs[i][1] - col);

        diffs[i][0] = (currBot + currPos) - 1;
        diffs[i][1] = coinLocs[i][0];
        diffs[i][2] = coinLocs[i][1];
      }

      for(int i = 0; i < diffs.length; i++){
        if(diffs[i][0] < smallest){
          smallI = i;
        }
      }

      if(row > diffs[smallI][1]){
        if(Math.abs(row - coinLocs[0][0]) != 1){
          return "north";
        }
        else if(botIsThere(botInfo, coinLocs)){
          return go(row, col, botInfo, coins, arenaLen);
        }
      }
      else if(row < diffs[smallI][1]){
        if(Math.abs(row - coinLocs[0][0]) != 1){
          return "south";
        }
        else if(botIsThere(botInfo, coinLocs)){
          return go(row, col, botInfo, coins, arenaLen);
        }
      }

      if(col > diffs[smallI][2]){
        if(Math.abs(row - coinLocs[0][0]) != 1){
          return "west";
        }
        else if(botIsThere(botInfo, coinLocs)){
          return go(row, col, botInfo, coins, arenaLen);
        }
      }
      else if(col < diffs[smallI][2]){
        if(Math.abs(row - coinLocs[0][0]) != 1){
          return "east";
        }
        else if(botIsThere(botInfo, coinLocs)){
          return go(row, col, botInfo, coins, arenaLen);
        }
      }



      return "none";
    }

    public void died(int moves, int coins, String reason) {
    }

    public void won(int moves, int coins) {
    }


    public void newSimulation() {
    }
}
