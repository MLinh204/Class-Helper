package com.example.Class_Helper.model;

import jakarta.persistence.*;

@Entity
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String powerType;

    @Column(columnDefinition = "LONGBLOB")
    @Basic(fetch = FetchType.EAGER)
    private byte[] photo = new byte[0];
    private String symbol;
    @Column(nullable = false)
    private int level = 1;

    @Column(nullable = false)
    private double heart = 10.0;

    @Column(nullable = false)
    private int point = 0;
    @Column(nullable = false)
    private int crystal = 10;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPowerType() {
        return powerType;
    }

    public void setPowerType(String powerType) {
        this.powerType = powerType;
    }

    public double getHeart() {
        return heart;
    }

    public void setHeart(double heart) {
        this.heart = heart;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getPoint() {
        return point;
    }

    public void setPoint(int point) {
        this.point = point;
    }

    public int getCrystal() {
        return crystal;
    }

    public void setCrystal(int crystal) {
        this.crystal = crystal;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public byte[] getPhoto() {
        return photo;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }
}
