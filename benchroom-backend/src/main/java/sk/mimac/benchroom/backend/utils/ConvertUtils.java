package sk.mimac.benchroom.backend.utils;

import java.util.HashSet;
import org.slf4j.LoggerFactory;
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
        dto.setReleaseDate(entity.getReleaseDate());
        dto.setSoftwareId(entity.getSoftware().getId());
        dto.setSoftwareName(entity.getSoftware().getName());
        return dto;
    }

    public static SoftwareVersion convert(SoftwareVersionDto dto) {
        SoftwareVersion entity = new SoftwareVersion(dto.getId());
        entity.setName(dto.getName());
        entity.setReleaseDate(dto.getReleaseDate());
        entity.setSoftware(new Software(dto.getSoftwareId()));
        return entity;
    }

    public static BenchmarkSuiteDto convert(BenchmarkSuite entity) {
        BenchmarkSuiteDto dto = new BenchmarkSuiteDto(entity.getId());
        dto.setName(entity.getName());
        dto.setSoftwareId(entity.getSoftware().getId());
        dto.setSoftwareName(entity.getSoftware().getName());
        dto.setSetupScript(entity.getSetupScript());
        dto.setCleanupScript(entity.getCleanupScript());
        return dto;
    }

    public static BenchmarkSuite convert(BenchmarkSuiteDto dto) {
        BenchmarkSuite entity = new BenchmarkSuite(dto.getId());
        entity.setName(dto.getName());
        entity.setSoftware(new Software(dto.getSoftwareId()));
        entity.setSetupScript(dto.getSetupScript());
        entity.setCleanupScript(dto.getCleanupScript());
        return entity;
    }

    public static BenchmarkParameter convert(BenchmarkParameterDto dto) {
        BenchmarkParameter entity = new BenchmarkParameter(dto.getId());
        entity.setName(dto.getName());
        entity.setCommandLineArguments(dto.getCommandLineArguments());
        entity.setCommandLineInput(dto.getCommandLineInput());
        entity.setBenchmarkSuite(new BenchmarkSuite(dto.getBenchmarkSuiteId()));
        return entity;
    }

    public static BenchmarkParameterDto convert(BenchmarkParameter entity) {
        BenchmarkParameterDto dto = new BenchmarkParameterDto(entity.getId());
        dto.setName(entity.getName());
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

    public static ScriptDto convert(Script entity) {
        ScriptDto dto = new ScriptDto(entity.getId());
        dto.setType(entity.getType());
        dto.setScriptData(entity.getScript());
        dto.setSoftwareVersionId(entity.getSoftwareVersion().getId());
        dto.setSupportedPlatforms(new HashSet<>(entity.getSupportedPlatforms()));
        return dto;
    }

    public static Script convert(ScriptDto dto) {
        Script entity = new Script(dto.getId());
        entity.setType(dto.getType());
        entity.setScript(dto.getScriptData());
        entity.setSoftwareVersion(new SoftwareVersion(dto.getSoftwareVersionId()));
        entity.setSupportedPlatforms(new HashSet<>(dto.getSupportedPlatforms()));
        return entity;
    }
}
