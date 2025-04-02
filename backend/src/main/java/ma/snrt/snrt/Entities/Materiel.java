package ma.snrt.snrt.Entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Materiel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String designation;

    private String marque;

    private String model;

    @Column(unique = true)
    private String serialNumber	;

    private int quantity;

    private String numMarche;

    private String etat;

    private boolean selected;

    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "type_id")
    private Type type;

    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "unite_id")
    private Unite unite;

}
