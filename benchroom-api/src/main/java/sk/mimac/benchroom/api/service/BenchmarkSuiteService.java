package sk.mimac.benchroom.api.service;

import sk.mimac.benchroom.api.dto.Page;
import sk.mimac.benchroom.api.dto.impl.BenchmarkSuiteDto;
import sk.mimac.benchroom.api.filter.BenchmarkSuiteFilter;

/**
 *
 * @author Milan Fabian
 */
public interface BenchmarkSuiteService {

    BenchmarkSuiteDto getSuiteById(long id);

    long insertSuite(BenchmarkSuiteDto suite);
    
    void updateSuite(BenchmarkSuiteDto suite);
    
    Page<BenchmarkSuiteDto> getSuitePage(BenchmarkSuiteFilter filter);
}
