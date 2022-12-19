package com.code.Entity;

import com.code.Enum.tokenType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table
public class token {
    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    private int id ;
    @Column(nullable = false)
    private String token ;
    @Column(nullable = false)
    private LocalDateTime createAt;
    @Column(nullable = false)
    private LocalDateTime expiryAt;
    private LocalDateTime confirmAt;
    private tokenType tokenType;
    @ManyToOne
    @JoinColumn(
            nullable = false,
            name = "account_id"
    )
    private account account;
    public void genNewToken(){
        this.token = UUID.randomUUID().toString();
        this.createAt = LocalDateTime.now();
        this.expiryAt = LocalDateTime.now().plusMinutes(15);
    }
}
