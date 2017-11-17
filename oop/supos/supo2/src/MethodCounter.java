import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

public class MethodCounter {
	public static void main(String[] args) {
		Class arrayLife = ArrayLife.class;
		Class pattern = Pattern.class;
		
		Method[] arrayMeth = arrayLife.getDeclaredMethods();
		for(Method m : arrayMeth) {
			if(m.getModifiers() == Modifier.PRIVATE) {
				System.out.println(m.getName() + "  private");
			}else if(m.getModifiers() == Modifier.PUBLIC) {
				System.out.println(m.getName() + " public");
			}
		}

		Method[] patternMeth = pattern.getDeclaredMethods();
		for(Method m : patternMeth) {
			if(m.getModifiers() == Modifier.PRIVATE) {
				System.out.println(m.getName() + "  private");
			}else if(m.getModifiers() == Modifier.PUBLIC) {
				System.out.println(m.getName() + " public");
			}
		}

	}
}
