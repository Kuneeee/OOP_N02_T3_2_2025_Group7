public class TestOuterClass {
    public static void main(String[] args) {
        // Tạo đối tượng OuterClass
        OuterClass outer = new OuterClass();

        // Tạo đối tượng InnerClass thông qua OuterClass
        OuterClass.InnerClass inner = outer.new InnerClass();

        // Gọi phương thức hiển thị
        inner.display();
    }
}
