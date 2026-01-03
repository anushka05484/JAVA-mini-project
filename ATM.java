import java.util.InputMismatchException;
import java.util.Scanner;

public class ATM {
    private Account account;
    private Scanner scanner;

    public ATM(Account account) {
        this.account = account;
        scanner = new Scanner(System.in);
    }

    public void start() {
        System.out.print("Enter your PIN: ");
        int pin = scanner.nextInt();

        if (!account.validatePin(pin)) {
            System.out.println("Incorrect PIN. Access denied.");
            return;
        }

        boolean exit = false;

        while (!exit) {
            displayMenu();
            try {
                int choice = scanner.nextInt();

                switch (choice) {
                    case 1:
                        System.out.println("Current Balance: $" + account.getBalance());
                        break;

                    case 2:
                        System.out.print("Enter amount to withdraw: ");
                        account.withdraw(scanner.nextDouble());
                        break;

                    case 3:
                        System.out.print("Enter amount to deposit: ");
                        account.deposit(scanner.nextDouble());
                        break;

                    case 4:
                        System.out.println("Thank you for using the ATM. Goodbye!");
                        exit = true;
                        break;

                    default:
                        System.out.println("Invalid choice. Select 1–4.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter numeric values.");
                scanner.nextLine(); // clear buffer
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            } finally {
                System.out.println("Transaction complete.\n");
            }
        }
    }

    private void displayMenu() {
        System.out.println("\n__ ATM MENU __");
        System.out.println("1. Check Balance");
        System.out.println("2. Withdraw Money");
        System.out.println("3. Deposit Money");
        System.out.println("4. Exit");
        System.out.print("Choose an option: ");
    }
}
