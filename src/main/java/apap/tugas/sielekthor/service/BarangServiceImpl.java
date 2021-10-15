package apap.tugas.sielekthor.service;


import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import apap.tugas.sielekthor.model.BarangModel;
import apap.tugas.sielekthor.repository.BarangDb;


@Service
@Transactional
public class BarangServiceImpl implements BarangService {

    @Autowired
    BarangDb barangDb;

    @Override
    public List<BarangModel> getListBarang() {
        return barangDb.findAll();
    }

    @Override
    public void addBarang(BarangModel barang) {
        barangDb.save(barang);
    }

    @Override
    public BarangModel getBarangByIdBarang(Long idBarang) {
        Optional<BarangModel> brg = barangDb.findById(idBarang);

        if (brg.isPresent()) {
            return brg.get();
        } else {
            return null;
        }
    }

    @Override
    public BarangModel updateBarang(BarangModel barang) {
        barangDb.save(barang);

        return barang;
        
    }
    
}
