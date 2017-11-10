package uk.ac.cam.ym346.prejava.ex2;
public class TinyLife{
    public static void main(String[] args) throws java.io.IOException{
        boolean live = computeCell(0x20A0600000000000L,Integer.decode(args[0]),Integer.decode(args[1]));
        System.out.println(live);
       //play(Long.decode(args[0]));
    }
    public static boolean getCell(long world, int col, int row){
        if(row > 7 || row < 0 || col < 0 || col > 7){
            return false;
        }
        int position = 8*row + col;
        return PackedLong.get(world, position);
    }
    public static long setCell(long world, int col, int row, boolean value){
        if(row > 7 || row < 0 || col < 0 || col > 7){
            return 0;
        }
        int position = 8*row + col;
        return PackedLong.set(world,position, value); 
    }
    public static void print(long world) { 
       System.out.println("-"); 
       for (int row = 0; row < 8; row++) { 
          for (int col = 0; col < 8; col++) {
             System.out.print(getCell(world, col, row) ?"#" : "_"); 
          }
          System.out.println(); 
       } 
    }
    public static int countNeighbours(long world, int col, int row){
        int count = 0;
        for(int x= col - 1; x <= col + 1; x++){
            for(int y = row - 1; y <= row+1; y++){
                if(!(x==col && y == row)){
                    if(getCell(world, x, y)){
                        count++;
                    }
                }
            }
        }
        return count;
    }
    public static boolean computeCell(long world,int col,int row) {

       // liveCell is true if the cell at position (col,row) in world is live
       boolean liveCell = getCell(world, col, row);
        
       // neighbours is the number of live neighbours to cell (col,row)
       int neighbours = countNeighbours(world, col, row);

       // we will return this value at the end of the method to indicate whether 
       // cell (col,row) should be live in the next generation
       boolean nextCell = false;
        
       //A live cell with less than two neighbours dies (underpopulation)
       if (neighbours < 2) {
          nextCell = false;
       }
     
       //A live cell with two or three neighbours lives (a balanced population)
       //TODO: write a if statement to check neighbours and update nextCell
       if(liveCell && (neighbours == 2 || neighbours == 3)){
           nextCell = true;       
       }       
       //A live cell with with more than three neighbours dies (overcrowding)
       //TODO: write a if statement to check neighbours and update nextCell
       if(liveCell && neighbours > 3){
           nextCell = false;
       }
       //A dead cell with exactly three live neighbours comes alive
       //TODO: write a if statement to check neighbours and update nextCell
       if(!liveCell && neighbours==3){
           nextCell = true;
       }
       return nextCell;
    }
    public static long nextGeneration(long world){
        long newWorld = world;
        for(int col = 0; col < 8; col++){
            for(int row = 0; row < 8; row++){
               newWorld = setCell(newWorld, col, row, computeCell(world, col, row)); 
            }
        }
        return newWorld;
    }
    public static void play(long world) throws java.io.IOException {
       int userResponse = 0;
       while (userResponse != 'q') {
          print(world);
          userResponse = System.in.read();
          world = nextGeneration(world);
       }
    }
}
