package com.intelli5.labourlink.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class UserEmailAndNameForChat {
    @Id
    private String email;
    private String name;

    private Status status;
}


//    CREATE VIEW userEmailAndNameForChat AS
//    SELECT COALESCE(c.email, l.email) AS email, COALESCE(c.name, l.name) AS name
//        FROM customer c
//        LEFT JOIN labour l ON c.email = l.email
//        UNION
//        SELECT COALESCE(c.email, l.email) AS email, COALESCE(c.name, l.name) AS name
//        FROM customer c
//        RIGHT JOIN labour l ON c.email = l.email;