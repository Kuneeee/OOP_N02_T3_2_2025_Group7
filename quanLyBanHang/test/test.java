class Number {
        int i;
    }
public class test {
    static void f(Number m) {
        m.i = 15;
    }
    public static void main(String[] args) {
        Number n = new Number();
        n.i = 14;
        test.f(n);
        System.out.println(n.i);
    }
}

// see u guy next time 