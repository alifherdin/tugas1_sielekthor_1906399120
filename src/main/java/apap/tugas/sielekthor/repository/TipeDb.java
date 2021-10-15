package apap.tugas.sielekthor.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import apap.tugas.sielekthor.model.TipeModel;

public interface TipeDb extends JpaRepository<TipeModel, Long> {
    
}
