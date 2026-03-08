public class Exercise05 {

    // Custom Exception
    static class InvalidAgeException extends Exception {
        public InvalidAgeException(String msg) {
            super(msg);
        }
    }

    // Lop User
    static class User {
        private int age;

        public void setAge(int age) throws InvalidAgeException {
            if (age < 0) {
                throw new InvalidAgeException("Tuoi khong the am!");
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
            u.setAge(-10);
            System.out.println("Tuoi cua user la: " + u.getAge());
        } catch (InvalidAgeException e) {
            System.out.println("Loi: " + e.getMessage());
            e.printStackTrace();
        }

        System.out.println("Chuong trinh van tiep tuc chay...");
    }
}
