package apap.tugas.sielekthor.service;


import java.util.Date;
import java.time.format.DateTimeFormatter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.Calendar;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import apap.tugas.sielekthor.model.BarangModel;
import apap.tugas.sielekthor.model.PembelianBarangModel;
import apap.tugas.sielekthor.model.PembelianModel;
import apap.tugas.sielekthor.repository.PembelianBarangDb;
import apap.tugas.sielekthor.repository.PembelianDb;


@Service
@Transactional
public class PembelianServiceImpl implements PembelianService {

    @Autowired
    PembelianDb pembelianDb;

    @Autowired
    PembelianBarangDb pembelianBarangDb;

    @Override
    public String invoicing(PembelianModel pembelian) {
        String ch1 = Integer.toString((pembelian.getMember().getNamaMember().toLowerCase().toCharArray()[0] - 96) % 10);

        String ch2x = pembelian.getNamaAdmin().toUpperCase();
        String ch2 = Character.toString(ch2x.charAt(ch2x.length() - 1));
        

        for (String i : pembelian.getTanggalPembelian().toString().split("-")) {
            System.out.println(i);
        }

        DateFormat dtf = new SimpleDateFormat("ddMM");
        String ch3_6 = dtf.format(pembelian.getTanggalPembelian());

        String ch7_8 = ((pembelian.getIsCash() == true) ? "02" : "01");

        Integer ch9_11x = (Integer.valueOf(ch3_6.substring(0, 2)) + Integer.valueOf(ch3_6.substring(2, 4)) * 5);
        String ch9_11 = ((Integer.toString(ch9_11x).length() < 3) ? ("0" + (Integer.toString(ch9_11x))) : Integer.toString(ch9_11x));

        Random rnd = new Random();
        char ch12x = (char) ('a' + rnd.nextInt(26));
        char ch13x = (char) ('a' + rnd.nextInt(26));
        String ch12_13 = (Character.toString(ch12x) + Character.toString(ch13x)).toUpperCase();

        return (ch1 + ch2 + ch3_6 + ch7_8 + ch9_11 + ch12_13);
    }

    @Override
    public List<PembelianModel> getListPembelian() {
        return pembelianDb.findAll();
    }

    @Override
    public Long getTotalQuantityPembelian(PembelianModel pembelian) {
        Long jmlQuantity = 0L;
        List<PembelianBarangModel> temp = pembelianBarangDb.findByPembelian(pembelian);

        for (int i = 0; i < temp.size(); i++) {
            jmlQuantity += temp.get(i).getQuantity();
        }

        return jmlQuantity;
    }

    @Override
    public List<PembelianBarangModel> getAllPembelianbarang(PembelianModel pembelian) {
        return pembelianBarangDb.findByPembelian(pembelian);
    }

    @Override
    public Long getTotalHargaPembelian(PembelianModel pembelian) {
        List<PembelianBarangModel> temp = pembelianBarangDb.findByPembelian(pembelian);
        Long totalTemp = 0L;

        for (int i = 0; i < temp.size(); i++) {
            Integer jmlHarga = temp.get(i).getBarang().getHargaBarang();
            Integer jmlQuantity = temp.get(i).getQuantity();

            totalTemp += jmlHarga * jmlQuantity;
        }

        return totalTemp;
    }

    @Override
    public PembelianModel getPembelianByIdPembelian(Long id) {
        Optional<PembelianModel> pmbl = pembelianDb.findById(id);

        if (pmbl.isPresent()) {
            return pmbl.get();
        } else {
            return null;
        }
    }

    @Override
    public List<BarangModel> getBarangs(PembelianModel pembelian) {
        List<PembelianBarangModel> temp = pembelianBarangDb.findByPembelian(pembelian);
        List<BarangModel> listBarangs = new ArrayList<BarangModel>();
        
        for (int i = 0; i < temp.size(); i++) {
            listBarangs.add(temp.get(i).getBarang());
        }

        return listBarangs;
    }

    @Override
    public PembelianModel addPembelian(PembelianModel pembelian) {
        Date currDate = new Date();
        Integer tempTotal = 0;        
        
        for (int i = 0; i < pembelian.getListPembelianBarang().size(); i++) {
            Calendar c = Calendar.getInstance();
            Date dateTemp = new Date();
            PembelianBarangModel tempPemb = pembelian.getListPembelianBarang().get(i);

            int tempX = tempPemb.getQuantity() * tempPemb.getBarang().getHargaBarang();
            
            tempTotal += tempX;

            c.setTime(dateTemp);
            c.add(Calendar.DATE, tempPemb.getBarang().getJumlahGaransi());
            dateTemp = c.getTime();
            
            tempPemb.setTanggalGaransi(dateTemp);
            tempPemb.setPembelian(pembelian);

            tempPemb.getBarang().setStok(tempPemb.getBarang().getStok() - tempPemb.getQuantity());
        }


        pembelian.setTanggalPembelian(currDate);
        pembelian.setNoInvoice(invoicing(pembelian));
        pembelian.setTotal(tempTotal);


        pembelianDb.save(pembelian);
        
        return pembelian;
    }

    @Override
    public List<BarangModel> deletePembelian(PembelianModel pembelian) {
        List<BarangModel> temp = new ArrayList<>();

        for (PembelianBarangModel i : pembelian.getListPembelianBarang()) {
            BarangModel tempBarang = new BarangModel();

            i.getBarang().setStok(i.getBarang().getStok() + i.getQuantity());

            tempBarang.setNamaBarang(i.getBarang().getNamaBarang());
            tempBarang.setStok(i.getBarang().getStok() + i.getQuantity());

            temp.add(tempBarang);
        }


        pembelianDb.delete(pembelian);
        
        return temp;
    }
    
}
