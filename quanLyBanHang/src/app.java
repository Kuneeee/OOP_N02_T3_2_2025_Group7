import java.util.Scanner;

import quanLyBanHang.testcase.breakAndContinue;
import quanLyBanHang.testcase.number;
import quanLyBanHang.testcase.passObject;
import quanLyBanHang.testcase.randNum;
import quanLyBanHang.testcase.shortCircuit;
import quanLyBanHang.testcase.switchCake;

public class app
{
   public static void main(String[] args) 
   {
      Scanner input = new Scanner(System.in);

      breakAndContinue.test();

      number n = new number();
      passObject.f(n);
      System.out.println(n.i);

      int a;
      a = randNum.random();
      System.out.println("random number " + a);

      int b = input.nextInt();
      if (shortCircuit.test1(b) == true) System.out.println("be hon 10");
      else if (shortCircuit.test2(b) == true) System.out.println("be hon 20");
      else if (shortCircuit.test3(b) == true) System.out.println("be hon 30");
      input.close();

      switchCake.test();
   }
}

