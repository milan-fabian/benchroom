package sk.mimac.benchroom.backend.utils;

import sk.mimac.benchroom.api.dto.impl.*;
import sk.mimac.benchroom.backend.persistence.entity.*;

/**
 *
 * @author Milan Fabian
 */
public class ConvertUtils {

    public static SoftwareDto convert(Software entity) {
        SoftwareDto dto = new SoftwareDto(entity.getId());
        dto.setName(entity.getName());
        return dto;
    }

    public static Software convert(SoftwareDto dto) {
        Software entity = new Software(dto.getId());
        entity.setName(dto.getName());
        return entity;
    }

    public static SoftwareVersionDto convert(SoftwareVersion entity) {
        SoftwareVersionDto dto = new SoftwareVersionDto(entity.getId());
        dto.setName(entity.getName());
        return dto;
    }

    public static SoftwareVersion convert(SoftwareVersionDto dto) {
        SoftwareVersion entity = new SoftwareVersion(dto.getId());
        entity.setName(dto.getName());
        return entity;
    }

    public static BenchmarkSuiteDto convert(BenchmarkSuite entity) {
        BenchmarkSuiteDto dto = new BenchmarkSuiteDto(entity.getId());
        dto.setName(entity.getName());
        dto.setSoftwareId(entity.getSoftware().getId());
        dto.setSoftwareName(entity.getSoftware().getName());
        return dto;
    }

    public static BenchmarkSuite convert(BenchmarkSuiteDto dto) {
        BenchmarkSuite entity = new BenchmarkSuite(dto.getId());
        entity.setName(dto.getName());
        entity.setSoftware(new Software(dto.getSoftwareId()));
        return entity;
    }

    public static BenchmarkParameter convert(BenchmarkParameterDto dto) {
        BenchmarkParameter entity = new BenchmarkParameter(dto.getId());
        entity.setNote(dto.getNote());
        entity.setCommandLineArguments(dto.getCommandLineArguments());
        entity.setCommandLineInput(dto.getCommandLineInput());
        entity.setBenchmarkSuite(new BenchmarkSuite(dto.getBenchmarkSuiteId()));
        return entity;
    }

    public static BenchmarkParameterDto convert(BenchmarkParameter entity) {
        BenchmarkParameterDto dto = new BenchmarkParameterDto(entity.getId());
        dto.setNote(entity.getNote());
        dto.setCommandLineArguments(entity.getCommandLineArguments());
        dto.setCommandLineInput(entity.getCommandLineInput());
        dto.setBenchmarkSuiteId(entity.getBenchmarkSuite().getId());
        return dto;
    }
    
    public static BenchmarkRunDto convert(BenchmarkRun entity) {
        BenchmarkRunDto dto = new BenchmarkRunDto(entity.getId());
        dto.setAverageRunTime(entity.getAverageRunTime());
        dto.setBenchmarkParameter(convert(entity.getBenchmarkParameter()));
        dto.setCommandLineOutput(entity.getCommandLineOutput());
        dto.setHardwareParameters(entity.getHardwareParameters());
        dto.setNumberOfRuns(entity.getNumberOfRuns());
        dto.setResultCode(entity.getResultCode());
        dto.setSoftwareVersion(convert(entity.getSoftwareVersion()));
        dto.setWhenStarted(entity.getWhenStarted());
        return dto;
    }

}
