/* ADD COMMENTS TO THIS CLASS, DO NOT CHANGE ANY CODE! */
import java.util.*;

public class BankAccount {

  private int accountNumber;
  private String ownerName;
  private double balance;
  private String type; // Personal, Business, Charitable

  public BankAccount() {
    accountNumber    = 0;
    ownerName = "";
    balance = 0.0;
    type = "Personal";
  }
 

  public BankAccount(int number, String name, double initialDeposit, String type) {
    accountNumber    = number;
    ownerName = name;
    balance = initialDeposit;
    this.type = type;  // Why does 'this' need to be used here??
  }

  public int getAccountNumber() {
    return accountNumber;
  }

  public void setAccountNumber(int number) {
    accountNumber = number;
  }

  public String getOwnerName() {
    return ownerName;
  }

  public void setOwnerName(String name) {
    ownerName = name;
  }

  public double getBalance() {
    return balance;
  }

  public void setBalance(double newAmount) {
    balance = newAmount;
  }

  public String getType() {
	return type;
  }

  // observe there is no setType because the bank's practice requires
  //  closing an account and creating a new one of a different type.

  public void deposit(double amount) {
    balance += amount;
  }

  public void withdrawl(double amount) {
    balance -= amount;
  }

  public String toString() {
    return type + ": " + accountNumber + " " + ownerName + " " + balance;
  }
}
