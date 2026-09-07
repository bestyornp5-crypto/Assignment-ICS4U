import java.util.Scanner;
public class KrabApp {
    public static void main(String[] args) {
        // Here, I declared the variable for the programing
        int option,historyCount =0 ,historyNum = 0;
        long points = 0;
        double distant, tolls, tripcost,fprice,fdelivery, tax,discount;
        String usepoint;
        String[] history = new String[5];
        // The requirement said make a array with 5 blocks
        Scanner scanner = new Scanner(System.in);
        // I use do loop for loop until user press key 4
        do {
            // Here is the format of option which is shown for user when they run
            System.out.println("=====================");
            System.out.printf("%s%14s%6s\n",'|',"KRAB APP", '|');
            System.out.println("=====================");
            System.out.println("1 - Krab Car");
            System.out.println("2 - Krab Food");
            System.out.println("3 - Info & History");
            System.out.println("4 - Exit");
            System.out.print("Your Option: ");
            // I want to use to check by hasNextInt() --> true if data input from keyboard is int
            //                                        --> false if data input from keyboard is others
            if (scanner.hasNextInt()){ // I check if input (option) from keyboard is integer , it will accept
                option = scanner.nextInt();
                if (option < 1 || option > 4) { // If option from 1 to 4, will continue
                    System.out.println("WARNING: Invalid Option!");
                }
            }else {
                System.out.println("WARNING: Invalid Option!");
                scanner.next();
                option = 0; // If not, it will warn
            }
            switch (option) {
                case 1: // This case for Krab Car
                    // user will input the data for krab car here
                    System.out.println("---------------------");
                    System.out.printf("%s%14s%6s\n",'|',"KRAB CAR", '|');
                    System.out.println("---------------------");
                    System.out.print("Enter distant (KM): ");
                    distant = scanner.nextDouble();
                    System.out.print("Enter toll (rm): ");
                    tolls = scanner.nextDouble();
                    // I will check the distance and the tolls is possible or not
                    if (distant <= 0 || tolls < 0) {
                        System.out.println("WARNING: Invalid distance or tolls!");
                    } else {
                        // If everything is suitable , we will start to calculate
                        // Before calculate, I will ask user that do they want to use Points for Discount
                        System.out.printf("Have %d Krab Points. Use it? (Y/N): ", points);
                        scanner.nextLine();
                        usepoint = scanner.nextLine().toLowerCase();
                        // I got scared that user will input y Y n N , so I will change to lowercase for all
                        if (!(usepoint.equals("y") || usepoint.equals("n"))) {
                            System.out.println("WARNING: Invalid Option!");
                            // If they press sth which are different to y Y n N,, it will warning
                        } else {
                            if (distant <= 5) {
                                tripcost = 5.0; // They requirement say like that
                            } else {
                                tripcost = Math.round((6 * Math.sqrt(0.5 * distant - 2) + 5) * 100.0) / 100.0;
                                // Why Math.round(I * 100.0) / 100.0
                                // I want to take 2 decimal for this number directly
                                //Ex: Math.round(222.7797 * 100) / 100 = 22278 / 100.0 = 222.78
                                // This is how I round up with 2 decimal, just for easily calculate next step
                            }
                            discount = 0;
                            double temporary = tripcost;
                            boolean usedPoints = false;
                            // I declare usedPoints by boolean for the next step when I will check to pu into array
                            // usedPoints helps to calculate used discount or not also
                            if (usepoint.equals("y") && points >= 100) {
                                usedPoints = true;
                                discount = (points / 100) * 5;
                                points = 0;
                                // When I used points for discount and enough points
                                if (discount >= tolls + tripcost) {
                                    tolls = 0;
                                    tripcost = 0;
                                } else if (discount >= tripcost) {
                                    // if discount only higher than tripcost, I will substract for tripcost and the remaining I substract for tolls
                                    tolls -= (discount - tripcost);
                                    tripcost = 0;
                                } else {
                                    tripcost -= discount;
                                }
                            } else if (usepoint.equals("y")) { // if the points < 100, it will warn and go back the menu
                                System.out.println("WARNING: Invalid Option!");
                                break; // stop case 1 and go back menu
                            }
                            // if nothing happen, we will calculate the points will we get
                            long earnPoints;
                            if (usedPoints) // if use discount --> no points will we get, and the points remaining = 0
                                earnPoints = 0;
                            else {
                                earnPoints = Math.round((tripcost + tolls) * 10);
                                // That why I want to round 2f tripcost before.
                                points += earnPoints; // if we didn't use point for discount --> We will get points
                            }
                            System.out.printf("%-19s:%12.2f\n", "Trip Cost", temporary);
                            System.out.printf("%-19s:%12.2f\n", "Toll", tolls);
                            if (usepoint.equals("y")) { // Only print when we pressed Y y
                                System.out.printf("%-19s: -%10.2f\n", "Discount", discount);
                            }
                            System.out.print("--------------------------------\n");
                            System.out.printf("%-19s:%12.2f\n", "Total", tripcost + tolls);
                            System.out.printf("%-19s:%12d\n", "Krab Points Earned", earnPoints);
                            historyNum++;
                            String newHis;
                            if (usedPoints ) { // String Format for using Points to get Discounts
                                newHis = String.format("%d%9s%-4s: Charged %.2f rm, discounted %.2f rm", historyNum, " ", "CAR", tolls + tripcost, discount);
                            } else { // String Format for not using Points to get Discounts
                                newHis = String.format("%d%9s%-4s: Charged %.2f rm and earned %d Krab Points", historyNum, " ", "CAR",tripcost + tolls , earnPoints);
                            }
                            if (historyCount < 5) {
                                history[historyCount] = newHis;
                                historyCount++;
                                // Explain for variable historyCount
                                // the maximum value of historyCount can get is = 5, but if it = 5 we must to update the array by loop
                                // Because the maximum index of array if 4, I use historyCount only for 5 previous time using to store data
                                // About the order of history, I use variable "historyNum" --> Domain [1;infinity]
                            } else {
                                for (int i = 0; i < 4; i++) {
                                    history[i] = history[i + 1];
                                   //The history[2] will become history[1]
                                       // ......[2] will jump to .....[4]
                                        //.................
                                        //.......[4] will jump to[3]
                                }
                                history[4] = newHis;// And I will push the final to history[4]
                                // It is like data structure Queue : FIFO
                            }
                        }
                    }
                    break;
                case 2:
                    System.out.println("---------------------");
                    System.out.printf("%s%14s%6s\n",'|',"KRAB Food", '|');
                    System.out.println("---------------------");
                    // Here for input the data for food Krab
                    System.out.print("Enter Food Price  : ");
                    fprice = scanner.nextDouble();
                    System.out.print("Enter distant (KM): ");
                    distant = scanner.nextDouble();
                    if (fprice <= 0 || distant <= 0) {
                        System.out.println("WARNING: Invalid Option!");
                    } else {
                        // Calculate the cost of food deliver , earnedpoints and tax
                        fdelivery = (double) 15 / 4 * distant;
                        tax = 0.12 * fprice;
                        System.out.printf("%-19s:%12.2f\n", "Food Cost", fprice);
                        System.out.printf("%-19s:%12.2f\n", "Tax", tax);
                        System.out.printf("%-19s:%12.2f\n", "Delivery Fee", fdelivery);
                        System.out.print("--------------------------------\n");
                        System.out.printf("%-19s:%12.2f\n", "Total", fprice + tax + fdelivery);
                        // Because krab food points is round down , so I use long ro round , because it is only take the whole number, never take the decimal
                        long foodPoints = (long) (fprice * 3 );
                        points += foodPoints;
                        System.out.printf("%-19s:%12d\n", "Krab Points Earned", foodPoints);
                        historyNum++;
                        String newHis = String.format("%d%13s: Charged %.2f rm and earned %d Krab Points",historyNum,"FOOD",fprice + tax + fdelivery,foodPoints);
                        if (historyCount < 5){
                            // Same with case 1, only different the data will be assigned
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
                    System.out.println();
                    System.out.printf("%-5s%s%15s\n","Hist","#","Description");
                    System.out.printf("---------------------------------------------------------------\n");
                    if (historyCount == 0) {
                        System.out.println("(No history at the moment!)");
                        //If history is empty
                    } else {
                        for (int i = historyCount - 1; i >= 0; i--) {
                            System.out.println(history[i]);
                        }
                    }
                    break;
                case 4:
                    System.out.println("Thank you for using Krab App");
            }
        }while (option != 4);
    }
}
