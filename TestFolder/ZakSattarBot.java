public class ZakSattarBot implements Bot {

  private int counter = 0;
  private int smallestPos = 0;

    //returns the name of the Bot
    public String getName() {
        return getClass().getName();
    }

    public String eatEveryone(int[][] botInfo, int row, int col){
      int smallI = 0;
      int smallest = 100000000;
      int[][] bots = new int[botInfo.length][3];

      for(int i = 0; i < botInfo.length; i++){
        int currBot = botInfo[i][0] + botInfo[i][1];
        int currPos = row + col;

        bots[i][0] = Math.abs(currBot - currPos);
        bots[i][1] = botInfo[i][0];
        bots[i][2] = botInfo[i][1];
      }

      for(int i = 0; i < bots.length; i++){
        if(bots[i][0] < smallest){
          smallI = i;
        }
      }

      System.out.println(smallI + "  " + row + "  " + bots[smallI][1]);

      if(row > bots[smallI][1]){
        return "north";
      }
      else if(row < bots[smallI][1]){
        return "south";
      }
      else if(row == bots[smallI][1]) {
        if(col > bots[smallI][2]){
          return "west";
        }
        else if(col < bots[smallI][2]){
          return "east";
        }
        System.out.println(row == bots[smallI][1] && col == bots[smallI][2]);
      }

      System.out.println("none");
      return "none";
    }

    public String move(int row, int col, int coins, int arenaLen, int[][] botInfo, int[][] coinLocs) {

      int largestCoin = -10000000;
      int[] playerCoins = new int[botInfo.length];
      for(int i = 0; i < botInfo.length; i++){
        playerCoins[i] = botInfo[i][2];
      }

      for(int g : playerCoins){
        if(g > largestCoin){
          largestCoin = g;
        }
      }

      if(largestCoin > coins){
        int convI = 0;
        for(int i = 0; i < botInfo.length; i++){
          if(botInfo[i][2] < coins){
            convI = i;
          }
        }

        if(convI != 0) {
          int nearestPos = 100000;
          int smallestPos = 0;
          for(int j = 0; j < coinLocs.length; j++){
            int totalRow = Math.abs(botInfo[convI][0] - coinLocs[j][0]);
            int totalCol = Math.abs(botInfo[convI][1] - coinLocs[j][1]);

            if(totalRow + totalCol < nearestPos){
              nearestPos = totalRow + totalCol;
              smallestPos = j;
            }
          }
          if(row != coinLocs[smallestPos][0]){
            if(row > coinLocs[smallestPos][0]){
              return "north";
            }
            else if(row < coinLocs[smallestPos][0]){
              return "south";
            }
          }
          else {
            System.out.println(row + " " + coinLocs[smallestPos][0]);
          }

          if(col != coinLocs[smallestPos][1]){
            if(col > coinLocs[smallestPos][1]){
              return "west";
            }
            else if(col < coinLocs[smallestPos][1]){
              return "east";
            }
          }
        }
        else {
          System.out.println(largestCoin + " is " + coins);
        }
      }
      else {
        boolean canMoveNorth = enemyThere(row, col, "north", botInfo, arenaLen, coins);
        boolean canMoveSouth = enemyThere(row, col, "south", botInfo, arenaLen, coins);
        boolean canMoveWest = enemyThere(row, col, "west", botInfo, arenaLen, coins);
        boolean canMoveEast = enemyThere(row, col, "east", botInfo, arenaLen, coins);

        if(row != coinLocs[0][0]){
          if(row > coinLocs[0][0] && canMoveNorth ){
            return "north";
          }
          else if(row < coinLocs[0][0] && canMoveSouth ){
            return "south";
          }
        }

        if(col != coinLocs[0][1]){
          if(col > coinLocs[0][1] && canMoveWest ){
            return "west";
          }
          else if(col < coinLocs[0][1] && canMoveEast ){
            return "east";
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
      System.out.println("You won " + moves + " " + coins);
    }


    public void newSimulation() {
      System.out.println("New Simulation!");
    }

    public boolean canPickUpCoin(int currRow, int currCol, int coinRow, int coinCol, int[][] botInfo) {
      if(Math.abs(currRow - coinRow) > 1 || Math.abs(currCol - coinCol) > 1){
        return true;
      }
      else {
        for(int i = 0; i < botInfo.length; i++){
          int botRow = botInfo[i][0];
          int botCol = botInfo[i][1];

          if((Math.abs(botRow - coinRow) + Math.abs(botCol - coinRow)) == 0){
            return false;
          }
        }
      }
      return true;
    }

    public boolean enemyThere(int row, int col, String direction, int[][] botInfo, int arenaLen, int coins){

      int counter = 0;

      if(direction == "north"){
        for(int i = 0; i < botInfo.length; i++){
          if((botInfo[i][0] < row - 1 && row != 0 && row != arenaLen) || botInfo[i][2] < coins){
            counter++;
          }
        }

        if(counter == botInfo.length){
          return false;
        }
      }

      else if(direction == "south"){
        for(int i = 0; i < botInfo.length; i++){
          if((botInfo[i][0] > row + 1 && row != 0 && row != arenaLen) || botInfo[i][2] < coins){
            counter++;
          }
        }

        if(counter == botInfo.length){
          return false;
        }
      }

      else if(direction == "west"){
        for(int i = 0; i < botInfo.length; i++){
          if((botInfo[i][1] < col - 1 && col != 0 && col != arenaLen) || botInfo[i][2] < coins){
            counter++;
          }
        }

        if(counter == botInfo.length){
          return false;
        }
      }

      else if(direction == "east"){
        for(int i = 0; i < botInfo.length; i++){
          if((botInfo[i][1] > col + 1 && col != 0 && col != arenaLen) || botInfo[i][2] < coins){
            counter++;
          }
        }

        if(counter == botInfo.length){
          return false;
        }
      }
      return true;
    }

}
