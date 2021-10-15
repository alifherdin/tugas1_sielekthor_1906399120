package apap.tugas.sielekthor.service;


import java.util.List;

import apap.tugas.sielekthor.model.BarangModel;
import apap.tugas.sielekthor.model.PembelianBarangModel;
import apap.tugas.sielekthor.model.PembelianModel;


public interface PembelianService {
    List<PembelianModel> getListPembelian();
    Long getTotalQuantityPembelian(PembelianModel pembelian);
    Long getTotalHargaPembelian(PembelianModel pembelian);
    PembelianModel getPembelianByIdPembelian(Long id);
    List<BarangModel> getBarangs(PembelianModel pembelian);
    List<PembelianBarangModel> getAllPembelianbarang(PembelianModel pembelian);
    String invoicing(PembelianModel pembelian);
    PembelianModel addPembelian(PembelianModel pembelian);
    List<BarangModel> deletePembelian(PembelianModel pembelian);
}
