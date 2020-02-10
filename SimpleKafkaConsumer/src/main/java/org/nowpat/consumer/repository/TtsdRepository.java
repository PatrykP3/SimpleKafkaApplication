package org.nowpat.consumer.repository;

import java.util.ArrayList;
import java.util.List;

import org.nowpat.dto.TransportTestSubData;
import org.springframework.stereotype.Component;

@Component
public class TtsdRepository {

    private List<TransportTestSubData> repository = new ArrayList<>();

    public void add(TransportTestSubData record) {

        repository.add(record);
    }

    public List<TransportTestSubData> getAll() {

        return repository;
    }
}
