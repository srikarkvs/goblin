package com.example.goblin.readers;

import com.example.goblin.entity.JobConfig;
import com.example.goblin.enums.SourceType;
import com.example.goblin.interfaces.DataReader;
import com.example.goblin.interfaces.DataReaderFactory;
import com.example.goblin.sinks.CsvFileDataSink;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class DataReaderFactoryImpl implements DataReaderFactory {
    private static final Logger log = LoggerFactory.getLogger(DataReaderFactoryImpl.class);
    private final Map<String, DataReader<?>> readerMap;

    @Autowired
    public DataReaderFactoryImpl(Map<String, DataReader<?>> readerMap) {
            this.readerMap = readerMap;
        }

        @Override
        public DataReader<?> getReader(JobConfig config) {
            SourceType sourceType = config.getSourceType();
            DataReader<?> reader = readerMap.get(sourceType.name());
            if (reader == null) {
                log.error("No reader registered for source type: {}", sourceType);
                throw new IllegalArgumentException();
            }
            return reader;
        }

}
