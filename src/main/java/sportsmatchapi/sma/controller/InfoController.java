package sportsmatchapi.sma.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import sportsmatchapi.sma.model.Info;
import sportsmatchapi.sma.repository.*;

import java.util.Optional;

@RestController
@RequestMapping("api/info")
public class InfoController {

    @Autowired
    InfoRepository infoRepository;

    @GetMapping("")
    public Optional<Info> info() {
        return infoRepository.findFirst();
    }

}
