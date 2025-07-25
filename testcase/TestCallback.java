public class TestCallback {
    public static void main(String[] args) {
        // Tạo đối tượng OuterClass
        OuterClass outer = new OuterClass();

        // Tạo đối tượng InnerClass từ OuterClass
        OuterClass.InnerClass inner = outer.new InnerClass();

        // Gọi phương thức có callback
        inner.doSomethingWithCallback(new Callback() {
            @Override
            public void onDone(String message) {
                System.out.println("Callback được gọi: " + message);
            }
        });
    }
}
