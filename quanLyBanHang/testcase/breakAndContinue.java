<<<<<<< HEAD:quanLyBanHang/testcase/breakAndContinue.java
package quanLyBanHang.testcase;

=======
>>>>>>> 6759a82 (Cap nhat thu vien OOP):testcase/breakAndContinue.java
public class breakAndContinue {
    public static void test()
    {
        for (int i = 1; i < 100; i ++)
        {
            if (i == 74) break;
            if (i % 9 != 0) continue;
            System.out.println(i);
        }
    }
}
