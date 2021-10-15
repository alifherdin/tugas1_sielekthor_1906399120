package apap.tugas.sielekthor.model;


import java.io.Serializable;
import java.util.List;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Entity
@Table(name = "tipe")
public class TipeModel implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(max=255)
    @Column(name="nama_tipe", nullable = false)
    private String namaTipe;
    
    @NotNull
    @Size(max=255)
    @Column(name="deskripsi_tipe", nullable = false)
    private String deskripsiTipe;

    @OneToMany(mappedBy = "tipe", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<BarangModel> listBarang;
}
