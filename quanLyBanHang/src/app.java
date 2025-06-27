import quanLyBanHang.testcase.breakAndContinue;
import quanLyBanHang.testcase.number;
import quanLyBanHang.testcase.passObject;
import quanLyBanHang.testcase.randNum;

public class app
{
   public static void main(String[] args) 
   {
      breakAndContinue.test();

      number n = new number();
      passObject.f(n);
      System.out.println(n.i);

      int a;
      a = randNum.random();
      System.out.println("random number " + a);
   }
}

