package apap.tugas.sielekthor.controller;

import java.util.List;
import java.time.LocalTime;

import org.springframework.stereotype.Controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import apap.tugas.sielekthor.model.MemberModel;
import apap.tugas.sielekthor.service.MemberService;


@Controller
public class MemberController {
    
    @Qualifier("memberServiceImpl")
    @Autowired
    private MemberService memberService;

    @GetMapping("member")
    public String listmember(Model model) {
        List<MemberModel> listMember = memberService.getListMember();
        model.addAttribute("listMember", listMember);

        return "viewall-member";
    }

    @GetMapping("member/tambah")
    public String addMember(Model model) {
        model.addAttribute("barang", new MemberModel());

        return "form-add-member";
    }

    @PostMapping("member/tambah")
    public String addMemberSubmitPage(@ModelAttribute MemberModel mbr, Model model) {
        memberService.addMember(mbr);
        model.addAttribute("namaMember", mbr.getNamaMember());

        return "add-member";
    }    

    @GetMapping("member/ubah/{idMember}")
    public String chgBarang(@PathVariable Long idMember, Model model) {
        MemberModel mbr = memberService.getMemberByIdMember(idMember);
        model.addAttribute("member", mbr);

        return "form-ubah-member";
    }

    @PostMapping("/member/ubah")
    public String chgMemberSubmitPage(@ModelAttribute MemberModel member, Model model) {
        MemberModel updatedMember = memberService.updateMember(member);
        
        model.addAttribute("upd_mbr", updatedMember);

        return "update-member-succeed";
    }

}
