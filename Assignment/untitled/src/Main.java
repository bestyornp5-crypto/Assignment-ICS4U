//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
import java.util.Scanner;
public class Main{
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int option;
        do {
            System.out.println("=====================");
            System.out.printf("%s%14s%6s\n", '|', "KRAB APP", '|');
            System.out.println("=====================");
            System.out.println("1 - Krab Car");
            System.out.println("2 - Krab Food");
            System.out.println("3 - Info & History");
            System.out.println("4 - Exit");
            System.out.print("Your Option: ");
            option = scanner.nextInt();
            System.out.println();
            switch (option) {
                case 1:
                    System.out.println("---------------------");
                    System.out.printf("%s%14s%6s\n", '|', "KRAB APP", '|');
                    System.out.println("---------------------");
                    System.out.print("Enter distant (KM): ");
                    double distant = scanner.nextDouble();
                    System.out.println();
                    System.out.print("Enter toll (rm): ");
                    double toll = scanner.nextDouble();
                    System.out.println();
                case 4:
                    System.out.println("Thank you for using Krab App");
                    break;
                default:
                    System.out.println("Invalid Option !");
            }
        } while(option != 4);

    }
}