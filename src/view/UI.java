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
    private static void thumbnail(){
        System.out.println("============================");
        System.out.println("      Product Inventory     ");
        System.out.println("============================");
        System.out.println("""
                1. Get All Products
                2. Add New Product 
                3. Update Product 
                4. Delete Product
                5. Find Product
                """);
        System.out.println("============================");
        System.out.println("       User Inventory       ");
        System.out.println("============================");
        System.out.println("""
                6. Get All Users
                7. Add New User
                8. Update User
                9. Delete User
                10. Find User
                0. Exit 
                """);
    }

    public static void home(){
        while (true){
            thumbnail();
            System.out.print("[+] Insert option: ");
            switch (new Scanner(System.in).nextInt()){
                case 1->{
                    productController
                            .getAllProducts()
                            .forEach(System.out::println);
                }
                case 2->{
                    System.out.print("[+] Insert Product Name: ");
                    String pName = new Scanner(System.in).nextLine();
                    System.out.print("[+] Insert Expire Year: ");
                    int year = new Scanner(System.in).nextInt();
                    System.out.print("[+] Insert Expire Month: ");
                    int month = new Scanner(System.in).nextInt();
                    System.out.print("[+] Insert Expire Day: ");
                    int day = new Scanner(System.in).nextInt();
                    ProductCreateDto productCreateDto
                             = new ProductCreateDto(pName, Date.valueOf(LocalDate.of(year, month, day)));
                    ProductResponseDto product = productController.insertNewProduct(productCreateDto);
                    System.out.println(product);
                }
                case 3->{
                    System.out.print("[+] Insert Product Uuid: ");
                    String uuid = new Scanner(System.in).nextLine();
                    System.out.print("[+] Insert new Product Name: ");
                    String newPName = new Scanner(System.in).nextLine();
                    System.out.print("[+] Insert new Expired Year: ");
                    int year = new Scanner(System.in).nextInt();
                    System.out.print("[+] Insert new Expired Month: ");
                    int month = new Scanner(System.in).nextInt();
                    System.out.print("[+] Insert new Expired Day: ");
                    int day = new Scanner(System.in).nextInt();
                    ProductResponseDto updatedProduct = productController
                            .updateProductByUuid(uuid,
                                    new UpdateProductDto(newPName, Date.valueOf(LocalDate.of(year, month, day))));
                    System.out.println(updatedProduct);
                }
                case 4->{
                    System.out.print("[+] Delete Product By Uuid: ");
                    String uuid = new Scanner(System.in).nextLine();
                    productController.deleteProductByUuid(uuid);
                    System.out.println("[+] Deleted successfully");
                }
                case 5->{
                    System.out.print("[+] Find Product By Uuid: ");
                    String uuid = new Scanner(System.in).nextLine();
                    System.out.println(productController.getProductByUuid(uuid));
                }
                case 6->{
                    userController
                            .getAllUsers()
                            .forEach(System.out::println);
                }
                case 7->{
                    System.out.print("[+] Insert userName: ");
                    String userName = new Scanner(System.in).nextLine();
                    System.out.print("[+] Insert Email: ");
                    String email = new Scanner(System.in).nextLine();
                    System.out.print("[+] Insert Password: ");
                    String password = new Scanner(System.in).nextLine();
                    UserCreateDto userCreateDto
                            = new UserCreateDto(userName,email,password);
                    UserResponseDto user = userController.insertNewUser(userCreateDto);
                    System.out.println(user);
                }
                case 8->{
                    System.out.print("[+] Insert User Uuid: ");
                    String uuid = new Scanner(System.in).nextLine();
                    System.out.print("[+] Insert new Username: ");
                    String newUserName = new Scanner(System.in).nextLine();
                    UserResponseDto updatedUser = userController
                            .updateUserByUuid(uuid,
                                    new UpdateUserDto(newUserName));
                    System.out.println(updatedUser);
                }
                case 9->{
                    System.out.print("[+] Delete User By Uuid: ");
                    String uuid = new Scanner(System.in).nextLine();
                    userController.deleteUserByUuid(uuid);
                    System.out.println("[+] Deleted successfully");
                }
                case 10->{
                    System.out.print("[+] Find User By Uuid: ");
                    String uuid = new Scanner(System.in).nextLine();
                    System.out.println(userController.getUserByUuid(uuid));
                }
                case 0->{
                    System.out.println("Thank you for using our system");
                    return;
                }
            }
        }
    }
}
