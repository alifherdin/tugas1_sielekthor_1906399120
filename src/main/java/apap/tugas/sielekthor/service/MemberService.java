package apap.tugas.sielekthor.service;

import java.util.List;

import apap.tugas.sielekthor.model.MemberModel;
import apap.tugas.sielekthor.model.PembelianModel;

public interface MemberService {
    List<MemberModel> getListMember();

    void addMember(MemberModel mbr);

    MemberModel getMemberByIdMember(Long idMember);

    MemberModel updateMember(MemberModel member);

    String invoicing(PembelianModel pembelian);

}
