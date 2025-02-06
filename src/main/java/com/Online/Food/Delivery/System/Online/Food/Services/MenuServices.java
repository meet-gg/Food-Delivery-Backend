package com.Online.Food.Delivery.System.Online.Food.Services;

import com.Online.Food.Delivery.System.Online.Food.DTO.MenuDTO;
import com.Online.Food.Delivery.System.Online.Food.Entity.Menu;
import com.Online.Food.Delivery.System.Online.Food.Repository.MenuRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MenuServices {

    private static final Logger log = LoggerFactory.getLogger(MenuServices.class);
    private final MenuRepository menuRepository;
    private final ModelMapper modelMapper;

    public ResponseEntity<?> createMenu(MenuDTO menuDTO) {
        try {
            log.info("menu dto {}", menuDTO);
            Menu menu=modelMapper.map(menuDTO, Menu.class);
            Menu savemenu=menuRepository.save(menu);
            log.info("saved menu {}",savemenu);
            return new ResponseEntity<>(modelMapper.map(savemenu, MenuDTO.class), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getLocalizedMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
