package ro.uaic.info.l3.entities;


import javax.persistence.*;

@Entity
@Table(name = "test_table")
@NamedQueries({
        @NamedQuery(name = "Test.findAll",
                query = "select t from Test t"),
})
public class Test {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "id")
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;

    @Basic(optional = true)
    @Column(name = "name")
    private String name;

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }
}