package apap.tugas.sielekthor.model;


import java.util.Date;
import java.io.Serializable;
import java.util.List;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
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
@Table(name = "pembelian")
public class PembelianModel implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name="total", nullable = false)
    private Integer total;

    @NotNull
    @Column(nullable = false)
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date tanggalPembelian;

    @NotNull
    @Size(max=255)
    @Column(name="nama_admin", nullable = false)
    private String namaAdmin;

    @NotNull
    @Size(max=255)
    @Column(name="no_invoice", nullable = false, unique = true)
    private String noInvoice;

    @NotNull
    @Column(name="is_cash", nullable = false)
    private Boolean isCash;
    
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "id_member", referencedColumnName = "id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private MemberModel member;

    @OneToMany(mappedBy = "pembelian", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<PembelianBarangModel> listPembelianBarang;
}
