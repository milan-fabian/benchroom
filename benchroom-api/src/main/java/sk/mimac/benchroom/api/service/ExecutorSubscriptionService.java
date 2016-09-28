package sk.mimac.benchroom.api.service;

import java.util.Collection;
import java.util.Map;
import sk.mimac.benchroom.api.dto.impl.BenchmarkRunJobDto;
import sk.mimac.benchroom.api.dto.impl.ExecutorDto;
import sk.mimac.benchroom.api.enums.JobStatus;
import sk.mimac.benchroom.api.system.SystemParameter;

/**
 *
 * @author Milan Fabian
 */
public interface ExecutorSubscriptionService {

    BenchmarkRunJobDto checkRunJob(Map<SystemParameter, String> systemParameters);

    void addRunJob(long executorId, BenchmarkRunJobDto runJob);

    Collection<ExecutorDto> getAllExecutors();

    ExecutorDto getExecutorById(long id);
    
    void changeRunJobStatus(long runJobId, JobStatus status);
}
