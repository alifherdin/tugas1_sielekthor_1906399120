package apap.tugas.sielekthor.service;


import java.util.List;
import apap.tugas.sielekthor.model.BarangModel;


public interface BarangService {
    List<BarangModel> getListBarang();

    void addBarang(BarangModel barang);

    BarangModel getBarangByIdBarang(Long idBarang);

    BarangModel updateBarang(BarangModel barang);
}
