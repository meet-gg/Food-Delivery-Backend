//package com.Online.Food.Delivery.System.Online.Food.Controller;
//
//import com.Online.Food.Delivery.System.Online.Food.DTO.MenuDTO;
//import com.Online.Food.Delivery.System.Online.Food.Services.MenuServicesImpl;
//import lombok.RequiredArgsConstructor;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//@RestController
//@RequiredArgsConstructor
//@RequestMapping("menu")
//public class MenuController {
//
//    private static final Logger log = LoggerFactory.getLogger(MenuController.class);
//    private final MenuServicesImpl menuServices;
//
//    @PostMapping("/add")
//    public ResponseEntity<?> createMenu(@RequestBody MenuDTO menuDTO) {
//        log.info("controller {}",menuDTO);
//        ResponseEntity<?> menu = menuServices.createMenu(menuDTO);
//        return ResponseEntity.ok(menu);
//    }
//}
