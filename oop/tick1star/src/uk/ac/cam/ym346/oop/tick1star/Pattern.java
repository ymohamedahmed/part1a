package uk.ac.cam.ym346.oop.tick1star;

public class Pattern{

    private String mName;
    private String mAuthor;
    private int mWidth;
    private int mHeight;
    private int mStartCol;
    private int mStartRow;
    private String mCells;

    public String getName(){
        return mName;
    }

    public String getAuthor(){
        return mAuthor;
    }

    public int getWidth(){
        return mWidth;
    }

    public int getHeight(){
        return mHeight;
    }

    public int getStartColumn(){
        return mStartCol;
    }

    public int getStartRow(){
        return mStartRow;
    }

    public String getCells(){
        return mCells;
    }

    public Pattern(String format){
        String[] input = format.split(":");
        mName = input[0];
        mAuthor = input[1];
        mWidth = Integer.parseInt(input[2]);
        mHeight = Integer.parseInt(input[3]);
        mStartCol = Integer.parseInt(input[4]);
        mStartRow = Integer.parseInt(input[5]);
        mCells = input[6];
    }
    
    public void initialise(boolean[][] world){
        String[] rows = mCells.split(" ");
        for(int i = 0; i < rows.length; i++){
           char[] row = rows[i].toCharArray();
           for(int x = 0; x < row.length; x++){
               int val = Integer.parseInt(String.valueOf(row[x]));
               if(val == 1){
                   world[i+mStartRow][x+mStartCol] = true;
               }
           }
        }
    }
    
    public static void main(String[] args){
        Pattern pattern = new Pattern(args[0]);
        System.out.println("Name: " + pattern.getName() + 
                "\n Author: " + pattern.getAuthor()+ "\n Width: " + pattern.getWidth() 
                + "\n Height: " + pattern.getHeight() + "\n StartCol: " + 
                pattern.getStartColumn() + "\n StartRow: " + pattern.getStartRow() + "\n Pattern: " + pattern.getCells());
    }
}
