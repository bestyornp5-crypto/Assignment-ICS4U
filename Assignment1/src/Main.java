import java.util.Scanner;
public class Main {
    public static void main(String[] args) {
        int option,historyCount =0 ,historyNum = 0;
        long points = 0;
        double distant, tolls, tripcost,fprice,fdelivery, tax,discount;
        String usepoint;
        String[] history = new String[5];
        Scanner scanner = new Scanner(System.in);
        do {
            System.out.println("=====================");
            System.out.printf("%s%14s%6s\n",'|',"KRAB APP", '|');
            System.out.println("=====================");
            System.out.println("1 - Krab Car");
            System.out.println("2 - Krab Food");
            System.out.println("3 - Info & History");
            System.out.println("4 - Exit");
            System.out.print("Your Option: ");
            if (scanner.hasNextInt()){
                option = scanner.nextInt();
                if (option < 1 || option > 4) {
                    System.out.println("Invalid option!");
                }
            }else {
                System.out.println("Invalid option !");
                scanner.next();
                option = 0; // To continue to loop
            }
            switch (option){
                case 1:
                    // Bao remeber that points is long variable, so divide 10 , I will get whole part
                    System.out.println("---------------------");
                    System.out.printf("%s%14s%6s\n",'|',"KRAB APP", '|');
                    System.out.println("---------------------");
                    System.out.print("Enter distant (KM): ");
                    distant = scanner.nextDouble();
                    System.out.print("Enter toll (rm): ");
                    tolls = scanner.nextDouble();
                    if (distant <= 0){
                        System.out.println("Invalid distance !");
                    } else if (tolls <= 0) {
                        System.out.println("Invalid tolls !");
                    } else{
                        System.out.printf("Have %d Krab Points. Use it? (Y/N): ", points);
                        scanner.nextLine();
                        usepoint = scanner.nextLine().toLowerCase();
                        if (!(usepoint.equals("y") || usepoint.equals("n"))) {
                            System.out.println("Invalid option !");
                            System.out.println("Your point is not enough, so we save the initial cost !");
                        }else {
                            if (distant <= 5) tripcost = 5.0;
                            else tripcost = Math.round((6 * Math.sqrt(0.5 * distant - 2) + 5) * 100.0) / 100.0;
                            discount = 0;
                            double temporary = tripcost;
                            boolean usedPoints  = false;
                            if (usepoint.equals("y") && points >= 100) {
                                usedPoints = true;
                                discount = (points / 100) * 5;
                                points = 0;
                                if (discount >= tolls + tripcost) {
                                    tolls = 0;
                                    tripcost = 0;
                                } else if ( discount >= tripcost) {
                                    tolls -= (discount - tripcost);
                                } else {
                                    tripcost -= discount;
                                }
                            } else if (usepoint.equals("y"))
                                System.out.println("Your point is not enough, so we save the initial cost !");
                            long earnPoints;
                            if (usedPoints)
                                earnPoints =0;
                            else {
                                earnPoints = Math.round(((tripcost + tolls) * 10));
                                points += earnPoints;
                            }

                            System.out.printf("%-19s:%12.2f\n", "Trip Cost", temporary);
                            System.out.printf("%-19s:%12.2f\n", "Toll", tolls);
                            System.out.printf("%-19s: -%10.2f\n","Discount",discount);
                            System.out.print("--------------------------------\n");
                            System.out.printf("%-19s:%12.2f\n", "Total", tripcost + tolls);
                            System.out.printf("%-19s:%12d\n","Krab Points Earned",earnPoints);
                            historyNum++;
                            String newHis = String.format("%d%9s%-4s: Charged %.2f rm and earned %d Krab Points",historyNum," ","CAR",tripcost + tolls,earnPoints);
                            if (historyCount < 5){
                                history[historyCount] = newHis;
                                historyCount++;
                            } else {
                                for (int i = 0; i < 4 ; i++){
                                    history[i] = history[i + 1];
                                }
                                history[4] = newHis;
                            }

                        }
                    }
                    break;
                case 2:
                    System.out.print("Enter Food Price: ");
                    fprice = scanner.nextDouble();
                    System.out.print("Enter distant (KM): ");
                    distant = scanner.nextDouble();
                    if (fprice <= 0 || distant <= 0) {
                        System.out.println("Invalid option !");
                    } else {
                        fdelivery = (double) 15 / 4 * distant;
                        tax = 0.12 * fprice;
                        System.out.printf("%-19s:%12.2f\n", "Food Cost", fprice);
                        System.out.printf("%-19s:%12.2f\n", "Tax", tax);
                        System.out.printf("%-19s:%12.2f\n", "Delivery Fee", fdelivery);
                        System.out.print("--------------------------------\n");
                        System.out.printf("%-19s:%12.2f\n", "Total", fprice + tax + fdelivery);
                        long foodPoints = (long) (fprice * 3 );
                        points += foodPoints;
                        System.out.printf("%-19s:%12d\n", "Krab Points Earned", Math.round((fprice)*3));
                        historyNum++;
                        String newHis = String.format("%d%13s: Charged %.2f rm and earned %d Krab Points",historyNum,"FOOD",fprice + tax + fdelivery,Math.round((fprice) *3));
                        if (historyCount < 5){
                            history[historyCount] = newHis;
                            historyCount++;
                        } else {
                            for (int i = 0; i < 4 ; i++){
                                history[i] = history[i + 1];
                            }
                            history[4] = newHis;
                        }

                    }
                    break;
                case 3:
                    System.out.printf("---------------------\n");
                    System.out.printf("%-6s%-14s%s\n","|","USER INFO","|");
                    System.out.printf("---------------------\n");
                    System.out.printf("%-18s%d\n","Krab Points",points);
                    System.out.printf("%-5s%s%15s\n","Hist","#","Description");
                    System.out.printf("---------------------------------------------------------------\n");
                    for (int i = historyCount - 1; i >= 0; i--) {
                        System.out.println(history[i]);
                    }
                    break;
                case 4:
                    System.out.println("Thank you for using Krab App");
            }
        }while (option != 4);
    }
} // Restrict 182 line
