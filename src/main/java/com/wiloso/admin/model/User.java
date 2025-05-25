package com.wiloso.admin.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "users")
public class User {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String nama;

  @Column(unique = true)
  private String email;

  private String password;

  private LocalDateTime tanggalDibuat;

  public User() {
    this.tanggalDibuat = LocalDateTime.now();
  }

  // Getters & Setters
  public Long getId() { return id; }
  public String getNama() { return nama; }
  public void setNama(String nama) { this.nama = nama; }
  public String getEmail() { return email; }
  public void setEmail(String email) { this.email = email; }
  public String getPassword() { return password; }
  public void setPassword(String password) { this.password = password; }
  public LocalDateTime getTanggalDibuat() { return tanggalDibuat; }
}
