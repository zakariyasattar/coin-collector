public class ZakSattarBot implements Bot {

  private int counter = 0;

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

      if(row != bots[smallI][1]){
        if(row > bots[smallI][1]){
          return "north";
        }
        else if(row < bots[smallI][1]){
          return "south";
        }
      }

      if(col != bots[smallI][2]){
        if(col > bots[smallI][2]){
          return "west";
        }
        else if(col < bots[smallI][2]){
          return "east";
        }
      }
      System.out.println("row - " + row + " col - " + col + "    " + bots[smallI][2] + "----" + bots[smallI][1]);
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
        System.out.println(largestCoin + " --he;lllllll ");
      }
      else {
        System.out.println("beast");
        //return eatEveryone(botInfo, row, col);
      }

      boolean enemyNorth = enemyThere(row, col, "north", botInfo, arenaLen, coins);
      boolean enemySouth = enemyThere(row, col, "south", botInfo, arenaLen, coins);
      boolean enemyWest = enemyThere(row, col, "west", botInfo, arenaLen, coins);
      boolean enemyEast = enemyThere(row, col, "north", botInfo, arenaLen, coins);

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
        if(row > diffs[smallI][1] ){//&& !enemyNorth){
          return "north";
        }
        else if(row < diffs[smallI][1]){ //&& !enemySouth){
          return "south";
        }
      }

      if(col != diffs[smallI][2]){
        if(col > diffs[smallI][2]){//&& !enemyWest){
          return "west";
        }
        else if(col < diffs[smallI][2]){// && !enemyEast){
          return "east";
        }
      }
      System.out.println("nothing");
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

    public boolean enemyThere(int row, int col, String direction, int[][] botInfo, int arenaLen, int coins){

      int counter = 0;

      if(direction == "north"){
        for(int i = 0; i < botInfo.length; i++){
          if(botInfo[i][0] < row - 1 && row != 0 && row != arenaLen || botInfo[i][2] < coins){
            counter++;
          }
        }

        if(counter == botInfo.length){
          return true;
        }
      }

      else if(direction == "south"){
        for(int i = 0; i < botInfo.length; i++){
          if(botInfo[i][0] > row + 1 && row != 0 && row != arenaLen || botInfo[i][2] < coins){
            counter++;
          }
        }

        if(counter == botInfo.length){
          return true;
        }
      }

      else if(direction == "west"){
        for(int i = 0; i < botInfo.length; i++){
          if(botInfo[i][1] < col - 1 && col != 0 && col != arenaLen || botInfo[i][2] < coins){
            counter++;
          }
        }

        if(counter == botInfo.length){
          return true;
        }
      }

      else if(direction == "east"){
        for(int i = 0; i < botInfo.length; i++){
          if(botInfo[i][1] > col + 1 && col != 0 && col != arenaLen || botInfo[i][2] < coins){
            counter++;
          }
        }

        if(counter == botInfo.length){
          return true;
        }
      }
      return false;
    }

}
