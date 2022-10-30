package com.huvenet.loatong.batch.testConfig;

import com.huvenet.loatong.batch.dto.OneDto;
import org.springframework.batch.item.file.transform.LineAggregator;

public class TextJob2LineAggreagator<T> implements LineAggregator<T> {
    @Override
    public String aggregate(T item) {
        if (item instanceof OneDto) {
            return item.toString() + "_item";
        }
        return item.toString();
    }
}
