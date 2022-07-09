package com.fabio.crm.controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.fabio.crm.data.entities.Customer;
import com.fabio.crm.data.entities.Quote;
import com.fabio.crm.data.repositories.CustomerRepository;
import com.fabio.crm.data.repositories.QuoteRepository;
import com.fabio.crm.services.CustomerService;
import com.fabio.crm.services.QuoteService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
@RequestMapping("/quote")
public class QuoteController {

    @Autowired
    CustomerRepository customerRepository;
    
    @Autowired
    private final QuoteRepository quoteRepository;

    @Autowired
    private final QuoteService quoteService;
    @Autowired
    private final CustomerService customerService;

    public QuoteController(QuoteRepository quoteRepository, QuoteService quoteService, CustomerService  customerService){
        this.quoteRepository = quoteRepository;
        this.quoteService = quoteService;
        this.customerService = customerService;
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String create(Model model, @RequestParam Long id)
    {
        
        System.out.println(id);
        model.addAttribute("quoteInstance", new Quote());

        //
        
        model.addAttribute("customerId", id);
        return "quote/create.html";
    }
    
    @PostMapping("/save")
    public String save(@Valid @ModelAttribute("quoteInstance") Quote quoteInstance,
                       BindingResult bindingResult,
                        @RequestParam(value = "customerId") String customerId,
                       Model model,
                       RedirectAttributes atts) {
        System.out.println("customerID: "+customerId);
        System.out.println(quoteInstance.getDescription()+", "+quoteInstance.getPrice());
        Customer customer = customerService.getCustomerById(Long.parseLong(customerId));
        System.out.println("customeridnew "+customer.getId());
        quoteInstance.setCustomer(customer);
        quoteRepository.save(quoteInstance);
        return "redirect:/customer/edit/"+customerId;
        
    }
    
    
    @RequestMapping(value = "/data_for_datatable/{customerID}", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public String getDataForDatatable(@RequestParam Map<String, Object> params,
                                        @PathVariable(value = "customerID") int customerId) {
        int draw = params.containsKey("draw") ? Integer.parseInt(params.get("draw").toString()) : 1;
        int length = params.containsKey("length") ? Integer.parseInt(params.get("length").toString()) : 30;
        int start = params.containsKey("start") ? Integer.parseInt(params.get("start").toString()) : 30;
        int currentPage = start / length;

        String sortName = "id";
        String dataTableOrderColumnIdx = params.get("order[0][column]").toString();
        String dataTableOrderColumnName = "columns[" + dataTableOrderColumnIdx + "][data]";
        if (params.containsKey(dataTableOrderColumnName))
            sortName = params.get(dataTableOrderColumnName).toString();
        String sortDir = params.containsKey("order[0][dir]") ? params.get("order[0][dir]").toString() : "asc";

        Sort.Order sortOrder = new Sort.Order((sortDir.equals("desc") ? Sort.Direction.DESC : Sort.Direction.ASC), sortName);
        Sort sort = Sort.by(sortOrder);

        Pageable pageRequest = PageRequest.of(currentPage,
                length,
                sort);

        String queryString = (String) (params.get("search[value]"));

        Page<Quote> quotes = quoteService.getQuotesForDatatable(queryString, pageRequest);

        long totalRecords = quotes.getTotalElements();

        List<Map<String, Object>> cells = new ArrayList<>();
        quotes.forEach(quote -> {
            if (quote.getCustomer().getId()==customerId){
                Map<String, Object> cellData = new HashMap<>();
                cellData.put("id", quote.getId());
                cellData.put("description", quote.getDescription());
                cellData.put("price", quote.getPrice());
                cells.add(cellData);
            }
        });

        Map<String, Object> jsonMap = new HashMap<>();

        jsonMap.put("draw", draw);
        jsonMap.put("recordsTotal", totalRecords);
        jsonMap.put("recordsFiltered", totalRecords);
        jsonMap.put("data", cells);

        String json = null;
        try {
            json = new ObjectMapper().writeValueAsString(jsonMap);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return json;
    }
}
