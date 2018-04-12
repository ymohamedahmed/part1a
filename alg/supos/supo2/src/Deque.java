import java.util.ArrayList;

public class Deque<T> {
    private ArrayList<T> mBuffer = new ArrayList<>();
    private int mSize;

    // Points to first empty location at the 'front'
    private int mHead = 0;
    // Points to first empty location at the 'back'
    private int mTail = 0;

    public Deque(int size) {
        mSize = size;
    }

    public void addEnd(T insert) {
        mBuffer.add(mTail, insert);
        mTail = (mTail + 1) % mSize;
    }

    public void addFront(T insert) {
        mBuffer.add(mHead, insert);
        mHead = (mHead == 0) ? mSize - 1 : mHead - 1;
    }

    public T peekEnd() throws EmptyDequeException {
        if(mHead == mTail && mHead == 0) {
            throw new EmptyDequeException();
        }else{
            return mBuffer.get(mTail-1);
        }
    }

    public T peekFront() throws EmptyDequeException {
        if(mHead == mTail && mHead == 0) {
            throw new EmptyDequeException();
        }else{
            return mBuffer.get(mHead+1);
        }
    }

    public T popEnd() throws EmptyDequeException {
        if(mHead == mTail && mHead == 0) {
            throw new EmptyDequeException();
        }else{
            int temp = mTail;
            mTail = (mTail == 0) ? mSize - 1 : mTail - 1;
            return mBuffer.get(temp);
        }
    }
    public T popFront() throws EmptyDequeException {
        if(mHead == mTail && mHead == 0) throw new EmptyDequeException();
        else{
            int temp = mHead;
            mHead = (mHead + 1) % mSize;
            return mBuffer.get(temp);
        }
    }

    static class EmptyDequeException extends Exception{}
}
