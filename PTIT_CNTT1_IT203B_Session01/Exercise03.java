public class Exercise03 {

    static class User {
        private int age;

        public void setAge(int age) {
            if (age < 0) {
                throw new IllegalArgumentException("Tuoi khong the am!");
            }
            this.age = age;
        }

        public int getAge() {
            return age;
        }
    }

    public static void main(String[] args) {
        User u = new User();

        try {
            u.setAge(-5);
            System.out.println("Tuoi cua user la: " + u.getAge());
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }

        System.out.println("Chuong trinh van tiep tuc chay...");
    }
}

