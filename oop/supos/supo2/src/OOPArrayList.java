
public class OOPArrayList implements OOPList{
	private Object[] array = new Object[1];

	@Override
	public Object nth(int n) {
		return array[n];
	}

	@Override
	public void update(int n, Object o) {
		array[n] = o;
	}

	@Override
	public void append(Object o) {
		boolean appended = false;
		for(int i = 0; i < array.length; i++) {
			if(array[i] == null) {
				array[i] = o;
				appended = true;
				break;
			}
		}
		// Case where array runs out of size
		// Double array and copy elements across to new array
		if(!appended) {
			Object[] newArr = new Object[array.length * 2];
			for(int i = 0; i < array.length; i++) {
				newArr[i] = array[i];
			}
			newArr[array.length + 1] = o;
			array = newArr;
		}
	}

	@Override
	public int length() {
		int n = 0;
		for(int i = 0; i < array.length; i++) {
			if(array[i] == null) {
				break;
			}
			n++;
		}
		return n;
	}

	@Override
	public void delete(int n) {
		array[n] = null;
		// delete element then shift all the elements down
		for(int i = n+1; i < array.length; i++) {
			array[i-1] = array[i];
		}
	}
}
