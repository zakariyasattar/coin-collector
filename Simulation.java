import java.util.*;
import java.io.*;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import javax.swing.table.*;

public class Simulation extends JFrame {

    private ArrayList<Class<?>> allBotClasses;
    private ArrayList<Bot> allBots;

    private ArrayList<BotInfo> botInfo = new ArrayList<>();
    private ArrayList<Coin> coins = new ArrayList<>();
    private Board board = new Board(1);

    private JPanel center;
    private JPanel right;
    private JPanel south;
    private TreeMap<String, Score> records = new TreeMap<>();
    private TreeMap<String, Color> colors = new TreeMap<>();
    private boolean redrawScreen = true;

    private JButton startButton = new JButton("Start");
    private JSlider speedSlider = new JSlider();
    private JTable displayTable;
    private JProgressBar progressBar = new JProgressBar(0, 8000);

    public static void main(String[] args) {
        Simulation sim = new Simulation();
    }

    public Simulation() {

        setSize(600, 600);
        setTitle("Collect Those Coins!");

        allBotClasses = loadClasses();
        allBots = new ArrayList<Bot>();

        for(Class<?> c: allBotClasses) {
            try {
                Bot b = (Bot)c.newInstance();
                allBots.add(b);
                colors.put(b.getName(), new Color(  (int)(Math.random()*256),
                                                    (int)(Math.random()*256),
                                                    (int)(Math.random()*256)));
            }
            catch(Exception e) {
                e.printStackTrace();
                System.out.println("Failed instantiating class " + c);
            }
        }

        Container c = getContentPane();
        c.setLayout(new BorderLayout());
        center = new JPanel() {
            public void paint(Graphics g) {

                g.setColor(Color.WHITE);
                int w = getWidth();
                int h = getHeight();

                int len = board.occupied.length;

                int boxWidth = w / len;
                int boxHeight = w / len;

                g.fillRect(0, 0, w, h);

                for(int i = 0; i < botInfo.size(); i++) {
                    BotInfo bi = botInfo.get(i);
                    g.setColor(colors.get(bi.logic.getName()));

                    g.fillRect(bi.col*boxWidth, bi.row*boxHeight, boxWidth, boxHeight);

                    g.setColor(Color.BLACK);
                    g.drawString(bi.coins + "", bi.col*boxWidth, bi.row*boxHeight + 12);
                }

                for(int i = 0; i < coins.size(); i++) {
                    Coin c = coins.get(i);

                    if(i == 0)
                        g.setColor(Color.YELLOW);
                    else
                        g.setColor(Color.LIGHT_GRAY);

                    g.fillRect(c.col*boxWidth, c.row*boxHeight, boxWidth, boxHeight);
                }
            }
        };
        c.add(center, BorderLayout.CENTER);
        center.setMinimumSize(new Dimension(600, 600));
        center.setPreferredSize(new Dimension(600, 600));

        right = new JPanel();
        south = new JPanel();
        c.add(right, BorderLayout.EAST);
        c.add(south, BorderLayout.SOUTH);

        speedSlider.setMinimum(1);
        speedSlider.setMaximum(1000);
        speedSlider.setValue(1000);

        startButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Thread t = new Thread(new Runnable() {
                    public void run() {
                        startButton.setEnabled(false);
                        records = new TreeMap<>();
                        progressBar.setValue(0);
                        for(int i = 0; i < 8000; i++) {
                            simulateOnce(allBots, records);
                            progressBar.setValue(i+1);
                        }
                        startButton.setEnabled(true);
                        center.repaint();
                        ((AbstractTableModel)displayTable.getModel()).fireTableDataChanged();
                    }
                });
                t.start();
            }
        });

        south.add(startButton);
        south.add(speedSlider);
        south.add(progressBar);

        displayTable = new JTable(new AbstractTableModel() {

            public int getRowCount() {
                return allBots.size() + 1;
            }

            public int getColumnCount() {
                return 5;
            }

            public Object getValueAt(int rowIndex, int columnIndex) {
                if(rowIndex == 0) {
                    switch(columnIndex) {
                        case 0: return "Name";
                        case 1: return "Color";
                        case 2: return "Wins";
                        case 3: return "Coins";
                        case 4: return "Moves";
                    }

                }

                rowIndex--;
                String name = allBots.get(rowIndex).getName();

                if(columnIndex == 0)
                    return name;
                else if(columnIndex == 1) {
                    return "";
                }
                else if(records.containsKey(name)) {
                    if(columnIndex == 2)    {
                        return records.get(name).wins;
                    }
                    else if(columnIndex == 3) {
                        return records.get(name).coinsAtDeath;
                    }
                    else if(columnIndex == 4) {
                        return records.get(name).moves;
                    }
                }

                return "";
            }

        }) {

            public Component prepareRenderer(TableCellRenderer renderer, int row, int col) {
                Component comp = super.prepareRenderer(renderer, row, col);
                Object value = getModel().getValueAt(row, col);
                if (col == 1 && row != 0) {
                    row--;
                    String name = allBots.get(row).getName();
                    comp.setBackground(colors.get(name));
                } else {
                    comp.setBackground(Color.white);
                }
                return comp;
            }
        };

        right.add(displayTable);

        setVisible(true);

        pack();
    }

    private ArrayList<Class<?>> loadClasses()
    {
        ArrayList<Class<?>> list = new ArrayList<Class<?>>();

        String currentDir = System.getProperty("user.dir");// + "/bin";
        File dir = new File(currentDir);

        String[] children = dir.list();
        if(children == null)
            return list;
        else
        {
            for(int i=0; i<children.length; i++)
            {
                if(children[i].endsWith(".class"))
                {
                    try
                    {
                        Class<?> c = Class.forName(children[i].substring(0,children[i].length() - ".class".length()));
                        if(c != null && !c.equals(Bot.class) && Bot.class.isAssignableFrom(c))
                            list.add(c);

                    }
                    catch(Exception e)
                    {
                        e.printStackTrace();
                    }
                }
            }
        }

        return list;
    }

	public Bot simulateOnce(ArrayList<Bot> bots, TreeMap<String, Score> results) {

        board = new Board(bots.size() + 4);

        botInfo = new ArrayList<BotInfo>();
        for(Bot b : bots) {
            botInfo.add(new BotInfo(board, b));
            b.newSimulation();

            if(!results.containsKey(b.getName())) {
                results.put(b.getName(), new Score());
            }
        }

        coins = new ArrayList<Coin>();
        coins.add(new Coin(5, board));
        coins.add(new Coin(1, board));
        coins.add(new Coin(1, board));
        coins.add(new Coin(1, board));
        coins.add(new Coin(1, board));

        int moves = 0;
        while(botInfo.size() > 1 && moves < 20000) {
            HashMap<Integer, ArrayList<BotInfo>> map = new HashMap<>();

            for(int j = botInfo.size()-1; j >=0; j--) {
                BotInfo b = botInfo.get(j);

                int[][] bi = new int[botInfo.size()][3];
                for(int i = 0; i < bi.length; i++) {
                    bi[i][0] = botInfo.get(i).row;
                    bi[i][1] = botInfo.get(i).col;
                    bi[i][2] = botInfo.get(i).coins;
                }

                int[][] coinLocs = new int[5][2];
                for(int i = 0; i < 5; i++) {
                    coinLocs[i][0] = coins.get(i).row;
                    coinLocs[i][1] = coins.get(i).col;
                }

                int row = b.row;
                int col = b.col;
                String next = "";
                try {
                    next = b.logic.move(b.row, b.col, b.coins, bots.size() + 4, bi, coinLocs);
                }
                catch(Exception e) {
                    b.logic.died(moves, b.coins, "Bot generated an exception and died!");
                    e.printStackTrace();
                    botInfo.remove(j);

                    Score score = results.get(b.logic.getName());
                    System.out.println(b.logic.getName());
                    score.gameCount++;
                    score.moves += moves;
                    score.coinsAtDeath += b.coins;

                    continue;
                }

                if(next.equals("north")) row--;
                if(next.equals("south")) row++;
                if(next.equals("east")) col++;
                if(next.equals("west")) col--;

                if(row < 0 || col < 0 || row >= board.occupied.length || col >= board.occupied.length) {
                    b.logic.died(moves, b.coins, "Bot left the board!  New loc: row=" + row + ", col=" + col + " arena length=" + board.occupied.length);
                    botInfo.remove(j);

                    Score score = results.get(b.logic.getName());
                    score.gameCount++;
                    score.moves += moves;
                    score.coinsAtDeath += b.coins;
                }
                else {

                    int loc = row * board.occupied.length + col;
                    if(!map.containsKey(loc))
                        map.put(loc, new ArrayList<BotInfo>());
                    ArrayList<BotInfo> list = map.get(loc);
                    list.add(b);
                }
            }

            for(int loc: map.keySet()) {

                ArrayList<BotInfo> info = map.get(loc);
                int row = loc / board.occupied.length;
                int col = loc % board.occupied.length;

                info.sort((a,b) -> b.coins - a.coins);

                BotInfo survivor = info.get(0);

                int coinCollection = 0;

                //some are going to die
                if(info.size() > 1) {

                    int pos = 0;
                    for(BotInfo i: info) {

                        //don't kill the first Bot if it has more coins than the rest
                        if(pos == 0 && info.get(1).coins != info.get(0).coins) {
                            continue;
                        }

                        i.logic.died(moves, i.coins, "Died colliding with another Bot with more coins");
                        Score score = results.get(i.logic.getName());
                        score.gameCount++;
                        score.moves += moves;
                        score.coinsAtDeath += i.coins;

                        botInfo.remove(i);
                        board.occupied[i.row][i.col] = false;

                        coinCollection += (int)(Math.ceil(i.coins * 0.85));
                    }

                    continue;
                }

                board.occupied[survivor.row][survivor.col] = false;
                board.occupied[row][col] = true;
                survivor.row = row;
                survivor.col = col;
                survivor.coins += coinCollection;

                for(Coin c: coins) {
                    if(c.row == row && c.col == col) {
                        survivor.coins += c.price;
                        c.teleport(board);
                        break;
                    }
                }
            }

            moves++;

            if(redrawScreen) {
                center.repaint();
                try { Thread.sleep(speedSlider.getValue()); } catch(Exception e) { e.printStackTrace(); }
                ((AbstractTableModel)displayTable.getModel()).fireTableDataChanged();
            }
        }

        botInfo.sort((a,b) -> b.coins - a.coins);

        //no winners :(
        if(botInfo.size() == 0) {
            return null;
        }
        //also no winners :(
        else if(botInfo.size() > 1 && botInfo.get(0).coins == botInfo.get(1).coins) {
            for(int i = 0; i < botInfo.size(); i++) {
                botInfo.get(i).logic.died(moves, botInfo.get(i).coins, "Died at end of game because no outright winner");

                Score score = results.get(botInfo.get(i).logic.getName());
                score.gameCount++;
                score.moves += moves;
                score.coinsAtDeath += botInfo.get(i).coins;
            }
            return null;
        }
        //we have a single winner!
        else {
            botInfo.get(0).logic.won(moves, botInfo.get(0).coins);
            Score score = results.get(botInfo.get(0).logic.getName());
            score.gameCount++;
            score.wins++;
            score.moves += moves;
            score.coinsAtDeath += botInfo.get(0).coins;

            for(int i = 1; i < botInfo.size(); i++) {
                botInfo.get(i).logic.died(moves, botInfo.get(i).coins, "Died at end of game because a Bot had more coins they you");

                score = results.get(botInfo.get(i).logic.getName());
                score.gameCount++;
                score.moves += moves;
                score.coinsAtDeath += botInfo.get(i).coins;
            }

            return botInfo.get(0).logic;
        }

	}

}

class Board {
    boolean[][] occupied;

    public Board(int len) {
        occupied = new boolean[len][len];
    }

    public boolean inBounds(int row, int col) {
        if(row < 0 || col < 0 || row >= occupied.length || col >= occupied.length)
            return false;
        return true;
    }

    //rad = 0 means just checking (row, col)
    //rad = 1 means also check 1 above, below, left and right
    public boolean available(int rad, int row, int col) {
        if(!inBounds(row, col))
            return false;

        if(occupied[row][col])
            return false;

        if(rad == 1) {
            return  !(inBounds(row-1, col) && occupied[row-1][col]) &&
                    !(inBounds(row+1, col) && occupied[row+1][col]) &&
                    !(inBounds(row, col-1) && occupied[row][col-1]) &&
                    !(inBounds(row, col+1) && occupied[row][col+1]) &&
                    !(inBounds(row-1, col-1) && occupied[row-1][col-1]) &&
                    !(inBounds(row-1, col+1) && occupied[row-1][col+1]) &&
                    !(inBounds(row+1, col-1) && occupied[row+1][col-1]) &&
                    !(inBounds(row+1, col+1) && occupied[row+1][col+1]);
        }

        return true;
    }
}

class BotInfo {

	int coins;
	int row;
	int col;
    Bot logic;

    public BotInfo(Board board, Bot bot) {
        logic = bot;

        do {
            row = (int)(Math.random()*board.occupied.length);
            col = (int)(Math.random()*board.occupied.length);
        }
        while(!board.available(1, row, col));
        board.occupied[row][col] = true;
    }
}

class Coin {

	int price;
	int row = -1;
	int col = -1;

	public Coin(int p, Board board) {
		price = p;
		teleport(board);
	}

	public void teleport(Board board) {

		int oldRow = row;
		int oldCol = col;

		do {
			row = (int)(Math.random()*board.occupied.length);
			col = (int)(Math.random()*board.occupied.length);
		}
		while(!board.available(0, row, col));

        if(oldRow != -1) board.occupied[oldRow][oldCol] = false;
        board.occupied[row][col] = true;
	}
}

class Score {
    int gameCount = 0;
    int moves = 0;
    int coinsAtDeath = 0;
    int wins = 0;
}
