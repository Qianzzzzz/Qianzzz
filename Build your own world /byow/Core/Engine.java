package byow.Core;


import java.io.PrintWriter; //for save
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;
import edu.princeton.cs.algs4.In;//读写文件的class

import byow.TileEngine.TERenderer;
import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;
import edu.princeton.cs.algs4.StdDraw;


public class Engine {
    TERenderer ter = new TERenderer();
    /* Feel free to change the width and height. */
    public static final int WIDTH = 60;
    public static final int HEIGHT = 30;
    public static final int RANGE = 5;

    public static final String SAVEPATH = "savefile.txt";//save file name

    private Pos avator;// avator position
    private Pos door;
    private TERenderer render;
    private int step;
    private int seed;
    private int status;
    private boolean startSave;

    // only displays tiles on the screen that are within the line of sight of the avatar.
    // The line of sight must be able to be toggled on and off with a keypress.
    private boolean toggleOn;

    public Engine() { // Initialize
        step = 0;
        startSave = false;
        status = 0;
    }

    /**
     * Method used for exploring a fresh world. This method should handle all inputs,
     * including inputs from the main menu.
     */
    public void interactWithKeyboard() { //draw the whole blank ui,
        status = 0;
        toggleOn = false;
        render = new TERenderer();

        render.initialize(WIDTH, HEIGHT + 4, 0, 2);

        startGame();
    }

    public void startGame() {
        String[] menu1 = {"New game(N)", "Load last(L)"};
        render.renderMenu("Welcome to Game BYOW", menu1, "have fun!");//（head，middle，footer）

        String input = getStartInput();

        TETile[][] world = interactWithInputString(input);
        render.renderFrame(world, avator, toggleOn);
        render.renderHUD("step: " + step, world);
        gameLoop(world);
    }

    public void winGame() {
        //status: 3
        String[] menu2 = {"New game(N)", "Exit(E)"};
        render.renderMenu("You win with step: " + step + ".", menu2, "");

        String input = getWinInput();
        if (input.equals("e")) {
            System.exit(1);//exit the game
        }

        TETile[][] world = interactWithInputString(input);
        render.renderFrame(world, avator, toggleOn);
        render.renderHUD("Step: " + step, world);
        gameLoop(world);
    }

    public String getInputSeed() { //inputseed
        String inputSeed = "";
        String[] menu2 = {inputSeed};
        render.renderMenu("Input a num as random seed", menu2, "Enter s to start");

        while (status == 1) {
            if (StdDraw.hasNextKeyTyped()) {
                char c = StdDraw.nextKeyTyped();
                if (c == 's' && !inputSeed.isEmpty()) {
                    status = 2;
                    break;
                } else if (Character.isDigit(c)) {
                    inputSeed += c;
                    menu2[0] = inputSeed;
                    render.renderMenu("Input a num as random seed", menu2, "Enter s to start");
                }
            }
        }

        return "N" + inputSeed + "S";
    }

    public String getStartInput() { //Accept input from the start screen
        while (status == 0) {
            if (StdDraw.hasNextKeyTyped()) {
                char c = StdDraw.nextKeyTyped();
                if (c == 'n') {
                    status = 1;
                } else if (c == 'l') {
                    status = 2;
                    return "l";
                }
            }
        }

        return getInputSeed();
    }

    public String getWinInput() { //N is new game, E exits
        while (status == 3) {
            if (StdDraw.hasNextKeyTyped()) {
                char c = StdDraw.nextKeyTyped();
                if (c == 'n') {
                    status = 1;
                } else if (c == 'e') {
                    return "e";
                }
            }
        }

        return getInputSeed();
    }

    public void gameLoop(TETile[][] world) {//map display control
        while (status == 2) {//running
            if (StdDraw.hasNextKeyTyped()) {
                char c = StdDraw.nextKeyTyped();
                parseCmd(c, world);
            }

            render.renderFrame(world, avator, toggleOn);
            render.renderHUD("step: " + step, world);
            StdDraw.pause(100);//Pause for 0.1s
        }

        if (status == 3) {//End the game and win.
            //win display all tiles
            render.renderFrame(world, avator, true);
            StdDraw.pause(1000);//Pause for 1s
            winGame();//Go to victory end screen
        } else if (status == 4) {//Esc game
            //have save game, so quit
            System.exit(1);
        }
    }

    public void genWorld(TETile[][] world, Random rand) { //generate world
        for (int x = 0; x < WIDTH; x++) { //generate empty world
            for (int y = 0; y < HEIGHT; y++) {
                world[x][y] = Tileset.NOTHING;
            }
        }

        ArrayList<Rect> rectList = new ArrayList<Rect>();

        int rightX = 1;
        int leftX = 1;

        while (rightX + RANGE < WIDTH) {
            Rect r1;
            //The value range of the x of the upper left corner vertex when generating a rectangle (from leftX to rightX+range), randomly select an integer within this range as the x of the vertex.
            // y is randomly selected within the range of hight+-1.
            //The vertex is lower left
            r1 = new Rect(rand, leftX, rightX + RANGE, 1, HEIGHT - 1);
            r1.genWH(rand, WIDTH, HEIGHT, RANGE);
            r1.fillWorld(world);

            if (rectList.size() > 0) {
                //connect rect
                int ci = RandomUtils.uniform(rand, 0, rectList.size());//随机选中在list中的一个矩形
                rectList.get(ci).connectTo(r1, rand, world);//把当前矩形连接到选中的list矩形
            }

            rectList.add(r1);//把当前矩形加入list

            leftX = r1.getLeftX();
            if (r1.getRightX() > rightX) {
                rightX = r1.getRightX();
            }
        }

        addWall(world);
        genDoorAndAvator(rand, rectList, world);
    }

    /**
     * Method used for autograding and testing your code. The input string will be a series
     * of characters (for example, "n123sswwdasdassadwas", "n123sss:q", "lwww". The engine should
     * behave exactly as if the user typed these characters into the engine using
     * interactWithKeyboard.
     * <p>
     * Recall that strings ending in ":q" should cause the game to quite save. For example,
     * if we do interactWithInputString("n123sss:q"), we expect the game to run the first
     * 7 commands (n123sss) and then quit and save. If we then do
     * interactWithInputString("l"), we should be back in the exact same state.
     * <p>
     * In other words, running both of these:
     * - interactWithInputString("n123sss:q")
     * - interactWithInputString("lww")
     * <p>
     * should yield the exact same world state as:
     * - interactWithInputString("n123sssww")
     *
     * @param input the input string to feed to your program
     * @return the 2D TETile[][] representing the state of the world
     */
    public TETile[][] interactWithInputString(String input) {//interactWithInputString generates maps from seeds
        // TODO: Fill out this method so that it run the engine using the input
        // passed in as an argument, and return a 2D tile representation of the
        // world that would have been drawn if the same inputs had been given
        // to interactWithKeyboard().
        //
        // See proj3.byow.InputDemo for a demo of how you can make a nice clean interface
        // that works for many different input types.

        TETile[][] finalWorldFrame = new TETile[WIDTH][HEIGHT];
        String line = input.toLowerCase();

        int si; //index of last start command, followed by movement commands
        if (line.charAt(0) == 'l') {//charAt
            loadGame(finalWorldFrame);
            si = 0;
        } else {
            int ni = line.indexOf("n");
            si = line.indexOf("s");
            if (ni < 0 || si < 0 || ni >= si) {
                return null;
            }
            String seed = line.substring(ni + 1, si);
            Random rand = new Random(Integer.parseInt(seed));
            genWorld(finalWorldFrame, rand);
        }

        //Execute move command (WASD), and save command(:Q)
        for (int i = si + 1; i < line.length(); i++) {
            char k = line.charAt(i);
            parseCmd(k, finalWorldFrame);
        }

        return finalWorldFrame;
    }

    public void genDoorAndAvator(Random rand, ArrayList<Rect> rectList,
                                 TETile[][] finalWorldFrame) {
        // add door at last rect
        Rect lastRect = rectList.get(rectList.size() - 1);
        LinkedList<Pos> posList = lastRect.getAdjWall(finalWorldFrame);
        int pi = RandomUtils.uniform(rand, 0, posList.size());
        door = posList.get(pi);
        finalWorldFrame[door.x][door.y] = Tileset.LOCKED_DOOR;

        // new add for phase 2
        // generate avator at random pos in random rect;
        int ci = RandomUtils.uniform(rand, 0, rectList.size() - 1);
        avator = rectList.get(ci).genRandomPos(rand);
        finalWorldFrame[avator.x][avator.y] = Tileset.AVATAR;
    }

    public void moveAvator(int dx, int dy, TETile[][] world) {

        Pos nextPos = avator.plus(dx, dy);

        if (GridUtil.checkEqual(nextPos, Tileset.FLOOR, world)) {
            world[avator.x][avator.y] = Tileset.FLOOR;
            avator = nextPos;
            world[avator.x][avator.y] = Tileset.AVATAR;
            step += 1;
        } else if (GridUtil.checkEqual(nextPos, Tileset.LOCKED_DOOR, world)) {
            world[avator.x][avator.y] = Tileset.FLOOR;
            avator = nextPos;
            world[avator.x][avator.y] = Tileset.AVATAR;
            step += 1;
            status = 3;// win
        }
    }

    public void parseCmd(char k, TETile[][] world) {//Parse commands, modify world and game state
//    	System.out.println("parseCmd");
        switch (k) {
            case 'w':
                moveAvator(0, 1, world);
                break;
            case 'a':
                moveAvator(-1, 0, world);
                break;
            case 's':
                moveAvator(0, -1, world);
                break;
            case 'd':
                moveAvator(1, 0, world);
                break;
            case ':':
                startSave = true;
                return;
            case 'q':
                if (startSave) {
                    saveGame(world);
                    status = 4;
                    return;
                }
                break;
            case 't'://toggle on / off
                toggleOn = !toggleOn;
                break;
        }
        startSave = false;
    }


    public void loadGame(TETile[][] world) {
        In inTxt = new In(SAVEPATH);

        for (int yi = HEIGHT - 1; yi >= 0; yi--) {
            String line = inTxt.readLine();
            for (int xi = 0; xi < WIDTH; xi++) {
                char c = line.charAt(xi);
                TETile tile;
                if (c == Tileset.AVATAR.character()) {
                    tile = Tileset.AVATAR;
                    this.avator = new Pos(xi, yi);
                } else if (c == Tileset.WALL.character()) {
                    tile = Tileset.WALL;
                } else if (c == Tileset.FLOOR.character()) {
                    tile = Tileset.FLOOR;
                } else if (c == Tileset.NOTHING.character()) {
                    tile = Tileset.NOTHING;
                } else if (c == Tileset.LOCKED_DOOR.character()) {
                    tile = Tileset.LOCKED_DOOR;
                    this.door = new Pos(xi, yi);
                } else {
                    System.out.println("loadGame error: " + c + ", x =" + xi + "y = " + yi);
                    return;
                }

                world[xi][yi] = tile;
            }
        }
        step = inTxt.readInt();
    }

    public void saveGame(TETile[][] world) {
        //https://www.programiz.com/java-programming/printwriter

        try {
            PrintWriter output = new PrintWriter(SAVEPATH);
            for (int y = HEIGHT - 1; y >= 0; y -= 1) {
                for (int x = 0; x < WIDTH; x += 1) {
                    output.print(world[x][y].character());
                }
                output.println("");
            }

            output.println(step);
            output.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public boolean checkNeighWithFilled(int xi, int yi, TETile[][] world) {
        for (int nx = xi - 1; nx <= xi + 1; nx++) {
            for (int ny = yi - 1; ny <= yi + 1; ny++) {
                if (nx != xi || ny != yi) {
                    if (GridUtil.checkEqual(nx, ny, Tileset.FLOOR, world)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public void addWall(TETile[][] world) {
        for (int xi = 0; xi < WIDTH; xi++) {
            for (int yi = 0; yi < HEIGHT; yi++) {
                if (world[xi][yi].equals(Tileset.NOTHING)) {
                    if (checkNeighWithFilled(xi, yi, world)) {
                        world[xi][yi] = Tileset.WALL;
                    }
                }
            }
        }
    }

//    public static void main(String[] args) {
//        Engine engine = new Engine();
//        engine.interactWithKeyboard();
//    }
}

