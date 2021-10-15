package apap.tugas.sielekthor.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import apap.tugas.sielekthor.model.MemberModel;

public interface MemberDb extends JpaRepository<MemberModel, Long> {
    
}
