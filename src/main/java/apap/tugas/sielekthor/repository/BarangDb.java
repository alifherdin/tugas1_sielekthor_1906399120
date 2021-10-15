package apap.tugas.sielekthor.repository;


import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import apap.tugas.sielekthor.model.BarangModel;


public interface BarangDb extends JpaRepository<BarangModel, Long> {
    
}
