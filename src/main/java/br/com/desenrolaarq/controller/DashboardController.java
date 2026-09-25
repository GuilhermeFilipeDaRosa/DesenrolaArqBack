package br.com.desenrolaarq.controller;

import br.com.desenrolaarq.dto.DashboardDTO;
import br.com.desenrolaarq.service.DashboardService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/dashboard")
public class DashboardController {

    private final DashboardService dashboardService;

    public DashboardController(DashboardService dashboardService) {
        this.dashboardService = dashboardService;
    }

    @GetMapping
    public DashboardDTO buscarDashboard() {
        return dashboardService.buscarDashboard();
    }
}