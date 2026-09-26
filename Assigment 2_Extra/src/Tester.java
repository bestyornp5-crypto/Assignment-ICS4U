public class Tester {
    public static void main(String[] args){
        StuSys test = new StuSys();
        test.ImportAcct("Mystudent.txt");
        System.out.println(test.GetStudentName("10001"));
        System.out.println(test.GetCourseGradeAt("10001",1));
        test.ChangePassword("10001","11111","91851", "91851");
        test.DisplayDatabase();

    }
}
