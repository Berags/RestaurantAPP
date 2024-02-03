package edu.unifi.model.entities;

import jakarta.persistence.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.ArrayList;
import java.util.List;

@Entity
@jakarta.persistence.Table(name = "tables")
public class Table {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "n_of_seats", nullable = false)
    @JdbcTypeCode(SqlTypes.SMALLINT)
    private int nOfSeats;

    @Column(name = "state", nullable = false)
    private TableState state;

    @ManyToOne(optional = false)
    private Room room;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "table", orphanRemoval = true)
    private List<Check> checks = new ArrayList<>();

    @Column(name = "name")
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Check> getChecks() {
        return checks;
    }

    public void setChecks(List<Check> checks) {
        this.checks = checks;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public TableState getState() {
        return state;
    }

    public void setState(TableState state) {
        this.state = state;
    }

    public int getNOfSeats() {
        return nOfSeats;
    }

    public void setNOfSeats(int nOfSeats) {
        this.nOfSeats = nOfSeats;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
