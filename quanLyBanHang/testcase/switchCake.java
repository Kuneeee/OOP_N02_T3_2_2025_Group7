package quanLyBanHang.testcase;

public class switchCake {
    public static void main(String[] args) {
        for (int i = 0; i <= 100; i ++)
        {
            char c = (char) (Math.random() * 26 + 'a');
            switch(c)
            {
                case 'a':
                case 'e':
                case 'i':
                case 'o':
                case 'u':
                    System.out.println("Vowel"); break;
                case 'y':
                case 'w':
                    System.out.println("Sometime vowel"); break;
                default:
                    System.out.println("Not a vowel");
            }
        }
    }
}
