package uk.ac.cam.ym346.oop.tick1star;

import java.io.IOException;

public class AnimatedLife{
    private int mWidth;
    private int mHeight;
    private boolean[][] mWorld;
    private Pattern mPattern; 
    private OutputAnimatedGif mOutputGif;
    private int mFrames;

    public AnimatedLife(String format, int noOfFrames, String output){
        mPattern = new Pattern(format);
        mHeight=mPattern.getHeight();
        mWidth=mPattern.getWidth();
        mFrames = noOfFrames;
        try {
			mOutputGif = new OutputAnimatedGif(output);
		} catch (IOException e) {
			e.printStackTrace();
		}
        mWorld=new boolean[mHeight][mWidth];
        mPattern.initialise(mWorld);
    }

    public static void main(String[] args) throws java.io.IOException {
       AnimatedLife al = new AnimatedLife(args[0], Integer.parseInt(args[1]), args[2]);
       al.play();
    }

    public boolean getCell(int col, int row){
        if(row < 0 || row >= mHeight) return false;
        if (col < 0 || col >= mWidth) return false;

        return mWorld[row][col];
    }
    public void setCell(int col, int row, boolean value){
        if(!(row < 0 || row >= mHeight || col < 0 || col >= mWidth)){
            mWorld[row][col] = value;
        }
    }
    public void print() { 
       System.out.println("-"); 
       for (int row = 0; row < mWorld.length; row++) { 
          for (int col = 0; col < mWorld[row].length; col++) {
             System.out.print(getCell(col, row) ?"#" : "_"); 
          }
          System.out.println(); 
       } 
    }
    private int countNeighbours(int col, int row){
        int count = 0;
        for(int x= col - 1; x <= col + 1; x++){
            for(int y = row - 1; y <= row+1; y++){
                if(!(x==col && y == row)){
                    if(getCell(x, y)){
                        count++;
                    }
                }
            }
        }
        return count;
    }
    private boolean computeCell(int col,int row) {

       // liveCell is true if the cell at position (col,row) in world is live
       boolean liveCell = getCell(col, row);
        
       // neighbours is the number of live neighbours to cell (col,row)
       int neighbours = countNeighbours(col, row);

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
    public void nextGeneration(){
        boolean[][] nextGeneration = new boolean[mWorld.length][];
        for(int y = 0; y < mWorld.length; ++y){
            nextGeneration[y] = new boolean[mWorld[y].length];
            for(int x = 0; x < mWorld[y].length; ++x){
                boolean nextCell = computeCell(x,y);
                nextGeneration[y][x] = nextCell;
            }
        }
        mWorld = nextGeneration;
    }

    public void play() throws java.io.IOException {
        for(int i = 0; i < mFrames; i++){
            mOutputGif.addFrame(mWorld);
            nextGeneration();
        }
        mOutputGif.close();
    }
}
