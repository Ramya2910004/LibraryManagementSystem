import java.util.*;
import java.io.*;

class Book {
    int id;
    String title;
    String author;
    boolean issued;

    Book(int id, String title, String author, boolean issued) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.issued = issued;
    }

    @Override
    public String toString() {
        return id + "," + title + "," + author + "," + issued;
    }

    static Book fromString(String line) {
        String[] d = line.split(",");
        return new Book(
                Integer.parseInt(d[0]),
                d[1],
                d[2],
                Boolean.parseBoolean(d[3])
        );
    }
}

public class LibraryManagementSystem {

    static ArrayList<Book> books = new ArrayList<>();
    static final String FILE_NAME = "books.txt";
    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        loadBooks();
        login();
    }

    // ---------------- LOGIN ----------------
    static void login() {
        System.out.print("Enter Username: ");
        String user = sc.nextLine();
        System.out.print("Enter Password: ");
        String pass = sc.nextLine();

        if (user.equals("admin") && pass.equals("admin123")) {
            adminMenu();
        } else if (user.equals("student") && pass.equals("stud123")) {
            studentMenu();
        } else {
            System.out.println("Invalid credentials!");
        }
    }

    // ---------------- ADMIN MENU ----------------
    static void adminMenu() {
        int choice;
        do {
            System.out.println("\n===== ADMIN MENU =====");
            System.out.println("1. Add Book");
            System.out.println("2. View Books");
            System.out.println("3. Search Book");
            System.out.println("4. Delete Book");
            System.out.println("5. Exit");
            System.out.print("Enter choice: ");

            choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1 -> addBook();
                case 2 -> viewBooks();
                case 3 -> searchBook();
                case 4 -> deleteBook();
                case 5 -> saveBooks();
                default -> System.out.println("Invalid choice");
            }
        } while (choice != 5);
    }

    // ---------------- STUDENT MENU ----------------
    static void studentMenu() {
        int choice;
        do {
            System.out.println("\n===== STUDENT MENU =====");
            System.out.println("1. View Books");
            System.out.println("2. Search Book");
            System.out.println("3. Exit");
            System.out.print("Enter choice: ");

            choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1 -> viewBooks();
                case 2 -> searchBook();
                case 3 -> {}
                default -> System.out.println("Invalid choice");
            }
        } while (choice != 3);
    }

    // ---------------- FEATURES ----------------
    static void addBook() {
        System.out.print("Book ID: ");
        int id = sc.nextInt();
        sc.nextLine();
        System.out.print("Title: ");
        String title = sc.nextLine();
        System.out.print("Author: ");
        String author = sc.nextLine();

        books.add(new Book(id, title, author, false));
        System.out.println("Book added!");
    }

    static void viewBooks() {
        if (books.isEmpty()) {
            System.out.println("No books available.");
            return;
        }
        for (Book b : books) {
            System.out.println(b.id + " | " + b.title + " | " + b.author +
                    " | " + (b.issued ? "Issued" : "Available"));
        }
    }

    static void searchBook() {
        System.out.print("Enter Book ID or Title: ");
        sc.nextLine();
        String key = sc.nextLine();

        for (Book b : books) {
            if (String.valueOf(b.id).equals(key) || b.title.equalsIgnoreCase(key)) {
                System.out.println("Found: " + b.id + " | " + b.title + " | " + b.author);
                return;
            }
        }
        System.out.println("Book not found.");
    }

    static void deleteBook() {
        System.out.print("Enter Book ID to delete: ");
        int id = sc.nextInt();

        Iterator<Book> it = books.iterator();
        while (it.hasNext()) {
            if (it.next().id == id) {
                it.remove();
                System.out.println("Book deleted!");
                return;
            }
        }
        System.out.println("Book not found.");
    }

    // ---------------- FILE HANDLING ----------------
    static void loadBooks() {
        try {
            File f = new File(FILE_NAME);
            if (!f.exists()) return;

            BufferedReader br = new BufferedReader(new FileReader(f));
            String line;
            while ((line = br.readLine()) != null) {
                books.add(Book.fromString(line));
            }
            br.close();
        } catch (Exception e) {
            System.out.println("Error loading books.");
        }
    }

    static void saveBooks() {
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_NAME));
            for (Book b : books) {
                bw.write(b.toString());
                bw.newLine();
            }
            bw.close();
        } catch (Exception e) {
            System.out.println("Error saving books.");
        }
    }
}
