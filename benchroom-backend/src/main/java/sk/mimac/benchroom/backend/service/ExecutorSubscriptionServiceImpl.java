package sk.mimac.benchroom.backend.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sk.mimac.benchroom.api.dto.impl.BenchmarkRunJobDto;
import sk.mimac.benchroom.api.dto.impl.ExecutorDto;
import sk.mimac.benchroom.api.enums.JobStatus;
import sk.mimac.benchroom.api.service.ExecutorSubscriptionService;
import sk.mimac.benchroom.api.system.SystemParameter;
import sk.mimac.benchroom.backend.persistence.dao.BenchmarkRunJobDao;
import sk.mimac.benchroom.backend.persistence.dao.ExecutorDao;
import sk.mimac.benchroom.backend.persistence.dao.SystemInfoDao;
import sk.mimac.benchroom.backend.persistence.entity.BenchmarkRunJob;
import sk.mimac.benchroom.backend.persistence.entity.Executor;
import sk.mimac.benchroom.backend.persistence.entity.SystemInfo;
import sk.mimac.benchroom.backend.utils.ConvertUtils;

/**
 *
 * @author Milan Fabian
 */
@Service
@Transactional(readOnly = true)
public class ExecutorSubscriptionServiceImpl implements ExecutorSubscriptionService {

    @Autowired
    private ExecutorDao executorDao;

    @Autowired
    private SystemInfoDao systemInfoDao;

    @Autowired
    private BenchmarkRunJobDao benchmarkRunJobDao;

    @Override
    @Transactional(readOnly = false)
    public BenchmarkRunJobDto checkRunJob(Map<SystemParameter, String> systemParameters) {
        Long systemInfoId = systemInfoDao.getWithSameParameters(systemParameters);
        if (systemInfoId == null) {
            SystemInfo systemInfo = new SystemInfo();
            systemInfo.setParameters(systemParameters);
            systemInfoDao.insert(systemInfo);
            createExecutor(systemInfo);
            return null;
        }
        Executor executor = executorDao.getBySystemInfo(systemInfoId);
        if (executor == null) {
            createExecutor(new SystemInfo(systemInfoId));
            return null;
        }
        Optional<BenchmarkRunJob> runJobOptional = executor.getRunJobs().stream().filter(job -> job.getStatus() == JobStatus.PREPARED).findFirst();
        if (runJobOptional.isPresent()) {
            BenchmarkRunJob runJob = runJobOptional.get();
            runJob.setStatus(JobStatus.RUNNING);
            benchmarkRunJobDao.update(runJob);
            return ConvertUtils.convert(runJob);
        }
        return null;
    }

    @Override
    @Transactional(readOnly = false)
    public void addRunJob(long executorId, BenchmarkRunJobDto runJobDto) {
        Executor executor = executorDao.find(executorId);
        BenchmarkRunJob runJob = ConvertUtils.convert(runJobDto);
        runJob.setStatus(JobStatus.PREPARED);
        runJob.setExecutor(executor);
        if (executor.getRunJobs() != null) {
            executor.getRunJobs().add(runJob);
        } else {
            List<BenchmarkRunJob> list = new ArrayList<>();
            list.add(runJob);
            executor.setRunJobs(list);
        }
        executorDao.update(executor);
    }

    @Override
    public Collection<ExecutorDto> getAllExecutors() {
        List<ExecutorDto> executors = new ArrayList<>();
        for (Executor executor : executorDao.getAll()) {
            executors.add(ConvertUtils.convert(executor));
        }
        return executors;
    }

    @Override
    public ExecutorDto getExecutorById(long id) {
        return ConvertUtils.convert(executorDao.find(id));
    }

    @Override
    @Transactional(readOnly = false)
    public void changeRunJobStatus(long runJobId, JobStatus status) {
        BenchmarkRunJob runJob = benchmarkRunJobDao.find(runJobId);
        runJob.setStatus(status);
        benchmarkRunJobDao.update(runJob);
    }

    private void createExecutor(SystemInfo systemInfo) {
        Executor executor = new Executor();
        executor.setSystemInfo(systemInfo);
        executorDao.insert(executor);
    }

}
