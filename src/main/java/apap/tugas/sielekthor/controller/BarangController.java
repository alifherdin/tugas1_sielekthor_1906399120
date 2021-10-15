package apap.tugas.sielekthor.controller;


import java.util.List;
import java.time.LocalTime;
import java.util.ArrayList;

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

import apap.tugas.sielekthor.model.TipeModel;
import apap.tugas.sielekthor.model.BarangModel;
import apap.tugas.sielekthor.model.PembelianBarangModel;
import apap.tugas.sielekthor.model.PembelianModel;
import apap.tugas.sielekthor.service.MemberService;
import apap.tugas.sielekthor.service.PembelianService;
import apap.tugas.sielekthor.model.MemberModel;
import apap.tugas.sielekthor.service.BarangService;
import apap.tugas.sielekthor.service.PembelianBarangService;
import apap.tugas.sielekthor.service.TipeService;


@Controller
public class BarangController {
    
    @Qualifier("barangServiceImpl")
    @Autowired
    private BarangService barangService;

    @Qualifier("tipeServiceImpl")
    @Autowired
    private TipeService tipeService;

    @Qualifier("pembelianServiceImpl")
    @Autowired
    private PembelianService pembelianService;

    @Qualifier("memberServiceImpl")
    @Autowired
    private MemberService memberService;

    @Qualifier("pembelianBarangServiceImpl")
    @Autowired
    private PembelianBarangService pembelianBarangService;

    @GetMapping("barang")
    public String listBarang(Model model) {
        List<BarangModel> listBarang = barangService.getListBarang();
        model.addAttribute("listBarang", listBarang);

        return "viewall-barang";
    }

    @GetMapping("barang/tambah")
    public String addBarang(Model model) {
        model.addAttribute("barang", new BarangModel());
        model.addAttribute("listTipe", tipeService.getListTipe());

        return "form-add-barang";
    }

    @PostMapping("barang/tambah")
    public String addBarangSubmitPage(@ModelAttribute BarangModel brg, Model model) {
        barangService.addBarang(brg);
        model.addAttribute("kodeBarang", brg.getKodeBarang());

        return "add-barang";
    }    

    @GetMapping("barang/lihat/{idBarang}")
    public String seeBarang(@PathVariable Long idBarang, Model model) {
        BarangModel brg = barangService.getBarangByIdBarang(idBarang);
        model.addAttribute("barang", brg);

        return "view-barang";
    }

    @GetMapping("barang/ubah/{idBarang}")
    public String chgBarang(@PathVariable Long idBarang, Model model) {
        BarangModel brg = barangService.getBarangByIdBarang(idBarang);
        model.addAttribute("barang", brg);

        return "form-ubah-barang";
    }

    @PostMapping("/barang/ubah")
    public String chgBarangSubmitPage(@ModelAttribute BarangModel barang, Model model) {
        BarangModel updatedBarang = barangService.updateBarang(barang);
        model.addAttribute("upd_brg", updatedBarang);

        return "update-barang-succeed";
    }

    @GetMapping(value = "barang/cari")
    public String cariBarangForm(Model model) {
        List<TipeModel> listTipe = tipeService.getListTipe();
        model.addAttribute("listTipe", listTipe);        

        return "cari-barang";
    }

    @GetMapping(value = "barang/cari/stok")
    public String cariBarang(@RequestParam(value = "tipe") Long tipe, @RequestParam(value = "stok") Boolean stok, Model model) {
        List<BarangModel> listBarang = new ArrayList<>();
        List<TipeModel> listTipe = tipeService.getListTipe();
           
        for (BarangModel i : barangService.getListBarang()) {
            if (i.getTipe().getId().longValue() == tipe.longValue()) {
                if (stok.booleanValue() == true) {
                    if (i.getStok() >= 1) {
                        listBarang.add(i);
                    }
                } else if (stok.booleanValue() == false) {
                    if (i.getStok() < 1) {
                        listBarang.add(i);
                    }
                }
            }
        }

        model.addAttribute("listTipe", listTipe);
        model.addAttribute("listBarang", listBarang);

        return "cari-barang";
    }
}
