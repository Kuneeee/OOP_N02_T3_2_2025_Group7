package testcase;

public class breakAndContinue {
    public static void main(String[] srgs)
    {
        for (int i = 0; i <= 100; i ++)
        {
            if (i == 74) break;
            if (i % 9 != 0) continue;
            System.out.println(i);
        }
    }
}
