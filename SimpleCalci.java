import java.text.DecimalFormat;
import java.util.InputMismatchException;
import java.util.Scanner;

public class SimpleCalci {
    private static final Scanner sc = new Scanner(System.in);
    private static final DecimalFormat df = new DecimalFormat("#.##########");
    private static double memory = 0.0;
    private static boolean isRunning = true;

    public static void main(String[] args) {
        System.out.println("╔══════════════════════════════════════╗");
        System.out.println("║        ADVANCED CALCULATOR           ║");
        System.out.println("║         Java Console Edition         ║");
        System.out.println("╚══════════════════════════════════════╝");
        System.out.println();

        //while (isRunning) {
            try {
                powerMenu();
                String choice = sc.nextLine();
                if (choice.equalsIgnoreCase("on")) {
                    interactiveCalculation();
                } else if (choice.equalsIgnoreCase("off")) {
                    isRunning = false;

                } else {
                    System.out.println("Invaild Entry");
                }

            } catch (Exception e) {
                System.out.println(" An unexpected error occurred: " + e.getMessage());
                System.out.println("Please try again.\n");
            }
       // }

        System.out.println("\n Thank you for using Advanced Calculator!");
        System.out.println(" Calculator session ended successfully.");
        sc.close();
    }

    private static void powerMenu() {
        String pmenu ="┌──────────────────────────────────────┐\n" +
                      "│            Select Option             │\n" +
                      "├──────────────────────────────────────┤\n" +
                      "│   0. OFF                             │\n" +
                      "│   1. ON                              │\n" +
                      "└──────────────────────────────────────┘";

        System.out.println(pmenu);
        // System.out.printf(" Memory: %s\n", df.format(memory)); // safe alternative
        System.out.print(" Enter your choice (on/off): ");
    }

    private static void displayMenu() {
  String menu = "┌──────────────────────────────────────┐\n" +
                "│              MAIN MENU               │\n" +
                "├──────────────────────────────────────┤\n" +
                "│  Basic Operations:                   │\n" +
                "│   1. Addition           (+)          │\n" +
                "│   2. Subtraction        (-)          │\n" +
                "│   3. Multiplication     (*)          │\n" +
                "│   4. Division           (÷)          │\n" +
                "│                                      │\n" +
                "│  Advanced Operations:                │\n" +
                "│   5. Power              (^)          │\n" +
                "│   6. Square Root        (sqrt)       │\n" +
                "│   7. Percentage         (%)          │\n" +
                "│                                      │\n" +
                "│  Memory Functions:                   │\n" +
                "│   8. Store to Memory    (MS)         │\n" +
                "│   9. Recall Memory      (MR)         │\n" +
                "│  10. Clear Memory       (MC)         │\n" +
                "│                                      │\n" +
                "│   0. Exit Calculator                 │\n" +
                "└──────────────────────────────────────┘";

        System.out.println(menu);
        System.out.printf(" Memory: %s\n", df.format(memory)); // safe alternative
        // System.out.print(" Enter your choice (0-10): ");
    }


     private static int getUserChoice() {
        try {
            int choice = sc.nextInt();
            if (choice < 0 || choice > 10) {
                System.out.println(" Invalid choice! Please select 0-10.");
                return -1;
            }
            return choice;
        } catch (InputMismatchException e) {
            System.out.println(" Invalid input! Please enter a number (0-10).");
            sc.nextLine(); // Clear invalid input
            return -1;
        }
    }

    private static void interactiveCalculation() {
        displayMenu();
        System.out.println("");
        double currentResult = getNumberInput("Enter the first number: ");
       // boolean continueCalc = true;

        while (isRunning) {
           // System.out.println("\nCurrent Result: " + df.format(currentResult));
            // System.out.println("\n");
            // System.out.println("Choose operation: +, -, *, /, ^, %, sqrt");
            // System.out.println("Or type 'exit' to return to main menu");

            System.out.print("Enter your choice (0-10): ");
            int input = getUserChoice(); // .trim().toLowerCase();

            /*
             * if (input.equals(0)) {
             * System.out.println("✅ Final Result: " + df.format(currentResult));
             * offerToStoreResult(currentResult);
             * break;
             * }
             */

            double nextNumber = 0;
            boolean needsSecondNumber = true;

            switch (input) {
                case 0:
                    /*
                     * System.out.println(" Final Result: " + df.format(currentResult));
                     * offerToStoreResult(currentResult);
                     * break;
                     */
                    offerToStoreResult(currentResult);
                    isRunning = false;
                    break;

                case 1:
                    nextNumber = getNumberInput("Enter number to add: ");
                    currentResult = addition(currentResult, nextNumber);
                    break;
                case 2:
                    nextNumber = getNumberInput("Enter number to subtract: ");
                    currentResult = subtraction(currentResult, nextNumber);
                    break;
                case 3:
                    nextNumber = getNumberInput("Enter number to multiply: ");
                    currentResult = multiplication(currentResult, nextNumber);
                    break;
                case 4:
                    nextNumber = getNumberInput("Enter number to divide by: ");
                    if (nextNumber == 0) {
                        System.out.println("❌ Error: Division by zero not allowed!");
                        continue; // skip this iteration
                    }
                    currentResult = division(currentResult, nextNumber);
                    break;
                case 5:
                    nextNumber = getNumberInput("Enter exponent: ");
                    currentResult = power(currentResult, nextNumber);
                    break;
                case 7:
                    nextNumber = getNumberInput("Enter percentage: ");
                    currentResult = calculatePercentage(currentResult, nextNumber);
                    break;
                case 6:
                    if (currentResult < 0) {
                        System.out.println("❌ Error: Cannot calculate square root of negative number!");
                        continue;
                    }
                    currentResult = squareRoot(currentResult);
                    needsSecondNumber = false;
                    break;
                case 8:
                    storeToMemory(currentResult);
                    break;
                case 9:
                    recallMemory();
                    break;
                case 10:
                    clearMemory();
                    break;

                default:
                    System.out.println("❌ Invalid operation. Try again.");
                    continue;
            }

            if (needsSecondNumber) {
                System.out.println("");
                System.out.println("Result after operation: " + df.format(currentResult));
               // memory = currentResult;
            } else {
                System.out.println("Result: " + df.format(currentResult));
               // memory = currentResult;
            }
           // offerToStoreResult(currentResult);
        }
    }

    /**
     * Store a number to memory
     */
    private static void storeToMemory(double currentResult) {
       //double number = getNumberInput("Enter number to store in memory: ");
        memory = currentResult;
        System.out.println(" Number " + df.format(memory) + " stored to memory successfully!");
    }

    /**
     * Recall number from memory
     */
    private static void recallMemory() {
        System.out.println(" Memory Recall");
        // System.out.println(repeat("─", 20));
        System.out.println(" Current memory value: " + df.format(memory));
    }

    /**
     * Clear memory
     */
    private static void clearMemory() {
        memory = 0.0;
        System.out.println(" Memory cleared successfully!");
        System.out.println(" Memory is now: " + df.format(memory));
    }

    private static double getNumberInput(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                double number = sc.nextDouble();
                return number;
            } catch (InputMismatchException e) {
                System.out.println(" Invalid input! Please enter a valid number.");
                sc.nextLine(); // Clear invalid input
            }
        }
    }

    /**
     * Offer to store calculation result to memory
     */
    private static void offerToStoreResult(double result) {
        System.out.print(" Store result to memory? (y/n): ");
        sc.nextLine(); // Consume newline
        String choice = sc.nextLine().trim().toLowerCase();

        if (choice.equals("y") || choice.equals("yes")) {
            memory = result;
            System.out.println(" Result stored to memory!");
        }
    }

    // ═══════════════════════════════════════
    // CALCULATION METHODS
    // ═══════════════════════════════════════

    /**
     * Addition operation
     * 
     * @param a First number
     * @param b Second number
     * @return Sum of a and b
     */
    public static double addition(double a, double b) {
        return a + b;
    }

    /**
     * Subtraction operation
     * 
     * @param a First number
     * @param b Second number
     * @return Difference of a and b
     */
    public static double subtraction(double a, double b) {
        return a - b;
    }

    /**
     * Multiplication operation
     * 
     * @param a First number
     * @param b Second number
     * @return Product of a and b
     */
    public static double multiplication(double a, double b) {
        return a * b;
    }

    /**
     * Division operation
     * 
     * @param a Dividend
     * @param b Divisor
     * @return Quotient of a divided by b
     */
    public static double division(double a, double b) {
        return a / b;
    }

    /**
     * Power operation
     * 
     * @param base     Base number
     * @param exponent Exponent
     * @return Base raised to the power of exponent
     */
    public static double power(double base, double exponent) {
        return Math.pow(base, exponent);
    }

    /**
     * Square root operation
     * 
     * @param number Number to find square root of
     * @return Square root of the number
     */
    public static double squareRoot(double number) {
        return Math.sqrt(number);
    }

    /**
     * Calculate percentage of a number
     * 
     * @param number     The base number
     * @param percentage The percentage to calculate
     * @return The percentage value of the number
     */
    public static double calculatePercentage(double number, double percentage) {
        return (number * percentage) / 100;
    }

}
