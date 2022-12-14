package vn.com.tpf.microservices.services;

import org.springframework.beans.factory.annotation.Autowired;
import vn.com.tpf.microservices.models.Automation.LoginDTO;

import java.util.Map;
import java.util.Queue;

public class AutomationThreadService implements Runnable {
    private Queue<LoginDTO> accounts;
    private String browser;
    private Map<String, Object> mapData;
    private String function;
    private String project;


    @Autowired
    private AutomationHandlerService automationHandlerService;

    public AutomationThreadService(Queue<LoginDTO> accounts, String browser, Map<String, Object> mapData, String function,String project) {
        super();
        this.accounts = accounts;
        this.browser = browser;
        this.mapData = mapData;
        this.function=function;
        this.project=project;
    }

    @Override
    public void run() {
        automationHandlerService.executor(accounts, browser, mapData,function,project);
    }
}