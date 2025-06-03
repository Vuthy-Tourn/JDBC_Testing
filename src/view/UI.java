package view;

import controller.ProductController;
import controller.UserController;
import model.dto.*;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Scanner;

public class UI {
    private static final ProductController productController
             = new ProductController();
    private static final UserController userController
            = new UserController();
    private static String selectEntity() {
        System.out.println("===============");
        System.out.println("  Select Type  ");
        System.out.println("===============");
        System.out.println("1. Product");
        System.out.println("2. User");
        System.out.print("[+] Choose: ");
        int choice = new Scanner(System.in).nextInt();
        return (choice == 1) ? "product" : "user";
    }
    private static void productMenu() {
        System.out.println("============================");
        System.out.println("      Product Inventory     ");
        System.out.println("============================");
        System.out.println("""
            1. Get All Products
            2. Add New Product 
            3. Update Product 
            4. Delete Product
            5. Find Product
            0. Back
            """);
    }
    private static void userMenu() {
        System.out.println("============================");
        System.out.println("       User Inventory       ");
        System.out.println("============================");
        System.out.println("""
            1. Get All Users
            2. Add New User
            3. Update User
            4. Delete User
            5. Find User
            0. Back
            """);
    }
    public static void home() {
        while (true) {
            String entity = selectEntity();
            boolean goBack = false;
            while (!goBack) {
                if (entity.equals("product")) {
                    productMenu();
                } else {
                    userMenu();
                }

                System.out.print("[+] Choose option: ");
                int option = new Scanner(System.in).nextInt();

                switch (option) {
                    case 1 -> {
                        if (entity.equals("product")) {
                           new TableUI<ProductResponseDto>().getTableDisplay(productController.getAllProducts());
                        } else {
                            new TableUI<UserResponseDto>().getTableDisplay(userController.getAllUsers());
                        }
                    }
                    case 2 -> {
                        if (entity.equals("product")) {
                            // Add new product
                            System.out.print("[+] Insert Product Name: ");
                            String pName = new Scanner(System.in).nextLine();
                            System.out.print("[+] Insert Expire Year: ");
                            int year = new Scanner(System.in).nextInt();
                            System.out.print("[+] Insert Expire Month: ");
                            int month = new Scanner(System.in).nextInt();
                            System.out.print("[+] Insert Expire Day: ");
                            int day = new Scanner(System.in).nextInt();
                            ProductCreateDto productCreateDto = new ProductCreateDto(pName, Date.valueOf(LocalDate.of(year, month, day)));
                            ProductResponseDto product = productController.insertNewProduct(productCreateDto);
                            System.out.println(product);
                        } else {
                            // Add new user
                            System.out.print("[+] Insert Username: ");
                            String userName = new Scanner(System.in).nextLine();
                            System.out.print("[+] Insert Email: ");
                            String email = new Scanner(System.in).nextLine();
                            System.out.print("[+] Insert Password: ");
                            String password = new Scanner(System.in).nextLine();
                            UserCreateDto userCreateDto = new UserCreateDto(userName, email, password);
                            UserResponseDto user = userController.insertNewUser(userCreateDto);
                            System.out.println(user);
                        }
                    }
                    case 3 -> {
                        if (entity.equals("product")) {
                            System.out.print("[+] Insert Product UUID: ");
                            String uuid = new Scanner(System.in).nextLine();
                            System.out.print("[+] Insert new Product Name: ");
                            String newName = new Scanner(System.in).nextLine();
                            System.out.print("[+] Insert new Expire Year: ");
                            int year = new Scanner(System.in).nextInt();
                            System.out.print("[+] Insert new Expire Month: ");
                            int month = new Scanner(System.in).nextInt();
                            System.out.print("[+] Insert new Expire Day: ");
                            int day = new Scanner(System.in).nextInt();
                            UpdateProductDto dto = new UpdateProductDto(newName, Date.valueOf(LocalDate.of(year, month, day)));
                            ProductResponseDto updatedProduct = productController.updateProductByUuid(uuid, dto);
                            System.out.println(updatedProduct);
                        } else {
                            System.out.print("[+] Insert User UUID: ");
                            String uuid = new Scanner(System.in).nextLine();
                            System.out.print("[+] Insert new Username: ");
                            String newName = new Scanner(System.in).nextLine();
                            UpdateUserDto dto = new UpdateUserDto(newName);
                            UserResponseDto updatedUser = userController.updateUserByUuid(uuid, dto);
                            System.out.println(updatedUser);
                        }
                    }
                    case 4 -> {
                        System.out.print("[+] Insert UUID: ");
                        String uuid = new Scanner(System.in).nextLine();
                        if (entity.equals("product")) {
                            productController.deleteProductByUuid(uuid);
                        } else {
                            userController.deleteUserByUuid(uuid);
                        }
                        System.out.println("[+] Deleted Successfully");
                    }
                    case 5 -> {
                        System.out.print("[+] Insert UUID: ");
                        String uuid = new Scanner(System.in).nextLine();
                        if (entity.equals("product")) {
                            System.out.println(productController.getProductByUuid(uuid));
                        } else {
                            System.out.println(userController.getUserByUuid(uuid));
                        }
                    }
                    case 0 -> goBack = true;
                }
            }
        }
    }

}
