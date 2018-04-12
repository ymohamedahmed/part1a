import java.util.Stack;

public class Palindrome {
    public static boolean testPalindrome(String toTest) {
        boolean result = true;
        Stack<Character> workingStack = new Stack<>();
        // Iterate over the first half of the string
        for (int i = 0; i < toTest.length() / 2; i++) {
            workingStack.push(toTest.charAt(i));
        }
        // Iterate over second half of the string
        for (int i = (toTest.length() + 1) / 2; i < toTest.length(); i++) {
            Character c = workingStack.pop();
            if (!c.equals(toTest.charAt(i))) {
                result = false;
                break;
            }
        }
        return result;
    }

    public static void main(String[] args) {
        System.out.println(testPalindrome("ab"));
        System.out.println(testPalindrome("aba"));
        System.out.println(testPalindrome("abba"));
        System.out.println(testPalindrome("abbb"));
    }
}
