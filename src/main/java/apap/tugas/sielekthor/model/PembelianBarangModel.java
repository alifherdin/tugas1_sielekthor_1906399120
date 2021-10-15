package apap.tugas.sielekthor.model;


import java.util.Date;
import java.io.Serializable;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Entity
@Table(name = "pembelian_barang")
public class PembelianBarangModel implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name="tanggal_garansi", nullable = false)
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date tanggalGaransi;

    @NotNull
    @Column(name="quantity", nullable = false)
    private Integer quantity;

    @ManyToOne
    @JoinColumn(name = "id_barang")
    private BarangModel barang;

    @ManyToOne
    @JoinColumn(name = "id_pembelian")
    private PembelianModel pembelian;
}
