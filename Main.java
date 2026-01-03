public class Main {
    public static void main(String[] args) {
        Account account = new Account(1000.0, 1234); // initial balance & PIN
        ATM atm = new ATM(account);
        atm.start();
    }
}
