package uk.ac.cam.ym346.tick1;
public class MaxCharHeap implements MaxCharHeapInterface {

    private int mElementCount = 0;
    private char[] mHeap;

    public MaxCharHeap(String s){
        mHeap = s.toLowerCase().toCharArray();
        mElementCount = mHeap.length;
        heapify();
    }
    private void heapify(){
        for(int i = mElementCount-1; i > 0; i--){
            int parentIndex = i%2 == 0 ? (i-2)/2 : (i-1)/2;
            char child = mHeap[i];
            char parent = mHeap[parentIndex];
            if(Character.compare(child, parent) > 0){
               mHeap[i] = parent;
               mHeap[parentIndex] = child;
            }
        }
    }
    @Override
    public char getMax() throws EmptyHeapException {
        if(mElementCount == 0){
            throw new EmptyHeapException();
        }else{
            mElementCount--;
            char max = mHeap[0];
            mHeap[0] = mHeap[mElementCount];
            heapify();
            return max;
        }
    }

    @Override
    public void insert(char i) {
       char toInsert = Character.toLowerCase(i);
       if(mElementCount == mHeap.length){
          char[] newArray = new char[mHeap.length*2];
          for(int j = 0; j < mHeap.length; j++){
              newArray[j] = mHeap[j];
          }
          mHeap = newArray;
       }
       mHeap[mElementCount++] = toInsert;
       heapify();
    }

    @Override
    public int getLength() {
        return mElementCount;
    }
    public static void main(String[] args){
        MaxCharHeap m = new MaxCharHeap("AbCdEfG");
    }
}
