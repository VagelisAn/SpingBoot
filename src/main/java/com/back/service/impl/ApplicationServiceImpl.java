package com.back.service.impl;

import com.back.service.ApplicationServices;
import org.springframework.stereotype.Service;

@Service
public class ApplicationServiceImpl implements ApplicationServices {

//
//    @Autowired
//    private RequestRepository requestRepository;
//
//    public Request existRequest(RequestDTO req){
//        Request requestRet = requestRepository.findByFirstNameAndLastName(req.getFirstName(),req.getLastName());
//        if(requestRet != null) {
//            requestRet.setFirstName(req.getFirstName());
//            requestRet.setLastName(req.getLastName());
//            requestRet.setYearStudy(req.getYearStudy());
//            requestRet.setYearHome(req.getYearHome());
//            requestRet.setNotJob(req.getNotJob());
//            requestRet.setDepartment(req.getDepartment());
//            requestRet.setBrothersSameCity(req.getBrothersSameCity());
//            requestRet.setBrothersAnotherCity(req.getBrothersAnotherCity());
//            requestRet.setIncome(req.getIncome());
//        }
//        return requestRet;
//    }
//
//    public Request save(Request req) {
//        req.setFinalPoint(calculatePoin(req));
//        req.setApprove(false);
//        return requestRepository.save(req);
//    }
//
//
//    public Request saveRequest(RequestDTO req) {
//        Request newReq= new Request();
//        newReq.setFirstName(req.getFirstName());
//        newReq.setLastName(req.getLastName());
//        newReq.setIncome(req.getIncome());
//        newReq.setDepartment(req.getDepartment());
//        newReq.setBrothersAnotherCity(req.getBrothersAnotherCity());
//        newReq.setBrothersSameCity(req.getBrothersSameCity());
//        newReq.setNotJob(req.getNotJob());
//        newReq.setYearHome(req.getYearHome());
//        newReq.setYearStudy(req.getYearStudy());
//        newReq.setFinalPoint(calculatePoints(req));
//        newReq.setApprove(false);
//        return requestRepository.save(newReq);
//    }
//
//    public List<Request> getRequest(){
//        return requestRepository.findAll();
//    }
//
//    public List<Request> getRequest(String departement){
//        return requestRepository.findByDepartment(departement);
//    }
//
//    public String activateRequest(RequestDTO req){
//        Request newReq = requestRepository.findById(req.getId());
//        newReq.setApprove(true);
//        requestRepository.save(newReq);
//        return "update";
//    }
//
//    public Request getActivate(Request req){
//        Request newReq = requestRepository.findById(req.getId());
//        newReq.setApprove(true);
//        return requestRepository.save(newReq);
//    }
//
//    public Request getDactivate(Request req){
//        Request newReq = requestRepository.findById(req.getId());
//        newReq.setApprove(false);
//        return requestRepository.save(newReq);
//    }
//
//    public static int calculatePoints(RequestDTO req){
//        int point = 0;
//        if(req.getIncome() <= 0 && req.getNotJob() == 0){
//            point = 1000000;
//        }
//        if(req.getYearStudy() >= 4){
//            point = -100000000;
//        }
//        if(req.getIncome() <= 10000){
//            point = 100;
//        }else if(req.getIncome() > 10000 && req.getIncome() <= 15000){
//            point = 50;
//        }else if(req.getIncome() >  15000){
//            point = 0;
//        }
//        point = point + req.getBrothersSameCity() * 20;
//        point = point + req.getBrothersAnotherCity() * 50;
//        point = point - req.getYearHome() * 10 ;
//
//        return point;
//    }
//
//    public static int calculatePoin(Request req){
//        int point = 0;
//        if(req.getIncome() <= 0 && req.getNotJob() == 0){
//            point = 1000000;
//        }
//        if(req.getYearStudy() >= 4){
//            point = -100000000;
//        }
//        if(req.getIncome() <= 10000){
//            point = 100;
//        }else if(req.getIncome() > 10000 && req.getIncome() <= 15000){
//            point = 50;
//        }else if(req.getIncome() >  15000){
//            point = 0;
//        }
//        point = point + req.getBrothersSameCity() * 20;
//        point = point + req.getBrothersAnotherCity() * 50;
//        point = point - req.getYearHome() * 10 ;
//
//        return point;
//    }
}
