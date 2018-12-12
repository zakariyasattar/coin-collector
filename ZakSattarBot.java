public class ZakSattarBot implements Bot {

    //returns the name of the Bot
    public String getName() {
        return getClass().getName();
    }

    public String move(int row, int col, int coins, int arenaLen, int[][] botInfo, int[][] coinLocs) {

      boolean enemyNorth = enemyThere(row, col, "north", botInfo, arenaLen);
      boolean enemySouth = enemyThere(row, col, "south", botInfo, arenaLen);
      boolean enemyWest = enemyThere(row, col, "west", botInfo, arenaLen);
      boolean enemyEast = enemyThere(row, col, "north", botInfo, arenaLen);

      int smallI = 0;
      int smallest = 10000000;
      int[][] diffs = new int[coinLocs.length][3];

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

      if(row != diffs[smallI][1]){
        if(row > diffs[smallI][1]){
          if(!enemyNorth){
            return "north";
          }
          else{
            return "none";
          }
        }
        else if(row < diffs[smallI][1]){
          if(!enemySouth){
            return "south";
          }
          else {
            return "none";
          }
        }
      }

      if(col != diffs[smallI][2]){
        if(col > diffs[smallI][2]){
          if(!enemyWest){
            return "west";
          }
          else {
            return "none";
          }
        }
        else if(col < diffs[smallI][2]){
          if(!enemyEast){
            return "east";
          }
          else {
            return "none";
          }
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

    public boolean enemyThere(int row, int col, String direction, int[][] botInfo, int arenaLen){

      int counter = 0;

      if(direction == "north"){
        for(int i = 0; i < botInfo.length; i++){
          for(int j = 0; j < botInfo[i].length; j++){
            if(botInfo[i][j] < row - 1 && row != 0 && row != arenaLen){
              counter++;
            }
          }
        }

        if(counter == botInfo.length){
          return true;
        }
      }

      else if(direction == "south"){
        for(int i = 0; i < botInfo.length; i++){
          for(int j = 0; j < botInfo[i].length; j++){
            if(botInfo[i][j] > row + 1 && row != 0 && row != arenaLen){
              counter++;
            }
          }
        }

        if(counter == botInfo.length){
          return true;
        }
      }

      else if(direction == "west"){
        for(int i = 0; i < botInfo.length; i++){
          for(int j = 0; j < botInfo[i].length; j++){
            if(botInfo[i][j] < col - 1 && col != 0 && col != arenaLen){
              counter++;
            }
          }
        }

        if(counter == botInfo.length){
          return true;
        }
      }

      else if(direction == "east"){
        for(int i = 0; i < botInfo.length; i++){
          for(int j = 0; j < botInfo[i].length; j++){
            if(botInfo[i][j] > col + 1 && col != 0 && col != arenaLen){
              counter++;
            }
          }
        }

        if(counter == botInfo.length){
          return true;
        }
      }
      return false;
    }

}
