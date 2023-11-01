package com.usuariomicroservicio.controller;

import com.usuariomicroservicio.service.UsuarioCuentaService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("UsuarioCuentaController")
@RequestMapping("/medioDePago")
public class UsuarioCuentaController {
    private UsuarioCuentaService usuarioCuentaService;

    @GetMapping("/saldo={usuario_id}")
    public void getSaldo(Long id){
        usuarioCuentaService.getSaldo(id);
    }
}
