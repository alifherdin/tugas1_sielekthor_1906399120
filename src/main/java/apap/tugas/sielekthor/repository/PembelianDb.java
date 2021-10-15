package apap.tugas.sielekthor.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import apap.tugas.sielekthor.model.PembelianModel;


public interface PembelianDb extends JpaRepository<PembelianModel, Long> {

}
