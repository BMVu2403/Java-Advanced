package PTIT_CNTT1_IT203B_Session02;

// Interface thứ nhất
interface UserActions {
    default void logActivity(String activity) {
        System.out.println("User activity: " + activity);
    }
}

// Interface thứ hai
interface AdminActions {
    default void logActivity(String activity) {
        System.out.println("Admin activity: " + activity);
    }
}

// Lớp thực thi cả hai interface
class SuperAdmin implements UserActions, AdminActions {

    // BẮT BUỘC phải override để giải quyết Diamond Problem
    @Override
    public void logActivity(String activity) {
        // Lập trình viên chủ động chọn logic mong muốn
        UserActions.super.logActivity(activity);
        AdminActions.super.logActivity(activity);
    }
}

public class Exercise05 {

    public static void main(String[] args) {

        SuperAdmin superAdmin = new SuperAdmin();
        superAdmin.logActivity("System configuration updated");
    }
}
