
import java.util.Scanner;

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
      a = randNum.RandNum();
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

