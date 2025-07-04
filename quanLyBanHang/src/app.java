
<<<<<<< HEAD
=======
<<<<<<< HEAD
import quanLyBanHang.review.breakAndContinue;
import quanLyBanHang.review.number;
import quanLyBanHang.review.passObject;
import quanLyBanHang.review.randNum;
import quanLyBanHang.review.shortCircuit;
import quanLyBanHang.review.switchCake;
=======
import quanLyBanHang.testcase.breakAndContinue;
import quanLyBanHang.testcase.number;
import quanLyBanHang.testcase.passObject;
import quanLyBanHang.testcase.randNum;
import quanLyBanHang.testcase.shortCircuit;
import quanLyBanHang.testcase.switchCake;
=======
// import testcase.breakAndContinue; // Removed invalid import
>>>>>>> 6759a82 (Cap nhat thu vien OOP)
>>>>>>> 34d5de85b81e69916ae59ae1bb3406c97784dc6e

>>>>>>> refs/remotes/origin/main
public class app
{
   public static void main(String[] args) 
   {
      /*
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
      */

      hangHoa myObj = new hangHoa(1, "banh mi", "long dep zai", 2025, "do an");
      System.out.println("hang hoa id: " + myObj.hang_hoa_ID);
      System.out.println("ten hang hoa: " + myObj.ten_hang_hoa);
      System.out.println("nha san xuat: " + myObj.nha_sx);
      System.out.println("loai hang hoa: " + myObj.loai_hang_hoa);
      System.out.println("nam san xuat: " + myObj.nam_sx);
   }
}

