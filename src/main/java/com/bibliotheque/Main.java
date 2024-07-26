package com.bibliotheque;

import java.sql.SQLException;

import com.bibliotheque.classes.Biography;
import com.bibliotheque.classes.Book;
import com.bibliotheque.classes.LibraryManager;
import com.bibliotheque.classes.Novel;
import com.bibliotheque.classes.ScienceFiction;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        try {
            LibraryManager library = new LibraryManager();
            Scanner scanner = new Scanner(System.in);
            boolean running = true;

            while (running) {
                System.out.println("\n--- Menu de Gestion de Bibliothèque ---");
                System.out.println("1. Ajouter un livre");
                System.out.println("2. Supprimer un livre");
                System.out.println("3. Modifier un livre par son identifiant");
                System.out.println("4. Rechercher un livre par nom");
                System.out.println("5. Lister les livres en saisissant une lettre alphabétique");
                System.out.println("6. Afficher le nombre de livres");
                System.out.println("7. Afficher les livres par catégorie");
                System.out.println("8. Afficher les détails d'un livre par son identifiant");
                System.out.println("9. Quitter");
                System.out.print("Choisissez une option: ");

                int choice = scanner.nextInt();
                scanner.nextLine(); // Consume newline

                switch (choice) {
                    case 1:
                        System.out.print("Entrez l'ID du livre: ");
                        String id = scanner.nextLine();
                        System.out.print("Entrez le titre du livre: ");
                        String title = scanner.nextLine();
                        System.out.print("Entrez l'auteur du livre: ");
                        String author = scanner.nextLine();
                        System.out.print("Entrez la catégorie du livre (Novel, Science Fiction, Biography): ");
                        String category = scanner.nextLine();
                        Book book;
                        switch (category) {
                            case "Novel":
                                book = new Novel(id, title, author);
                                break;
                            case "Science Fiction":
                                book = new ScienceFiction(id, title, author);
                                break;
                            case "Biography":
                                book = new Biography(id, title, author);
                                break;
                            default:
                                System.out.println("Catégorie inconnue!");
                                continue;
                        }
                        library.addBook(book);
                        System.out.println("Livre ajouté avec succès.");
                        break;
                    case 2:
                        System.out.print("Entrez l'ID du livre à supprimer: ");
                        String removeId = scanner.nextLine();
                        library.removeBook(removeId);
                        System.out.println("Livre supprimé avec succès.");
                        break;
                    case 3:
                        System.out.print("Entrez l'ID du livre à modifier: ");
                        String updateId = scanner.nextLine();
                        System.out.print("Entrez le nouveau titre du livre: ");
                        String newTitle = scanner.nextLine();
                        System.out.print("Entrez le nouvel auteur du livre: ");
                        String newAuthor = scanner.nextLine();
                        library.updateBook(updateId, newTitle, newAuthor);
                        System.out.println("Livre modifié avec succès.");
                        break;
                    case 4:
                        System.out.print("Entrez le nom du livre à rechercher: ");
                        String name = scanner.nextLine();
                        Book foundBook = library.searchBookByName(name);
                        if (foundBook != null) {
                            System.out.println("Livre trouvé: " + foundBook);
                        } else {
                            System.out.println("Livre non trouvé.");
                        }
                        break;
                    case 5:
                        System.out.print("Entrez une lettre alphabétique: ");
                        char letter = scanner.nextLine().charAt(0);
                        List<Book> booksByLetter = library.listBooksByLetter(letter);
                        System.out.println("Livres trouvés: ");
                        for (Book b : booksByLetter) {
                            System.out.println(b);
                        }
                        break;
                    case 6:
                        System.out.println("Nombre de livres: " + library.getBookCount());
                        break;
                    case 7:
                        System.out.print("Entrez la catégorie des livres à afficher: ");
                        String cat = scanner.nextLine();
                        List<Book> booksByCategory = library.getBooksByCategory(cat);
                        System.out.println("Livres trouvés: ");
                        for (Book b : booksByCategory) {
                            System.out.println(b);
                        }
                        break;
                    case 8:
                        System.out.print("Entrez l'ID du livre: ");
                        String bookId = scanner.nextLine();
                        try {
                            Book bookDetails = library.getBookByIdWithException(bookId);
                            System.out.println("Détails du livre: " + bookDetails);
                        } catch (LibraryManager.BookNotFoundException e) {
                            System.out.println(e.getMessage());
                        }
                        break;
                    case 9:
                        running = false;
                        System.out.println("Au revoir!");
                        break;
                    default:
                        System.out.println("Option invalide. Veuillez réessayer.");
                }
            }
            scanner.close();
            library.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
