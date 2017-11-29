package supo3;


public interface OOPList<T>{
	public T nth(int n);
	public void update(int n, T o);
	public void append(T o);
	public int length();
	public void delete(int n);
}
