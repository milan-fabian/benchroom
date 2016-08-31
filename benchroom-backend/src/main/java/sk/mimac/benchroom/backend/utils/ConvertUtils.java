package sk.mimac.benchroom.backend.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
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
        dto.setCommandLineArguments(entity.getCommandLineArguments());
        dto.setParameterPositions(entity.getParameterPositions());
        dto.setAfterEachRunScript(entity.getAfterEachRunScript());
        return dto;
    }

    public static BenchmarkSuite convert(BenchmarkSuiteDto dto) {
        BenchmarkSuite entity = new BenchmarkSuite(dto.getId());
        entity.setName(dto.getName());
        entity.setSoftware(new Software(dto.getSoftwareId()));
        entity.setSetupScript(dto.getSetupScript());
        entity.setCleanupScript(dto.getCleanupScript());
        entity.setCommandLineArguments(dto.getCommandLineArguments());
        entity.setParameterPositions(dto.getParameterPositions());
        entity.setAfterEachRunScript(dto.getAfterEachRunScript());
        return entity;
    }

    public static BenchmarkParameter convert(BenchmarkParameterDto dto) {
        BenchmarkParameter entity = new BenchmarkParameter(dto.getId());
        entity.setName(dto.getName());
        entity.setCommandLineArguments(dto.getCommandLineArguments());
        entity.setPriority(dto.getPriority());
        entity.setPosition(dto.getPosition());
        entity.setBenchmarkSuite(new BenchmarkSuite(dto.getBenchmarkSuiteId()));
        entity.setSetupScript(dto.getSetupScript());
        entity.setCleanupScript(dto.getCleanupScript());
        return entity;
    }

    public static BenchmarkParameterDto convert(BenchmarkParameter entity) {
        BenchmarkParameterDto dto = new BenchmarkParameterDto(entity.getId());
        dto.setName(entity.getName());
        dto.setCommandLineArguments(entity.getCommandLineArguments());
        dto.setPriority(entity.getPriority());
        dto.setPosition(entity.getPosition());
        dto.setBenchmarkSuiteId(entity.getBenchmarkSuite().getId());
        dto.setSetupScript(entity.getSetupScript());
        dto.setCleanupScript(entity.getCleanupScript());
        return dto;
    }

    public static BenchmarkRunDto convert(BenchmarkRun entity) {
        BenchmarkRunDto dto = new BenchmarkRunDto(entity.getId());
        dto.setSystemParameters(new HashMap<>(entity.getSystemParameters()));
        dto.setSoftwareVersion(convert(entity.getSoftwareVersion()));
        dto.setWhenStarted(entity.getWhenStarted());
        dto.setBenchmarkSuiteId(entity.getBenchmarkSuite().getId());
        List<BenchmarkRunResultDto> results = new ArrayList<>();
        for (BenchmarkRunResult result : entity.getResults()) {
            results.add(convert(result));
        }
        dto.setResults(results);
        List<BenchmarkParameterDto> parameters = new ArrayList<>();
        for (BenchmarkParameter result : entity.getBenchmarkParameters()) {
            parameters.add(convert(result));
        }
        dto.setBenchmarkParameters(parameters);
        return dto;
    }

    public static BenchmarkRunSimpleDto convertToSimple(BenchmarkRun entity) {
        BenchmarkRunSimpleDto dto = new BenchmarkRunSimpleDto(entity.getId());
        dto.setBenchmarkParameters(entity.getBenchmarkParameters().stream().map(parameter -> parameter.getName()).collect(Collectors.toList()).toString());
        dto.setSystemParameters(entity.getSystemParameters().toString());
        dto.setWhenStarted(entity.getWhenStarted());
        StringBuilder builder = new StringBuilder();
        for (BenchmarkRunResult result : entity.getResults()) {
            builder.append(result.getMonitor().getName()).append(": ").append(result.getResult()).append(", ");
        }
        dto.setResults(builder.toString());
        return dto;
    }

    public static BenchmarkRun convert(BenchmarkRunDto dto) {
        BenchmarkRun entity = new BenchmarkRun(dto.getId());
        Set<BenchmarkParameter> parameters = new HashSet<>();
        for (BenchmarkParameterDto parameter : dto.getBenchmarkParameters()) {
            parameters.add(new BenchmarkParameter(parameter.getId()));
        }
        entity.setBenchmarkParameters(parameters);
        entity.setSystemParameters(dto.getSystemParameters());
        entity.setSoftwareVersion(new SoftwareVersion(dto.getSoftwareVersion().getId()));
        entity.setWhenStarted(dto.getWhenStarted());
        entity.setBenchmarkSuite(new BenchmarkSuite(dto.getBenchmarkSuiteId()));
        return entity;
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

    public static BenchmarkMonitorDto convert(BenchmarkMonitor entity) {
        BenchmarkMonitorDto dto = new BenchmarkMonitorDto(entity.getId());
        dto.setName(entity.getName());
        dto.setAction(entity.getAction());
        dto.setBenchmarkSuiteId(entity.getBenchmarkSuite().getId());
        dto.setType(entity.getType());
        return dto;
    }

    public static BenchmarkMonitor convert(BenchmarkMonitorDto dto) {
        BenchmarkMonitor entity = new BenchmarkMonitor(dto.getId());
        entity.setName(dto.getName());
        entity.setAction(dto.getAction());
        entity.setBenchmarkSuite(new BenchmarkSuite(dto.getBenchmarkSuiteId()));
        entity.setType(dto.getType());
        return entity;
    }

    public static BenchmarkRunResultDto convert(BenchmarkRunResult entity) {
        BenchmarkRunResultDto dto = new BenchmarkRunResultDto(entity.getId());
        dto.setResult(entity.getResult());
        dto.setMonitorName(entity.getMonitor().getName());
        dto.setMonitorType(entity.getMonitor().getType());
        return dto;
    }
}
