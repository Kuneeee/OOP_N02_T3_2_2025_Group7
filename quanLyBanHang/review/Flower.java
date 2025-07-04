<<<<<<< HEAD:quanLyBanHang/review/Flower.java
package quanLyBanHang.review;
public class Flower{
    int petalCount = 0;
    String s = new String("null");
    Flower(int petal) { petalCount = petal; }
=======
package quanLyBanHang.testcase;

public class Flower{
    int petalCount = 0;
    String s = new String("null");
    Flower(int petals) { petalCount = petals; }
>>>>>>> refs/remotes/origin/main:quanLyBanHang/testcase/Flower.java
    Flower(String ss){s=ss;}
    Flower(String s, int petal){
        this(petal);
        this.s=s;
    }
    Flower() { this("hi",47);}
}