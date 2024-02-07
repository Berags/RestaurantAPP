package edu.unifi.model.entities;

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

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "room", orphanRemoval = true)

    private List<edu.unifi.model.entities.Table> tables = new ArrayList<>();

    public Room() {
    }
    public Room(String name) {
        this.name = name;
    }

    public List<edu.unifi.model.entities.Table> getTables() {
        return tables;
    }

    public void setTables(List<edu.unifi.model.entities.Table> tables) {
        this.tables = tables;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) { this.name = name; }
    public int getNumberOfTables() { return tables.size(); }
}