package model.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class User {
    private Integer id;
    private String uuid;
    private String userName;
    private String email;
    private String password;
    private Date createdDate;
}