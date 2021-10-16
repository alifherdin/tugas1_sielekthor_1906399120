package apap.tugas.sielekthor.controller;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.time.LocalTime;
import java.util.Map;
import java.util.HashMap;

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

import apap.tugas.sielekthor.model.BarangModel;
import apap.tugas.sielekthor.model.PembelianBarangModel;
import apap.tugas.sielekthor.model.PembelianModel;
import apap.tugas.sielekthor.service.MemberService;
import apap.tugas.sielekthor.service.PembelianService;
import apap.tugas.sielekthor.model.MemberModel;
import apap.tugas.sielekthor.service.BarangService;
import apap.tugas.sielekthor.service.PembelianBarangService;



@Controller
public class PembelianController {

    @Qualifier("pembelianServiceImpl")
    @Autowired
    private PembelianService pembelianService;

    @Qualifier("memberServiceImpl")
    @Autowired
    private MemberService memberService;

    @Qualifier("barangServiceImpl")
    @Autowired
    private BarangService barangService;

    @Qualifier("pembelianBarangServiceImpl")
    @Autowired
    private PembelianBarangService pembelianBarangService;

    private int jmlBarang = 2;

    @GetMapping("pembelian")
    public String listPembelian(Model model) {
        List<PembelianModel> listPembelian = pembelianService.getListPembelian();
        List<Long> listQ = new ArrayList<Long>();
        List<Long> listH = new ArrayList<Long>();

        for (int i = 0; i < listPembelian.size(); i++) {
            Long temp = pembelianService.getTotalQuantityPembelian(listPembelian.get(i));

            listQ.add(temp);
        }

        for (int i = 0; i < listPembelian.size(); i++) {
            Long temp = pembelianService.getTotalHargaPembelian(listPembelian.get(i));
            
            listH.add(temp);
        
        }


        model.addAttribute("listPembelian", listPembelian);
        model.addAttribute("listQuantity", listQ);
        model.addAttribute("listHarga", listH);

        return "viewall-pembelian";
    }

    @GetMapping("pembelian/{idPemb}")
    public String seePembelian(@PathVariable Long idPemb, Model model) {
        PembelianModel pembe = pembelianService.getPembelianByIdPembelian(idPemb);

        List<PembelianBarangModel> xPB = pembelianService.getAllPembelianbarang(pembe);
        List<BarangModel> listBarang = pembelianService.getBarangs(pembe);
        Long totalQuantity = pembelianService.getTotalQuantityPembelian(pembe);

        model.addAttribute("listBarang", listBarang);
        model.addAttribute("listPembBarang", xPB);
        model.addAttribute("pmbe", pembe);
        model.addAttribute("quantity", totalQuantity);

        return "view-pembelian";
    }


    @GetMapping("pembelian/tambah")
    public String addPembelian(Model model) {
        PembelianModel newPemb = new PembelianModel();
        List<MemberModel> listMember = memberService.getListMember();
        List<BarangModel> listBarangs = barangService.getListBarang();
        List<BarangModel> listBarang = new ArrayList<>();

        for (BarangModel i : listBarangs) {
            if (i.getStok() > 0) {
                listBarang.add(i);
            }
        }

        List<PembelianBarangModel> listPB = new ArrayList<PembelianBarangModel>();
        PembelianBarangModel tempPB =  new PembelianBarangModel();
        listPB.add(tempPB);
        newPemb.setListPembelianBarang(listPB);
        
        model.addAttribute("pembelian", newPemb);
        model.addAttribute("listMember", listMember);
        model.addAttribute("listBarang", listBarang);

        return "form-add-pembelian";
    }

    @PostMapping(value = "pembelian/tambah", params = {"gooo"})
    public String addPembelianSubmit(@ModelAttribute PembelianModel pembelian, Model model) {

        for (PembelianBarangModel i : pembelian.getListPembelianBarang()) {
            if (i.getQuantity() > i.getBarang().getStok()) {
                return "add-pembelian-failed";
            }
        }

        PembelianModel pmbl = pembelianService.addPembelian(pembelian);
        
        model.addAttribute("new_pemb", pmbl);

        return "add-pembelian-succeed";
    }

    @PostMapping(value = "pembelian/tambah", params = {"tambahRow"})
    public String addPembelianTambahRow(@ModelAttribute PembelianModel pembelian, Model model) {
        List<MemberModel> listMember = memberService.getListMember();
        List<PembelianBarangModel> listPB = pembelian.getListPembelianBarang();
        List<BarangModel> listBarangs = barangService.getListBarang();
        List<BarangModel> listBarang = new ArrayList<>();

        for (BarangModel i : listBarangs) {
            if (i.getStok() > 0) {
                listBarang.add(i);
            }
        }

        PembelianBarangModel tempPB =  new PembelianBarangModel();
        listPB.add(tempPB);
        pembelian.setListPembelianBarang(listPB);
        
        model.addAttribute("pembelian", pembelian);
        model.addAttribute("listMember", listMember);
        model.addAttribute("listBarang", listBarang);

        return "form-add-pembelian";
    }

    @PostMapping(value = "pembelian/tambah", params = {"hapusRow"})
    public String addPembelianHapusRow(@ModelAttribute PembelianModel pembelian, @RequestParam("hapusRow") Integer row, Model model) {
        List<MemberModel> listMember = memberService.getListMember();
        List<PembelianBarangModel> listPB = pembelian.getListPembelianBarang();
        List<BarangModel> listBarangs = barangService.getListBarang();
        List<BarangModel> listBarang = new ArrayList<>();

        for (BarangModel i : listBarangs) {
            if (i.getStok() > 0) {
                listBarang.add(i);
            }
        }

        listPB.remove(listPB.get(row));
        pembelian.setListPembelianBarang(listPB);
        
        model.addAttribute("pembelian", pembelian);
        model.addAttribute("listMember", listMember);
        model.addAttribute("listBarang", listBarang);

        return "form-add-pembelian";
    }


    @GetMapping(value = "pembelian/hapus/{idPembelian}")
    public String deletePembelian(@PathVariable Long idPembelian, Model model) {
        PembelianModel pemb = pembelianService.getPembelianByIdPembelian(idPembelian);

        List<BarangModel> brgs = pembelianService.deletePembelian(pemb);

        model.addAttribute("delPemb", pemb);
        model.addAttribute("barangs", brgs);    
        
        return "hapus-pembelian-succeed";
    }

    @GetMapping(value = "cari/pembelian")
    public String cariPembelian(@RequestParam(value = "idMember", required = false) Long idMember, 
                                @RequestParam(value = "tipePembayaran", required = false) Boolean tipePembayaran, 
                                Model model) {
        
        List<PembelianModel> pembelians = new ArrayList<>();
        List<MemberModel> members = memberService.getListMember();
        List<Long> listQ = new ArrayList<Long>();
                        
        if ((idMember != null) && (tipePembayaran != null)) {
            for (PembelianModel i : pembelianService.getListPembelian()) {
                if (i.getMember().getId().longValue() == idMember.longValue()) {
                    if ((i.getIsCash().equals(tipePembayaran))) {
                        pembelians.add((i));
                    }
                }
            }

            for (int i = 0; i < pembelians.size(); i++) {
                Long temp = pembelianService.getTotalQuantityPembelian(pembelians.get(i));
    
                listQ.add(temp);
            }
        }

        model.addAttribute("listQuantity", listQ);
        model.addAttribute("listPembelian", pembelians);
        model.addAttribute("listMember", members);

        return "cari-pembelian";
    }

    @GetMapping(value = "cari/member/paling-banyak")
    public String cariTerbanyak(Model model) {
        Map<MemberModel, Integer> map = new HashMap<>();
        List<MemberModel> listMember = new ArrayList<>();
        List<Integer> listJumlah = new ArrayList<>();

        
        for (MemberModel i : memberService.getListMember()) {
            Integer tempJml = 0;
            List<PembelianModel> pembeliansTemp = i.getListPembelian();

            for (PembelianModel j : pembeliansTemp) {
                tempJml += j.getTotal();
            }

            map.put(i, tempJml);
        }

        Object[] a = map.entrySet().toArray();

        Arrays.sort(a, new Comparator() {
            public int compare(Object o1, Object o2) {
                return ((Map.Entry<MemberModel, Integer>) o2).getValue().compareTo(((Map.Entry<MemberModel, Integer>) o1).getValue());
            }
        });

        for (Object i : a) {
            listMember.add(((Map.Entry<MemberModel, Integer>) i).getKey());
            listJumlah.add(((Map.Entry<MemberModel, Integer>) i).getValue());
        }
        

        model.addAttribute("listMember", listMember);
        model.addAttribute("listJumlah", listJumlah);

        return "cari-member-terbanyak";
    }
    
}
