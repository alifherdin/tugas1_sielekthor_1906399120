package apap.tugas.sielekthor.service;


import java.util.List;
import java.util.Optional;
import java.util.Random;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import apap.tugas.sielekthor.model.MemberModel;
import apap.tugas.sielekthor.model.PembelianModel;
import apap.tugas.sielekthor.repository.MemberDb;


@Service
@Transactional
public class MemberServiceImpl implements MemberService {

    @Autowired
    MemberDb memberDb;

    @Override
    public String invoicing(PembelianModel pembelian) {
        String ch1 = Integer.toString((pembelian.getMember().getNamaMember().toLowerCase().toCharArray()[0] - 96) % 10);

        String ch2x = pembelian.getNamaAdmin().toUpperCase();
        String ch2 = Character.toString(ch2x.charAt(ch2x.length() - 1));

        String[] ch3x = pembelian.getTanggalPembelian().toString().split("-");
        String ch3_6 = ch3x[2].split(" ")[0] + ch3x[1];

        String ch7_8 = ((pembelian.getIsCash() == true) ? "02" : "01");

        Integer ch9_11x = (Integer.valueOf(ch3x[2].split(" ")[0]) + Integer.valueOf(ch3x[1])) * 5;
        String ch9_11 = ((Integer.toString(ch9_11x).length() < 3) ? ("0" + (Integer.toString(ch9_11x))) : Integer.toString(ch9_11x));

        Random rnd = new Random();
        char ch12x = (char) ('a' + rnd.nextInt(26));
        char ch13x = (char) ('a' + rnd.nextInt(26));
        String ch12_13 = (Character.toString(ch12x) + Character.toString(ch13x)).toUpperCase();

        return (ch1 + ch2 + ch3_6 + ch7_8 + ch9_11 + ch12_13);
    }

    @Override
    public List<MemberModel> getListMember() {
        return memberDb.findAll();
    }

    @Override
    public void addMember(MemberModel mbr) {
        memberDb.save(mbr);
        
    }

    @Override
    public MemberModel getMemberByIdMember(Long idMember) {
        Optional<MemberModel> mbr = memberDb.findById(idMember);

        if (mbr.isPresent()) {
            return mbr.get();
        } else {
            return null;
        }

    }

    @Override
    public MemberModel updateMember(MemberModel member) {
        memberDb.save(member);
        
        List<PembelianModel> temp = member.getListPembelian();

        for (int i = 0; i < temp.size(); i++) {
            temp.get(i).setNoInvoice(invoicing(temp.get(i)));
        }

        member.setListPembelian(temp);

        memberDb.save(member);

        return member;
    }
   
}
