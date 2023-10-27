package com.pluralsight;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;


public class FinancialTracker {

    private static ArrayList<Transaction> transactions = new ArrayList<Transaction>();
    private static final String FILE_NAME = "transactions.csv";
    private static final String DATE_FORMAT = "yyyy-MM-dd";
    private static final String TIME_FORMAT = "HH:mm:ss";
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern(DATE_FORMAT);
    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern(TIME_FORMAT);

    public static void main(String[] args) {
        loadTransactions(FILE_NAME);
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running) {
            System.out.println("Welcome to TransactionApp");
            System.out.println("Choose an option:");
            System.out.println("D) Add Deposit");
            System.out.println("P) Make Payment (Debit)");
            System.out.println("L) Ledger");
            System.out.println("X) Exit");

            String input = scanner.nextLine().trim();

            switch (input.toUpperCase()) {
                case "D":
                    addDeposit(scanner);
                    break;
                case "P":
                    addPayment(scanner);
                    break;
                case "L":
                    ledgerMenu(scanner);
                    break;
                case "X":
                    running = false;
                    break;
                default:
                    System.out.println("Invalid option");
                    break;
            }
        }

        scanner.close();
    }

    public static void loadTransactions(String fileName) {
        // This method will show transactions from a given file. If any error occurs, an error message will show.



        try {
            BufferedReader reader = new BufferedReader(new FileReader(fileName));
            String line;

            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|");
                if (parts.length == 5) {
                    LocalDate date = LocalDate.parse(parts[0], DATE_FORMATTER);
                    LocalTime time = LocalTime.parse(parts[1], TIME_FORMATTER);
                    String description = parts[2];
                    String vendor = parts[3];
                    double amount = Double.parseDouble(parts[4]);
                    Transaction transaction = new Transaction(date, time, description, vendor, amount);
                    transactions.add(transaction);
                }
            }


            reader.close();

        } catch (Exception name) {
            name.printStackTrace();
        }


    }

    private static void addDeposit(Scanner scanner) {
        // This method will ask user for date, time, vendor, and amount of deposit. Needs to enter date & time in this
        // format: yyyy-MM-dd HH:mm:ss
        System.out.println("Please enter date (yyyy-MM-dd");
        String date = scanner.nextLine();
        System.out.println("Please enter time (HH:mm:ss)");
        String time = scanner.nextLine();
        System.out.println("Please enter the description");
        String description = scanner.nextLine();
        System.out.println("Please enter the name of vendor");
        String vendor = scanner.nextLine();
        System.out.println("Please enter deposit amount");
        double deposit = scanner.nextDouble();
        scanner.nextLine();
        if (deposit < 0)
            System.out.println("Error! Invalid input ! Please try again !");
        System.out.println("\n Deposit amount (enter a positive value) ");

        LocalDate formattedDate = LocalDate.parse(date, DATE_FORMATTER);
        LocalTime formattedTime = LocalTime.parse(time, TIME_FORMATTER);


        try {
            Transaction newDeposit = new Transaction(formattedDate, formattedTime, description, vendor, deposit);
            transactions.add(newDeposit);

            BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME, true));
            String outputLine = newDeposit.getDate() + "|" + newDeposit.getTime() + "|" + newDeposit.getDescription() + "|" + newDeposit.getVendor() + "|" + newDeposit.getAmount();
            writer.write("\n" + outputLine);
            writer.close();
        } catch (Exception e) {
            System.out.println("Error!");
        }
    }

    private static void addPayment(Scanner scanner) {
        //
        System.out.println("Please enter date (yyyy-MM-dd");
        String date = scanner.nextLine();
        System.out.println("Please enter time (HH:mm:ss)");
        String time = scanner.nextLine();
        System.out.println("Please enter the description");
        String description = scanner.nextLine();
        System.out.println("Please enter the name of vendor");
        String vendor = scanner.nextLine();
        System.out.println("Please enter payment amount");
        double payment = scanner.nextDouble();
        scanner.nextLine();
        if (payment < 0)
            System.out.println("Error! Invalid input ! Please try again !");
        System.out.println("\n Payment amount (enter a negative value) ");

        LocalDate formattedDate = LocalDate.parse(date, DATE_FORMATTER);
        LocalTime formattedTime = LocalTime.parse(time, TIME_FORMATTER);

        try {
            Transaction newPayment = new Transaction(formattedDate, formattedTime, description, vendor, payment);
            transactions.add(newPayment);

            BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME, true));
            String outputLine = newPayment.getDate() + "|" + newPayment.getTime() + "|" + newPayment.getDescription() + "|" + newPayment.getVendor() + "|" + newPayment.getAmount();
            writer.write("\n" + outputLine);
            writer.close();
        } catch (Exception e) {
            System.out.println("Error!");
        }


    }

    private static void ledgerMenu(Scanner scanner) {
        boolean running = true;
        while (running) {
            System.out.println("Ledger");
            System.out.println("Choose an option:");
            System.out.println("A) All");
            System.out.println("D) Deposits");
            System.out.println("P) Payments");
            System.out.println("R) Reports");
            System.out.println("H) Home");

            String input = scanner.nextLine().trim();

            switch (input.toUpperCase()) {
                case "A":
                    displayLedger();
                    break;
                case "D":
                    displayDeposits();
                    break;
                case "P":
                    displayPayments();
                    break;
                case "R":
                    reportsMenu(scanner);
                    break;
                case "H":
                    running = false;
                default:
                    System.out.println("Invalid option");
                    break;
            }
        }
    }

    private static void displayLedger() {
        // This method should display a table of all transactions with a column for each detail ex: date,time,amount...
        System.out.println("Transactions");
        for (int i = 0; i < transactions.size(); i++) {
            System.out.println(transactions.get(i));
        }
    }

    private static void displayDeposits() {
        // This method should display a table of all deposits in the `transactions' with column for each detail: date, time...
        System.out.println("Deposits");
        for (int i = 0; i < transactions.size(); i++) {
            if (transactions.get(i).getAmount() > 0) {
                System.out.println(transactions.get(i));
            }

        }
    }

    private static void displayPayments() {
        // This method should display a table of all payments 
        System.out.println("Payments");
        for (int i = 0; i < transactions.size(); i++) {
            if (transactions.get(i).getAmount() < 0) {
                System.out.println(transactions.get(i));
            }
        }
    }

    private static void reportsMenu(Scanner scanner) {
        boolean running = true;
        while (running) {
            System.out.println("Reports");
            System.out.println("Choose an option:");
            System.out.println("1) Month To Date");
            System.out.println("2) Previous Month");
            System.out.println("3) Year To Date");
            System.out.println("4) Previous Year");
            System.out.println("5) Search by Vendor");
            System.out.println("0) Back");

            String input = scanner.nextLine().trim();

            switch (input) {
                case "1":
                    filterTransactionsByDate(LocalDate.now().withDayOfMonth(1), LocalDate.now());
                    // Generate a report for all transactions within the current month,
                    // including the date, vendor, and amount for each transaction.
                    break;

                case "2":
                    // Generate a report for all transactions within the previous month,
                    // including the date, vendor, and amount for each transaction.
                    filterTransactionsByDate(LocalDate.now().withDayOfMonth(1).minusMonths(1).withDayOfMonth(1), LocalDate.now().withDayOfMonth(1).minusDays(1));
                    break;

                case "3":
                    // Generate a report for all transactions within the current year,
                    // including the date, vendor, and amount for each transaction.
                    filterTransactionsByDate(LocalDate.now().withDayOfYear(1), LocalDate.now().withMonth(12).withDayOfMonth(31));

                    break;


                case "4":
                    // Generate a report for all transactions within the previous year,
                    // including the date, vendor, and amount for each transaction.
                    filterTransactionsByDate(LocalDate.now().withDayOfYear(1), LocalDate.now().withMonth(12).withDayOfMonth(31).minusYears(1));
                    break;

                case "5":
                    System.out.println("Enter a vendor name:");
                    String vendor = scanner.nextLine().trim();
                    filterTransactionsByVendor(vendor);
                    // Prompt the user to enter a vendor name, then generate a report for all transactions
                    // with that vendor, including the date, vendor, and amount for each transaction.break;

                    break;
                case "0":
                    running = false;
                default:
                    System.out.println("Invalid option");
                    break;
            }
        }
    }


    private static void filterTransactionsByDate(LocalDate
                                                         startDate, LocalDate endDate) {
        // This method filters the transactions by date and prints a report to the console.
        // It takes two parameters: startDate and endDate, which represent the range of dates to filter by.
        // The method loops through the transactions list and checks each transaction's date against the date range.
        // Transactions that fall within the date range are printed to the console.
        // If no transactions fall within the date range, the method prints a message indicating that there are no results.

        System.out.println("report ");
        for (Transaction transaction : transactions) {
            if (transaction.getDate().isAfter(startDate.minusDays(1)) && transaction.getDate().isBefore(endDate.plusDays(1))) {
                System.out.println(transaction);

            }
        }
    }

    private static void filterTransactionsByVendor(String vendor) {
        // This method filters the transactions by vendor and prints a report to the console.
        // It takes one parameter: vendor, which represents the name of the vendor to filter by.
        // The method loops through the transactions list and checks each transaction's vendor name against the specified vendor name.
        // Transactions with a matching vendor name are printed to the console.
        // If no transactions match the specified vendor name, the method prints a message indicating that there are no results.
        try {
            for (Transaction transaction : transactions) {
                if (transaction.getVendor().equalsIgnoreCase(vendor)) {
                    System.out.println(transaction);


                }
            }
        } catch (Exception e) {
        }

    }
}