package apap.tugas.sielekthor.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;

import apap.tugas.sielekthor.model.TipeModel;
import apap.tugas.sielekthor.repository.TipeDb;


@Service
@Transactional
public class TipeServiceImpl implements TipeService {

    @Autowired
    TipeDb tipeDb;

    @Override
    public List<TipeModel> getListTipe() {
        return tipeDb.findAll();
    }

}
