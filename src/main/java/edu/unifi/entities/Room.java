package edu.unifi.entities;

import jakarta.persistence.*;
import jakarta.persistence.Table;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "rooms")
public class Room {
    @Id
    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "room", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<edu.unifi.entities.Table> tables = new ArrayList<>();

    public List<edu.unifi.entities.Table> getTables() {
        return tables;
    }

    public void setTables(List<edu.unifi.entities.Table> tables) {
        this.tables = tables;
    }
}
