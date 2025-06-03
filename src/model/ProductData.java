package model;

import model.entities.Product;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ProductData {
    public static List<Product> products
             = new ArrayList<>(
                     List.of(new Product(1, UUID.randomUUID().toString(),
                             "Coca",
                             Date.valueOf(LocalDate.of(2027,12,12))))
    );
}
