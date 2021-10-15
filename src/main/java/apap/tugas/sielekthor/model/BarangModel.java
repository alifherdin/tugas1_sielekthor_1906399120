package apap.tugas.sielekthor.model;


import java.io.Serializable;
import java.util.List;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Entity
@Table(name = "barang")
public class BarangModel implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(max=255)
    @Column(name="nama_barang", nullable = false)
    private String namaBarang;

    @NotNull
    @Column(name="stok", nullable = false)
    private Integer stok;

    @NotNull
    @Column(name="jumlah_garansi", nullable = false)
    private Integer jumlahGaransi;

    @NotNull
    @Size(max=255)
    @Column(name="deksripsi_barang", nullable = false)
    private String deskripsiBarang;
    
    @NotNull
    @Size(max=255)
    @Column(name="kode_barang", nullable = false)
    private String kodeBarang;
    
    @NotNull
    @Size(max=255)
    @Column(name="merk_barang", nullable = false)
    private String merkBarang;

    @NotNull
    @Column(name="harga_barang", nullable = false)
    private Integer hargaBarang;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "id_tipe", referencedColumnName = "id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private TipeModel tipe;

    @OneToMany(mappedBy = "barang", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<PembelianBarangModel> listPembelianBarang;
}
