package apap.tugas.sielekthor.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import apap.tugas.sielekthor.model.PembelianBarangModel;
import apap.tugas.sielekthor.model.PembelianModel;
import apap.tugas.sielekthor.model.BarangModel;


public interface PembelianBarangDb extends JpaRepository<PembelianBarangModel, Long> {
    List<PembelianBarangModel> findByPembelian(PembelianModel pembelian);

    List<PembelianBarangModel> findByBarang(BarangModel barang);
}
