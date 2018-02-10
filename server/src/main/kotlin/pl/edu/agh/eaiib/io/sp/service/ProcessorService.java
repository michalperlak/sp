package pl.edu.agh.eaiib.io.sp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.edu.agh.eaiib.io.sp.common.model.ProcessedData;
import pl.edu.agh.eaiib.io.sp.common.model.Reading;
import pl.edu.agh.eaiib.io.sp.repositories.ProcessedDataRepository;
import pl.edu.agh.eaiib.io.sp.repositories.ReadingsRepository;
import pl.edu.agh.eaiib.io.sp.utils.EvaluatorHelper;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class ProcessorService {

    private final ProcessedDataRepository processedDataRepository;

    private final ReadingsRepository readingsRepository;

    @Autowired
    public ProcessorService(ProcessedDataRepository processedDataRepository, ReadingsRepository readingsRepository) {
        this.processedDataRepository = processedDataRepository;
        this.readingsRepository = readingsRepository;
    }

    public void clean() {
        processedDataRepository.deleteAll();
    }

    public void processData() {

    }

    public void processData(String deviceId, long start) {
        List<Reading> readingList = readingsRepository.findAll();
        Stream<Reading> readingStream = readingList.stream()
                .filter(x -> x.getDeviceId().equals(deviceId))
                .filter(x -> x.getTimestamp() >= start);

        Double min = readingStream.mapToDouble(x -> x.getAccelerometer().get(2)).min().orElse(0);
        Double max = readingStream.mapToDouble(x -> x.getAccelerometer().get(2)).max().orElse(20);

        List<ProcessedData> processedDataList =
                readingStream.map(x -> new ProcessedData(x.getLocation(), EvaluatorHelper.evaluate(x.getAccelerometer().get(2), min, max)))
                        .collect(Collectors.toList());

        processedDataRepository.saveAll(processedDataList);
    }
}
