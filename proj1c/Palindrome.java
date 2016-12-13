/**
 * Created by Alfred on 07/12/16.
 */
public class Palindrome {
    public static Deque<Character> wordToDeque(String word){
        int i = 0;
        Deque<Character> d = new LinkedListDequeSolution();
        while(i != word.length()) {
            d.addLast(word.charAt(i));
            i += 1;
        }
        return d;
    }
    public static boolean isPalindrome(String word) {
        word = word.toLowerCase();
        Deque<Character> d = Palindrome.wordToDeque(word);
        if(d.size() <= 1) return true;
        Character f = d.removeFirst();
        Character l = d.removeLast();
        if(f == l) {
            return Palindrome.isPalindrome(word.substring(1,word.length()-1));
        }
        else {
            return false;
        }
    }
    public static boolean isPalindrome(String word, CharacterComparator cc) {
        word = word.toLowerCase();
        Deque<Character> d = Palindrome.wordToDeque(word);
        if(d.size() <= 1) return true;
        Character f = d.removeFirst();
        Character l = d.removeLast();
        if(cc.equalChars(f,l)) {
            return Palindrome.isPalindrome(word.substring(1,word.length()-1),cc);
        }
        else {
            return false;
        }
    }

    public static void main(String[] args) {
        Palindrome.wordToDeque("hej");
        System.out.print(Palindrome.isPalindrome("Repaper"));
    }
}
