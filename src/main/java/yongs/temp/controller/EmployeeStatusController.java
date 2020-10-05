package yongs.temp.controller;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import yongs.temp.model.Employee;
import yongs.temp.model.EmployeeStatus;
import yongs.temp.model.Status;

@RestController
@RequestMapping("/employeestatus")
public class EmployeeStatusController {
	private Logger logger = LoggerFactory.getLogger(EmployeeStatusController.class);    
	private static String EMPLOYEE_URL = "http://flex-employee/employee";
	private static String STATUS_URL = "http://flex-status/status";
	
	@Autowired
    WebClient.Builder webClientBuilder;	
	
    /* 데이터 생성을 위한 임시 method */
    @PostMapping("/init")
    public Mono<Void> init(@RequestBody Map<String, Long> numOfEmp) {
    	logger.debug("flex-employeestatus|EmployeeStatusController|init({})", numOfEmp.get("num"));

    	// flex-employee 데이터 생성
    	WebClient employeeClient = webClientBuilder.baseUrl(EMPLOYEE_URL).build();
    	employeeClient.delete()
    		.uri("/clean")
    		.retrieve()
    		.bodyToMono(Void.class).subscribe();
    	
    	employeeClient.post()
    		.uri("/init")
    		.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
    		.body(Mono.just(numOfEmp), Map.class)
    		.retrieve()
    		.bodyToMono(Void.class).subscribe();
 
    	try {
    		Thread.sleep(10*1000L);    		
    	} catch (Exception e) {}
    	
    	// flex-status 데이터 생성
    	WebClient statusClient = webClientBuilder.baseUrl(STATUS_URL).build();
    	statusClient.delete()
    		.uri("/clean")
    		.retrieve()
    		.bodyToMono(Void.class).subscribe();
    	
    	statusClient.post()
    		.uri("/init")
    		.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
    		.body(Mono.just(numOfEmp), Map.class)
    		.retrieve()
    		.bodyToMono(Void.class).subscribe();
    	
    	return Mono.empty();
    }
    
    @GetMapping("/all")
    public Flux<EmployeeStatus> findAll() {
    	logger.debug("flex-employeestatus|EmployeeStatusController|findAll()");
    	
    	// flex-employee 데이터 조회
    	WebClient employeeClient = webClientBuilder.baseUrl(EMPLOYEE_URL).build();
    	Flux<Employee> employeeFlux = employeeClient.get()
    			.uri("/all")
    			.retrieve()
    			.bodyToFlux(Employee.class);
 
    	// flex-status 데이터 조회
    	WebClient statusClient = webClientBuilder.baseUrl(STATUS_URL).build();
    	Flux<Status> statusFlux = statusClient.get()
    			.uri("/all")
    			.retrieve()
    			.bodyToFlux(Status.class);
    	
    	// id순으로  sorting되어(0, 1, 2, 3 ...) 가져오므로 단순 zipWith가 가능함.
    	Flux<EmployeeStatus> employeeStatusFlux = employeeFlux
    			.zipWith(statusFlux, (a, b) -> 
    				new EmployeeStatus(a.getId(), 
    								   a.getName(),
    								   a.getSex(),
    								   a.getSalary(),
    								   b.getProject(),
    								   b.getSkill(),
    								   b.getLevel(),
    								   b.getGrade()));
    	return employeeStatusFlux;
    }
}
