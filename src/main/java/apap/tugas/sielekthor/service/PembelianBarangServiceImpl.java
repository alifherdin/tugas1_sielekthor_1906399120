package apap.tugas.sielekthor.service;


import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import apap.tugas.sielekthor.model.BarangModel;
import apap.tugas.sielekthor.repository.BarangDb;
import apap.tugas.sielekthor.repository.PembelianBarangDb;


@Service
@Transactional
public class PembelianBarangServiceImpl implements PembelianBarangService {
    
    @Autowired
    PembelianBarangDb pembelianBarangDb;
}
