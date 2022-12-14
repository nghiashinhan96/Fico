package vn.com.tpf.microservices.services;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.apache.commons.lang.StringUtils;
import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import vn.com.tpf.microservices.models.AutoAllocation.AutoAllocationDTO;
import vn.com.tpf.microservices.models.AutoAssign.AutoAssignDTO;
import vn.com.tpf.microservices.models.AutoCRM.CRM_ExistingCustomerDTO;
import vn.com.tpf.microservices.models.AutoCRM.CRM_SaleQueueDTO;
import vn.com.tpf.microservices.models.AutoField.RequestAutomationDTO;
import vn.com.tpf.microservices.models.AutoField.SubmitFieldDTO;
import vn.com.tpf.microservices.models.AutoField.WaiveFieldDTO;
import vn.com.tpf.microservices.models.AutoMField.MFieldRequest;
import vn.com.tpf.microservices.models.AutoMField.WaiveFieldDetails;
import vn.com.tpf.microservices.models.DEReturn.DEResponseQueryMulDocDTO;
import vn.com.tpf.microservices.models.AutoQuickLead.QuickLeadDTO;
import vn.com.tpf.microservices.models.AutoQuickLead.QuickLeadDetails;
import vn.com.tpf.microservices.models.AutoReturnQuery.ResponseQueryDTO;
import vn.com.tpf.microservices.models.AutoReturnQuery.ResponseQueryDetails;
import vn.com.tpf.microservices.models.AutoReturnQuery.SaleQueueDTO;
import vn.com.tpf.microservices.models.AutoReturnQuery.SaleQueueDetails;
import vn.com.tpf.microservices.models.Automation.LoginDTO;
import vn.com.tpf.microservices.models.AutomationMonitor.AutomationMonitorDTO;
import vn.com.tpf.microservices.models.DEReturn.DEResponseQueryDTO;
import vn.com.tpf.microservices.models.DEReturn.DESaleQueueDTO;
import vn.com.tpf.microservices.models.QuickLead.Application;
import vn.com.tpf.microservices.utilities.Constant;
import vn.com.tpf.microservices.utilities.DataInitial;

import javax.annotation.PostConstruct;
import javax.management.Query;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

@Service
public class AutomationService {

	private ObjectNode error;

	@Autowired
	private ObjectMapper mapper;

	@Autowired
	private RabbitMQService rabbitMQService;

	@Autowired
	private AutomationHandlerService automationHandlerService;

	@Autowired
	private MongoTemplate mongoTemplate;

	@Autowired
	private ApplicationContext applicationContext;

	private WebDriver driver;
	final static ExecutorService workerThreadPool = Executors.newFixedThreadPool(Constant.THREAD_NUM);
	final static List<LoginDTO> accounts= Arrays.asList(
//            LoginDTO.builder().userName("auto_data_entry_1").password("Hcm@12345").build(),
//            LoginDTO.builder().userName("auto_data_entry_2").password("Hcm@12345").build(),
//            LoginDTO.builder().userName("auto_data_entry_3").password("Hcm@12345").build(),
//            LoginDTO.builder().userName("auto_data_entry_4").password("Hcm@12345").build(),
//            LoginDTO.builder().userName("auto_data_entry_5").password("Hcm@12345").build(),
			LoginDTO.builder().userName("anhdlh").password("Tpf@1234").build()
	);
	final static Queue<LoginDTO> loginDTOQueue = new LinkedBlockingQueue<>(accounts);


//	final static List<LoginDTO> momoAccounts= Arrays.asList(
//			LoginDTO.builder().userName("momo_auto5").password("Hcm@12345").build(),
//            LoginDTO.builder().userName("momo_auto12").password("Hcm@12345").build()
////            LoginDTO.builder().userName("momo_auto2").password("Hcm@12345").build(),
////            LoginDTO.builder().userName("momo_auto3").password("Hcm@12345").build(),
////            LoginDTO.builder().userName("momo_auto4").password("Hcm@12345").build(),
//
//	);
//	final static Queue<LoginDTO> momo_loginDTOQueue = new LinkedBlockingQueue<>(momoAccounts);

	final static List<LoginDTO> momoAccountsPro= Arrays.asList(
			LoginDTO.builder().userName("momo_auto1").password("Tpf@12345").build(),
			LoginDTO.builder().userName("momo_auto2").password("Tpf@12345").build(),
			LoginDTO.builder().userName("momo_auto3").password("Tpf@12345").build()
	);
	final static Queue<LoginDTO> momo_loginDTOQueue = new LinkedBlockingQueue<>(momoAccountsPro);


	final static List<LoginDTO> fptAccounts= Arrays.asList(
			LoginDTO.builder().userName("auto_1").password("Hcm@12345").build()
//            LoginDTO.builder().userName("momo_auto2").password("Hcm@12345").build(),
//            LoginDTO.builder().userName("momo_auto3").password("Hcm@12345").build(),
//            LoginDTO.builder().userName("momo_auto4").password("Hcm@12345").build(),
//            LoginDTO.builder().userName("momo_auto5").password("Hcm@12345").build()
	);
	final static Queue<LoginDTO> fpt_loginDTOQueue = new LinkedBlockingQueue<>(fptAccounts);

	@PostConstruct
	private void init() {

	}

	private Map<String, Object> response(int code, JsonNode body, Object data) {
		Map<String, Object> res = new HashMap<>();
		res.put("result_code", code);
		res.put("request_id", body.path("request_id").asText());
		res.put("date_time", body.path("date_time").asText());
		res.put("reference_id", body.path("reference_id").asText());
		if (code == 0) {
			res.put("data", mapper.convertValue(data, JsonNode.class));
		} else {
			res.put("message", mapper.convertValue(data, JsonNode.class).get("message"));
		}
		return Map.of("status", 200, "data", res);
	}

	//------------------------ DATA ENTRY - FUNCTION -------------------------------------

	public Map<String, Object> quickLeadApp(JsonNode request) throws Exception {
		JsonNode body = request.path("body");

		System.out.println(request);
		Assert.notNull(request.get("body"), "no body");
		Application application = mapper.treeToValue(request.path("body"), Application.class);

		new Thread(() -> {
			try {
				runAutomationDE_QL(application);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}).start();

		return response(0, body, application.getQuickLead());
	}

	public Map<String, Object> fullInfoApp(JsonNode request) throws Exception {
		JsonNode body = request.path("body");

		System.out.println(request);

		Assert.notNull(request.get("body"), "no body");
		Application application = mapper.treeToValue(request.path("body"), Application.class);

		new Thread(() -> {
			try {
				runAutomationDE_UpdateInfomation(application);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}).start();

		return response(0, body, application);
	}

	public Map<String, Object> updateAppError(JsonNode request) throws Exception {
		JsonNode body = request.path("body");

		System.out.println(request);

		Assert.notNull(request.get("body"), "no body");
		Application application = mapper.treeToValue(request.path("body"), Application.class);

		new Thread(() -> {
			try {
				runAutomationDE_UpdateAppError(application);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}).start();

		return response(0, body, application);
	}

	private void runAutomationDE_QL(Application application) throws Exception {
		String browser = "chrome";
		Map<String, Object> mapValue = DataInitial.getDataFromDE_QL(application);

		AutomationThreadService automationThreadService= new AutomationThreadService(loginDTOQueue, browser, mapValue,"quickLead","DATAENTRY");
		applicationContext.getAutowireCapableBeanFactory().autowireBean(automationThreadService);
		workerThreadPool.submit(automationThreadService);

		//awaitTerminationAfterShutdown(workerThreadPool);
	}

	private void runAutomationDE_UpdateInfomation(Application application) throws Exception {
		String browser = "chrome";
		Map<String, Object> mapValue = DataInitial.getDataFromDE(application);

		AutomationThreadService automationThreadService= new AutomationThreadService(loginDTOQueue, browser, mapValue,"runAutomation_UpdateInfo","DATAENTRY");
		applicationContext.getAutowireCapableBeanFactory().autowireBean(automationThreadService);
		workerThreadPool.submit(automationThreadService);

		//awaitTerminationAfterShutdown(workerThreadPool);
	}

	private void runAutomationDE_UpdateAppError(Application application) throws Exception {
		String browser = "chrome";
		Map<String, Object> mapValue = DataInitial.getDataFromDE(application);

		AutomationThreadService automationThreadService= new AutomationThreadService(loginDTOQueue, browser, mapValue,"runAutomation_UpdateAppError","DATAENTRY");
		applicationContext.getAutowireCapableBeanFactory().autowireBean(automationThreadService);
		workerThreadPool.submit(automationThreadService);

		//awaitTerminationAfterShutdown(workerThreadPool);
	}

	//------------------------- END DATA ENTRY - FUNCTION ---------------------------------

	//------------------------ MOMO - FUNCTION -------------------------------------

	public Map<String, Object> momoCreateApp(JsonNode request) throws Exception {
		JsonNode body = request.path("body");
		String reference_id = request.path("reference_id").asText();

		System.out.println(request);

		Assert.notNull(request.get("body"), "no body");
		Application application = mapper.treeToValue(request.path("body"), Application.class);
		application.setReference_id(reference_id);

		System.out.println(mapper.writeValueAsString(application));

		new Thread(() -> {
			try {
				runAutomationMomo_CreateApp(application);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}).start();

		return response(0, body, application);
	}

	private void runAutomationMomo_CreateApp(Application application) throws Exception {
		String browser = "chrome";
		Map<String, Object> mapValue = DataInitial.getDataMomo(application);
//
//		//get list account finone available
//		Query query = new Query();
//		query.addCriteria(Criteria.where("active").is(0));
//		List<AccountFinOneDTO> accountFinOneDTOList=mongoTemplate.find(query, AccountFinOneDTO.class);
//		if(accountFinOneDTOList ==null || accountFinOneDTOList.size()==0)
//		{
//
//		}

		AutomationThreadService automationThreadService= new AutomationThreadService(loginDTOQueue, browser, mapValue,"momoCreateApp","MOMO");
		applicationContext.getAutowireCapableBeanFactory().autowireBean(automationThreadService);
		workerThreadPool.submit(automationThreadService);

		//awaitTerminationAfterShutdown(workerThreadPool);
	}

	public Map<String, Object> fptCreateApp(JsonNode request) throws Exception {
		JsonNode body = request.path("body");
		String reference_id = request.path("reference_id").asText();

		System.out.println(request);

		Assert.notNull(request.get("body"), "no body");
		Application application = mapper.treeToValue(request.path("body"), Application.class);
		application.setReference_id(reference_id);

		System.out.println(mapper.writeValueAsString(application));

		new Thread(() -> {
			try {
				runAutomationFpt_CreateApp(application);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}).start();

		return response(0, body, application);
	}

	private void runAutomationFpt_CreateApp(Application application) throws Exception {
		String browser = "chrome";
		Map<String, Object> mapValue = DataInitial.getDataFpt(application);

		AutomationThreadService automationThreadService= new AutomationThreadService(fpt_loginDTOQueue, browser, mapValue,"fptCreateApp","FPT");
		applicationContext.getAutowireCapableBeanFactory().autowireBean(automationThreadService);
		workerThreadPool.submit(automationThreadService);

		//awaitTerminationAfterShutdown(workerThreadPool);
	}

	//------------------------- END MOMO - FUNCTION ---------------------------------

	//------------------------ AUTO ASSIGN - FUNCTION -------------------------------------
	public Map<String, Object> DE_AutoAssign(JsonNode request) throws Exception {
		JsonNode body = request.path("body");
		String reference_id = request.path("reference_id").asText();

		System.out.println(request);

		Assert.notNull(request.get("body"), "no body");
		List<AutoAssignDTO> autoAssignDTOList = mapper.convertValue(request.path("body").path("data"), new TypeReference<List<AutoAssignDTO>>(){});

		new Thread(() -> {
			try {
				runAutomationDE_AutoAssign(autoAssignDTOList);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}).start();

		return response(0, body, autoAssignDTOList);
	}

	private void runAutomationDE_AutoAssign(List<AutoAssignDTO> autoAssignDTOList) throws Exception {
		String browser = "chrome";
		Map<String, Object> mapValue = DataInitial.getDataFromDE_AutoAssign(autoAssignDTOList);

		AutomationThreadService automationThreadService= new AutomationThreadService(loginDTOQueue, browser, mapValue,"runAutomationDE_AutoAssign","DE");
		applicationContext.getAutowireCapableBeanFactory().autowireBean(automationThreadService);
		workerThreadPool.submit(automationThreadService);

		//awaitTerminationAfterShutdown(workerThreadPool);
	}
	//------------------------ END AUTO ASSIGN - FUNCTION -------------------------------------

	//------------------------ DE_ResponseRQ61 - FUNCTION -------------------------------------
	public Map<String, Object> deResponseQueryRQ61(JsonNode request) throws Exception {
		JsonNode body = request.path("body");
		System.out.println(request);
		Assert.notNull(request.get("body"), "no body");
		DEResponseQueryMulDocDTO deResponseQueryMulDocDTOList = mapper.treeToValue(request.path("body"), DEResponseQueryMulDocDTO.class);

		runAutomationDE_ResponseRQ61(deResponseQueryMulDocDTOList);

		return response(0, body, deResponseQueryMulDocDTOList);
	}

	// ----------------------- runAutomationDE_ResponseRQ61 -----------------------------------
	private void runAutomationDE_ResponseRQ61(DEResponseQueryMulDocDTO deResponseQueryMulDocDTOList) throws Exception {
		String browser = "chrome";
		String projectJson = deResponseQueryMulDocDTOList.getProject();
		System.out.println("project: "+projectJson);
		Map<String, Object> mapValue = DataInitial.getDataFromDE_ResponseQueryMulDoc(deResponseQueryMulDocDTOList);
		System.out.println("mapValue :"+mapValue.toString());
		AutomationThreadService automationThreadService = null;
		automationThreadService= new AutomationThreadService(loginDTOQueue, browser, mapValue,"runAutomationDE_ResponseQueryRQ61",projectJson.toUpperCase());
		applicationContext.getAutowireCapableBeanFactory().autowireBean(automationThreadService);
		workerThreadPool.submit(automationThreadService);
	}
	//------------------------ DE_ResponseQueryMulDoc - FUNCTION -------------------------------------
	public Map<String, Object> DE_ResponseQueryMulDoc(JsonNode request) throws Exception {
		JsonNode body = request.path("body");
		System.out.println(request);
		Assert.notNull(request.get("body"), "no body");
		DEResponseQueryMulDocDTO deResponseQueryMulDocDTOList = mapper.treeToValue(request.path("body"), DEResponseQueryMulDocDTO.class);

		runAutomationDE_ResponseQueryMulDoc(deResponseQueryMulDocDTOList);

		return response(0, body, deResponseQueryMulDocDTOList);
	}

	private void runAutomationDE_ResponseQueryMulDoc(DEResponseQueryMulDocDTO deResponseQueryMulDocDTOList) throws Exception {
		String browser = "chrome";
		String projectJson = deResponseQueryMulDocDTOList.getProject();
		Map<String, Object> mapValue = DataInitial.getDataFromDE_ResponseQueryMulDoc(deResponseQueryMulDocDTOList);
		AutomationThreadService automationThreadService = null;
		if("smartnet".equals(projectJson)) {
			automationThreadService= new AutomationThreadService(loginDTOQueue, browser, mapValue,"runAutomationDE_ResponseQueryMulDoc","RETURN");
		}else{
			automationThreadService= new AutomationThreadService(loginDTOQueue, browser, mapValue,"runAutomationDE_ResponseQueryMulDoc",projectJson.toUpperCase());
		}


		applicationContext.getAutowireCapableBeanFactory().autowireBean(automationThreadService);
		workerThreadPool.submit(automationThreadService);
	}

	//------------------------ DE_RESPONSE_QUERY - FUNCTION -------------------------------------
	public Map<String, Object> DE_ResponseQuery(JsonNode request) throws Exception {
		JsonNode body = request.path("body");
		System.out.println(request);
		Assert.notNull(request.get("body"), "no body");
		DEResponseQueryDTO deResponseQueryDTOList = mapper.treeToValue(request.path("body"), DEResponseQueryDTO.class);

		new Thread(() -> {
			try {
				runAutomationDE_ResponseQuery(deResponseQueryDTOList);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}).start();

		return response(0, body, deResponseQueryDTOList);
	}

	private void runAutomationDE_ResponseQuery(DEResponseQueryDTO deResponseQueryDTOList) throws Exception {
		String browser = "chrome";
		String projectJson = deResponseQueryDTOList.getProject();
		Map<String, Object> mapValue = DataInitial.getDataFromDE_ResponseQuery(deResponseQueryDTOList);
		AutomationThreadService automationThreadService = null;
        if("smartnet".equals(projectJson)) {
            automationThreadService= new AutomationThreadService(loginDTOQueue, browser, mapValue,"runAutomationDE_ResponseQuery","RETURN");
        }else{
            automationThreadService= new AutomationThreadService(loginDTOQueue, browser, mapValue,"runAutomationDE_ResponseQuery",projectJson.toUpperCase());
        }


        applicationContext.getAutowireCapableBeanFactory().autowireBean(automationThreadService);
		workerThreadPool.submit(automationThreadService);
	}
	//------------------------ END - DE_RESPONSE_QUERY - FUNCTION -------------------------------------

	//------------------------ DE_SALE_QUEUE - FUNCTION -------------------------------------
	public Map<String, Object> DE_SaleQueue(JsonNode request) throws Exception {
		JsonNode body = request.path("body");
		System.out.println(request);
		Assert.notNull(request.get("body"), "no body");
		DESaleQueueDTO deSaleQueueDTOList = mapper.treeToValue(request.path("body"), DESaleQueueDTO.class);

		new Thread(() -> {
			try {
				runAutomationDE_SaleQueue(deSaleQueueDTOList);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}).start();

		return response(0, body, deSaleQueueDTOList);
	}

	private void runAutomationDE_SaleQueue(DESaleQueueDTO deSaleQueueDTOList) throws Exception {
		String browser = "chrome";
		String projectJson = deSaleQueueDTOList.getProject();
		Map<String, Object> mapValue = DataInitial.getDataFromDE_SaleQueue(deSaleQueueDTOList);
		AutomationThreadService automationThreadService = null;
        if(projectJson.equals("smartnet")) {
            automationThreadService= new AutomationThreadService(loginDTOQueue, browser, mapValue,"runAutomationDE_SaleQueue","RETURN");
        }else{
            automationThreadService= new AutomationThreadService(loginDTOQueue, browser, mapValue,"runAutomationDE_SaleQueue",projectJson.toUpperCase());
        }

		applicationContext.getAutowireCapableBeanFactory().autowireBean(automationThreadService);
		workerThreadPool.submit(automationThreadService);
	}
	//------------------------ END - DE_SALE_QUEUE - FUNCTION -------------------------------------


	//------------------------ SMARTNET - FUNCTION -----------------------------------------
	public Map<String, Object> SN_quickLeadApp(JsonNode request) throws Exception {
		JsonNode body = request.path("body");

		System.out.println(request);
		Assert.notNull(request.get("body"), "no body");
		Application application = mapper.treeToValue(request.path("body"), Application.class);

		new Thread(() -> {
			try {
				SN_runAutomation_QL(application);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}).start();

		return response(0, body, application.getQuickLead());
	}

	private void SN_runAutomation_QL(Application application) throws Exception {
		String browser = "chrome";
		Map<String, Object> mapValue = DataInitial.getDataFromDE_QL(application);

		AutomationThreadService automationThreadService= new AutomationThreadService(loginDTOQueue, browser, mapValue,"SN_quickLead","RETURN");

		applicationContext.getAutowireCapableBeanFactory().autowireBean(automationThreadService);
		workerThreadPool.submit(automationThreadService);

		//awaitTerminationAfterShutdown(workerThreadPool);
	}

	//------------------------ END - SMARTNET - FUNCTION -------------------------------------


	//------------------------ MOBILITY - FUNCTION -----------------------------------------

	//------------------------ QUICKLEAD  -------------------------------------
	public Map<String, Object> MOBILITY_quickLeadApp(JsonNode request) throws Exception {
		JsonNode body = request.path("body");

		System.out.println(request);
		Assert.notNull(request.get("body"), "no body");
		Application application = mapper.treeToValue(request.path("body"), Application.class);

		new Thread(() -> {
			try {
				MOBILITY_runAutomation_QL(application);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}).start();

		return response(0, body, application.getQuickLead());
	}

	private void MOBILITY_runAutomation_QL(Application application) throws Exception {
		String browser = "chrome";
		Map<String, Object> mapValue = DataInitial.getDataFromDE_QL(application);

		AutomationThreadService automationThreadService= new AutomationThreadService(loginDTOQueue, browser, mapValue,"MOBILITY_quickLead","MOBILITY");
		applicationContext.getAutowireCapableBeanFactory().autowireBean(automationThreadService);
		workerThreadPool.submit(automationThreadService);

		//awaitTerminationAfterShutdown(workerThreadPool);
	}
	//------------------------ END - QUICKLEAD  -------------------------------------

	//------------------------ WAIVE_FIELD  --------------------------------------
	public Map<String, Object> Waive_Field(JsonNode request) throws Exception {
		JsonNode body = request.path("body");
		Assert.notNull(request.get("body"), "no body");
		RequestAutomationDTO requestWaiveField = mapper.convertValue(request.path("body"), new TypeReference<RequestAutomationDTO>(){});
		List<WaiveFieldDTO> waiveFieldDTOList = mapper.convertValue(request.path("body").path("data"), new TypeReference<List<WaiveFieldDTO>>(){});
		requestWaiveField.setWaiveFieldDTO(waiveFieldDTOList);

		new Thread(() -> {
			try {
				runAutomation_Waive_Field(requestWaiveField);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}).start();

		return response(0, body, requestWaiveField);
	}

	private void runAutomation_Waive_Field(RequestAutomationDTO waiveFieldDTOList) throws Exception {
		String browser = "chrome";
		String projectAuto = "WAIVEFIELD";
		Map<String, Object> mapValue = DataInitial.getDataFrom_Waive_Field(waiveFieldDTOList);

		AutomationThreadService automationThreadService= new AutomationThreadService(loginDTOQueue, browser, mapValue,"runAutomation_Waive_Field", projectAuto);
		applicationContext.getAutowireCapableBeanFactory().autowireBean(automationThreadService);
		workerThreadPool.submit(automationThreadService);
	}

	//------------------------ END - WAIVE_FIELD  ----------------------------------

	//------------------------ SUBMIT_FIELD  -------------------------------------
	public Map<String, Object> Submit_Field(JsonNode request) throws Exception {
		JsonNode body = request.path("body");
//		String referenceId = request.path("reference_id").toString();
		Assert.notNull(request.get("body"), "no body");
		RequestAutomationDTO requestSubmitField = mapper.convertValue(request.path("body"), new TypeReference<RequestAutomationDTO>(){});
		List<SubmitFieldDTO> submitFieldDTOList = mapper.convertValue(request.path("body").path("data"), new TypeReference<List<SubmitFieldDTO>>(){});
//		requestSubmitField.setReference_id(referenceId);
		requestSubmitField.setSubmitFieldDTO(submitFieldDTOList);

		new Thread(() -> {
			try {
				runAutomation_Submit_Field(requestSubmitField);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}).start();

		return response(0, body, requestSubmitField);
	}

	private void runAutomation_Submit_Field(RequestAutomationDTO submitFieldDTOList) throws Exception {
		String browser = "chrome";
		String projectAuto = "SUBMITFIELD";
		Map<String, Object> mapValue = DataInitial.getDataFrom_Submit_Field(submitFieldDTOList);

		AutomationThreadService automationThreadService= new AutomationThreadService(loginDTOQueue, browser, mapValue,"runAutomation_Submit_Field", projectAuto);
		applicationContext.getAutowireCapableBeanFactory().autowireBean(automationThreadService);
		workerThreadPool.submit(automationThreadService);
	}

	//------------------------ END - SUBMIT_FIELD  -------------------------------

	//------------------------ END - MOBILITY - FUNCTION -----------------------------------------

	//------------------------ PROJECT CRM  -------------------------------------

	//------------------------ QUICKLEAD -------------------------------------
	public Map<String, Object> CRM_quickLeadApp(JsonNode request) throws Exception {
		JsonNode body = request.path("body");

		System.out.println(request);
		Assert.notNull(request.get("body"), "no body");
		String project = request.path("body").path("project").textValue();
		CRM_ExistingCustomerDTO application = mapper.treeToValue(request.path("body"), CRM_ExistingCustomerDTO.class);

		new Thread(() -> {
			try {
				CRM_runAutomation_QL(application, project);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}).start();

		return response(0, body, application.getQuickLead());
	}

	private void CRM_runAutomation_QL(CRM_ExistingCustomerDTO application, String project) throws Exception {
		String browser = "chrome";
		Map<String, Object> mapValue = DataInitial.getDataFromCRM_QL(application);

		AutomationThreadService automationThreadService= new AutomationThreadService(loginDTOQueue, browser, mapValue,"CRM_quickLead", project.toUpperCase());
		applicationContext.getAutowireCapableBeanFactory().autowireBean(automationThreadService);
		workerThreadPool.submit(automationThreadService);
	}

	public Map<String, Object> CRM_quickLeadAppWithCustID(JsonNode request) throws Exception {
		JsonNode body = request.path("body");
		System.out.println(request);
		Assert.notNull(request.get("body"), "no body");
		CRM_ExistingCustomerDTO existingCustomerDTOList = mapper.treeToValue(request.path("body"), CRM_ExistingCustomerDTO.class);

		new Thread(() -> {
			try {
				CRM_runAutomation_QLWithCustID(existingCustomerDTOList);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}).start();

		return response(0, body, existingCustomerDTOList);
	}

	private void CRM_runAutomation_QLWithCustID(CRM_ExistingCustomerDTO existingCustomerDTOList) throws Exception {
		String browser = "chrome";
		String projectJson = existingCustomerDTOList.getProject();
		Map<String, Object> mapValue = DataInitial.getDataFromCRM_QLWithCustID(existingCustomerDTOList);
		AutomationThreadService automationThreadService = new AutomationThreadService(loginDTOQueue, browser, mapValue,"CRM_quickLead_With_CustID",projectJson);

		applicationContext.getAutowireCapableBeanFactory().autowireBean(automationThreadService);
		workerThreadPool.submit(automationThreadService);
	}
	//------------------------ END - QUICKLEAD

	//------------------------ EXISTING_CUSTOMER -------------------------------------
	public Map<String, Object> Existing_Customer(JsonNode request) throws Exception {
		JsonNode body = request.path("body");
		System.out.println(request);
		Assert.notNull(request.get("body"), "no body");
		CRM_ExistingCustomerDTO existingCustomerDTOList = mapper.treeToValue(request.path("body"), CRM_ExistingCustomerDTO.class);
		String applicationId = request.path("body").path("appId").textValue();
		if (applicationId != null && !StringUtils.isEmpty(applicationId)) {
			existingCustomerDTOList.setApplicationId(applicationId);
		}

		new Thread(() -> {
			try {
				runAutomation_Existing_Customer(existingCustomerDTOList);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}).start();

		return response(0, body, existingCustomerDTOList);
	}

	private void runAutomation_Existing_Customer(CRM_ExistingCustomerDTO existingCustomerDTOList) throws Exception {
		String browser = "chrome";
		String projectJson = existingCustomerDTOList.getProject();
		Map<String, Object> mapValue = DataInitial.getDataFrom_Existing_Customer(existingCustomerDTOList);
		AutomationThreadService automationThreadService = new AutomationThreadService(loginDTOQueue, browser, mapValue,"runAutomation_Existing_Customer",projectJson.toUpperCase());

		applicationContext.getAutowireCapableBeanFactory().autowireBean(automationThreadService);
		workerThreadPool.submit(automationThreadService);
	}

	//------------------------ END - EXISTING_CUSTOMER -------------------------------------


	//------------------------ SALE_QUEUE -------------------------------------
	public Map<String, Object> Sale_Queue_With_FullInfo(JsonNode request) throws Exception {
		JsonNode body = request.path("body");
		System.out.println(request);
		Assert.notNull(request.get("body"), "no body");
		CRM_SaleQueueDTO saleQueueDTOList = mapper.treeToValue(request.path("body"), CRM_SaleQueueDTO.class);
		String applicationId = request.path("body").path("appId").textValue();
		if (applicationId != null && !StringUtils.isEmpty(applicationId)) {
			saleQueueDTOList.setApplicationId(applicationId);
		}

		new Thread(() -> {
			try {
				runAutomation_Sale_Queue_With_FullInfo(saleQueueDTOList);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}).start();

		return response(0, body, saleQueueDTOList);
	}

	private void runAutomation_Sale_Queue_With_FullInfo(CRM_SaleQueueDTO saleQueueDTOList) throws Exception {
		String browser = "chrome";
		String projectJson = saleQueueDTOList.getProject();
		Map<String, Object> mapValue = DataInitial.getDataFrom_Sale_Queue_FullInfo(saleQueueDTOList);
		AutomationThreadService automationThreadService = new AutomationThreadService(loginDTOQueue, browser, mapValue,"runAutomation_Sale_Queue_With_FullInfo",projectJson.toUpperCase());

		applicationContext.getAutowireCapableBeanFactory().autowireBean(automationThreadService);
		workerThreadPool.submit(automationThreadService);
	}
	//------------------------ END - SALE_QUEUE -------------------------------------


	//------------------------ END - PROJECT CRM  -------------------------------------


	//------------------------ PROJECT CRM  -------------------------------------

	//------------------------ QUICK LEAD WITH ASSIGN POOL -------------------------------------------
	public Map<String, Object> quickLeadAppAssignPool(JsonNode request) throws Exception {
		JsonNode body = request.path("body");

		System.out.println(request);
		Assert.notNull(request.get("body"), "no body");
		Application application = mapper.treeToValue(request.path("body"), Application.class);

		new Thread(() -> {
			try {
				runAutomation_QLAssignPool(application);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}).start();

		return response(0, body, application.getQuickLead());
	}

	private void runAutomation_QLAssignPool(Application application) throws Exception {
		String browser = "chrome";
		Map<String, Object> mapValue = DataInitial.getDataFromDE_QL(application);

		AutomationThreadService automationThreadService= new AutomationThreadService(loginDTOQueue, browser, mapValue,"quickLead_Assign_Pool","DATAENTRY");
		applicationContext.getAutowireCapableBeanFactory().autowireBean(automationThreadService);
		workerThreadPool.submit(automationThreadService);
	}

	//------------------------ END - QUICK LEAD WITH ASSIGN POOL -------------------------------------

	//------------------------ END - PROJECT CRM  -------------------------------


	//------------------------ AUTO ALLOCATION  -------------------------------
	public Map<String, Object> AutoAssign_Allocation(JsonNode request) throws Exception {
		JsonNode body = request.path("body");
		System.out.println(request);
		Assert.notNull(request.get("body"), "no body");
		AutoAllocationDTO autoAssignAllocationDTOList = mapper.treeToValue(request.path("body"), AutoAllocationDTO.class);

		new Thread(() -> {
			try {
				runAutomation_AutoAssign_Allocation(autoAssignAllocationDTOList);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}).start();

		return response(0, body, autoAssignAllocationDTOList);
	}

	private void runAutomation_AutoAssign_Allocation(AutoAllocationDTO autoAssignAllocationDTOList) throws Exception {
		String browser = "chrome";
		String projectJson = autoAssignAllocationDTOList.getProject();
		Map<String, Object> mapValue = DataInitial.getDataFrom_AutoAssign_Allocation(autoAssignAllocationDTOList);

		AutomationThreadService automationThreadService= new AutomationThreadService(loginDTOQueue, browser, mapValue,"runAutomation_AutoAssign_Allocation",projectJson.toUpperCase());
		applicationContext.getAutowireCapableBeanFactory().autowireBean(automationThreadService);
		workerThreadPool.submit(automationThreadService);
	}

	//------------------------ QUICKLEAD  -------------------------------------
	public Map<String, Object> MOBILITY_quickLeadApp_Vendor(JsonNode request) throws Exception {
		JsonNode body = request.path("body");

		System.out.println(request);
		Assert.notNull(request.get("body"), "no body");
		Application application = mapper.treeToValue(request.path("body"), Application.class);

		new Thread(() -> {
			try {
				MOBILITY_runAutomation_QL_Vendor(application);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}).start();

		return response(0, body, application.getQuickLead());
	}

	private void MOBILITY_runAutomation_QL_Vendor(Application application) throws Exception {
		String browser = "chrome";
		Map<String, Object> mapValue = DataInitial.getDataFromDE_QL(application);

		AutomationThreadService automationThreadService= new AutomationThreadService(loginDTOQueue, browser, mapValue,"MOBILITY_quickLead_Vendor","MOBILITY");
		applicationContext.getAutowireCapableBeanFactory().autowireBean(automationThreadService);
		workerThreadPool.submit(automationThreadService);

		//awaitTerminationAfterShutdown(workerThreadPool);
	}
	//------------------------ END - QUICKLEAD  -------------------------------------

	//region FUNCTION QUICKLEAD
	//region PROJECT LEADGATEWAY
	public Map<String, Object> QuickLeadApplication(JsonNode request) throws Exception {
		JsonNode body = request.path("body");
		Assert.notNull(request.get("body"), "no body");
		QuickLeadDTO quickLeadDTOList = mapper.treeToValue(request.path("body"), QuickLeadDTO.class);

		new Thread(() -> {
			try {
				runAutomation_QuickLeadApplication(quickLeadDTOList);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}).start();

		return response(0, body, quickLeadDTOList.getQuickLead());
	}

	private void runAutomation_QuickLeadApplication(QuickLeadDTO quickLeadDTOList) throws Exception {
		String browser = "chrome";
		Map<String, Object> mapValue = DataInitial.getDataFromQuickLead(quickLeadDTOList);

		QuickLeadDTO monitorQuickLeadDTO = (QuickLeadDTO) mapValue.get("QuickLeadDTOList");
		AutomationMonitorDTO autoMonitor = AutomationMonitorDTO.builder().build();
		autoMonitor.setIdentificationNumber(monitorQuickLeadDTO.getQuickLead().getIdentificationNumber());
		autoMonitor.setReferentId(monitorQuickLeadDTO.getReference_id());
		autoMonitor.setQuickLeadId(monitorQuickLeadDTO.getQuickLeadId());
		autoMonitor.setFuncAutomation("quickLeadApplication");
		autoMonitor.setStatus("PROCESSING");
		autoMonitor.setProject(monitorQuickLeadDTO.getProject());
		mongoTemplate.insert(autoMonitor);

		AutomationThreadService automationThreadService= new AutomationThreadService(loginDTOQueue, browser, mapValue,"runAutomation_quickLeadApplication", monitorQuickLeadDTO.getProject().toUpperCase());
		applicationContext.getAutowireCapableBeanFactory().autowireBean(automationThreadService);
		workerThreadPool.submit(automationThreadService);
	}
	//endregion

	//region PROJECT LEADGATEWAY
	public Map<String, Object> QuickLeadApplicationVendor(JsonNode request) throws Exception {
		JsonNode body = request.path("body");
		Assert.notNull(request.get("body"), "no body");
		QuickLeadDTO quickLeadDTOList = mapper.treeToValue(request.path("body"), QuickLeadDTO.class);

		new Thread(() -> {
			try {
				runAutomation_QuickLeadApplicationVendor(quickLeadDTOList);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}).start();

		return response(0, body, quickLeadDTOList.getQuickLead());
	}

	private void runAutomation_QuickLeadApplicationVendor(QuickLeadDTO quickLeadDTOList) throws Exception {
		String browser = "chrome";
		Map<String, Object> mapValue = DataInitial.getDataFromQuickLeadVendor(quickLeadDTOList);

		QuickLeadDTO monitorQuickLeadVendorDTO = (QuickLeadDTO) mapValue.get("QuickLeadDTOList");
		AutomationMonitorDTO autoMonitor = AutomationMonitorDTO.builder().build();
		autoMonitor.setIdentificationNumber(monitorQuickLeadVendorDTO.getQuickLead().getIdentificationNumber());
		autoMonitor.setReferentId(monitorQuickLeadVendorDTO.getReference_id());
		autoMonitor.setQuickLeadId(monitorQuickLeadVendorDTO.getQuickLeadId());
		autoMonitor.setFuncAutomation("quickLeadApplicationVendor");
		autoMonitor.setStatus("PROCESSING");
		autoMonitor.setProject(monitorQuickLeadVendorDTO.getProject());
		mongoTemplate.insert(autoMonitor);

		AutomationThreadService automationThreadService= new AutomationThreadService(loginDTOQueue, browser, mapValue,"runAutomation_quickLeadApplicationVendor", monitorQuickLeadVendorDTO.getProject().toUpperCase());
		applicationContext.getAutowireCapableBeanFactory().autowireBean(automationThreadService);
		workerThreadPool.submit(automationThreadService);
	}
	//endregion

	//endregion

	//region FUNCTION RESPONSE QUERY

	public Map<String, Object> ResponseQuery(JsonNode request) throws Exception {
		JsonNode body = request.path("body");
		System.out.println(request);
		Assert.notNull(request.get("body"), "no body");
		ResponseQueryDetails responseQueryDTOList = mapper.treeToValue(request.path("body"), ResponseQueryDetails.class);

		new Thread(() -> {
			try {
				runAutomation_ResponseQuery(responseQueryDTOList);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}).start();

		return response(0, body, responseQueryDTOList);
	}

	private void runAutomation_ResponseQuery(ResponseQueryDetails responseQueryDTOList) throws Exception {
		String browser = "chrome";
		String projectJson = responseQueryDTOList.getProject();
		Map<String, Object> mapValue = DataInitial.getDataFrom_ResponseQuery(responseQueryDTOList);
		AutomationThreadService automationThreadService = new AutomationThreadService(loginDTOQueue, browser, mapValue,"runAutomation_ResponseQuery", projectJson.toUpperCase());

		ResponseQueryDTO monitorResponseQueryDTO = (ResponseQueryDTO) mapValue.get("ResponseQueryList");
		AutomationMonitorDTO autoMonitor = AutomationMonitorDTO.builder().build();
		autoMonitor.setApplicationId(monitorResponseQueryDTO.getApplicationId());
		autoMonitor.setReferentId(monitorResponseQueryDTO.getReferenceId());
		autoMonitor.setTransactionId(monitorResponseQueryDTO.getTransactionId());
		autoMonitor.setFuncAutomation("responseQuery");
		autoMonitor.setStatus("PROCESSING");
		autoMonitor.setProject(monitorResponseQueryDTO.getProject());
		mongoTemplate.insert(autoMonitor);

		applicationContext.getAutowireCapableBeanFactory().autowireBean(automationThreadService);
		workerThreadPool.submit(automationThreadService);
	}

	//endregion

	//region FUNCTION SALEQUEUE
	public Map<String, Object> SaleQueue(JsonNode request) throws Exception {
		JsonNode body = request.path("body");
		System.out.println(request);
		Assert.notNull(request.get("body"), "no body");
		SaleQueueDetails saleQueueDetailsList = mapper.treeToValue(request.path("body"), SaleQueueDetails.class);

		new Thread(() -> {
			try {
				runAutomation_SaleQueue(saleQueueDetailsList);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}).start();

		return response(0, body, saleQueueDetailsList);
	}

	private void runAutomation_SaleQueue(SaleQueueDetails saleQueueDetailsList) throws Exception {
		String browser = "chrome";
		String projectJson = saleQueueDetailsList.getProject();
		Map<String, Object> mapValue = DataInitial.getData_SaleQueue(saleQueueDetailsList);

		SaleQueueDTO monitorSaleQueueDTO = (SaleQueueDTO) mapValue.get("SaleQueueList");
		AutomationMonitorDTO autoMonitor = AutomationMonitorDTO.builder().build();
		autoMonitor.setApplicationId(monitorSaleQueueDTO.getApplicationId());
		autoMonitor.setReferentId(monitorSaleQueueDTO.getReferenceId());
		autoMonitor.setTransactionId(monitorSaleQueueDTO.getTransactionId());
		autoMonitor.setFuncAutomation("saleQueue");
		autoMonitor.setStatus("PROCESSING");
		autoMonitor.setProject(monitorSaleQueueDTO.getProject());
		mongoTemplate.insert(autoMonitor);

		AutomationThreadService automationThreadService = new AutomationThreadService(loginDTOQueue, browser, mapValue,"runAutomation_SaleQueue", projectJson.toUpperCase());

		applicationContext.getAutowireCapableBeanFactory().autowireBean(automationThreadService);
		workerThreadPool.submit(automationThreadService);
	}
	//endregion

	//region FUNCTION WAIVEFIELD
	public Map<String, Object> WaiveField(JsonNode request) throws Exception {
		JsonNode body = request.path("body");
		Assert.notNull(request.get("body"), "no body");
		MFieldRequest requestWaiveField = mapper.convertValue(request.path("body"), new TypeReference<MFieldRequest>(){});
		List<WaiveFieldDetails> waiveFieldDTOList = mapper.convertValue(request.path("body").path("data"), new TypeReference<List<WaiveFieldDetails>>(){});
		requestWaiveField.setWaiveFieldDTO(waiveFieldDTOList);

		new Thread(() -> {
			try {
				runAutomation_WaiveField(requestWaiveField);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}).start();

		return response(0, body, requestWaiveField);
	}

	private void runAutomation_WaiveField(MFieldRequest requestWaiveField) throws Exception {
		String browser = "chrome";
		String projectAuto = "WAIVEFIELD";
		Map<String, Object> mapValue = DataInitial.getDataFrom_WaiveField(requestWaiveField);

		AutomationThreadService automationThreadService= new AutomationThreadService(loginDTOQueue, browser, mapValue,"runAutomation_Waive_Field", projectAuto);
		applicationContext.getAutowireCapableBeanFactory().autowireBean(automationThreadService);
		workerThreadPool.submit(automationThreadService);
	}
	//endregion
//region simple product
	public Map<String, Object> returnSimpleProduct(JsonNode request) throws Exception {
		JsonNode body = request.path("body");

		System.out.println(request);

		Assert.notNull(request.get("body"), "no body");
		Application application = mapper.treeToValue(request.path("body"), Application.class);

		new Thread(() -> {
			try {
				runAutomation_return(application);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}).start();

		return response(0, body, application);
	}

	private void runAutomation_return(Application application) throws Exception {
		String browser = "chrome";
		Map<String, Object> mapValue = DataInitial.getDataFromDE(application);

		mapValue.put("func", "returnSimpleProduct");
		AutomationThreadService automationThreadService= new AutomationThreadService(loginDTOQueue, browser, mapValue,"runAutomation_return","SIMPLE_PRODUCT");
		applicationContext.getAutowireCapableBeanFactory().autowireBean(automationThreadService);
		workerThreadPool.submit(automationThreadService);
	}

	//endregion








}