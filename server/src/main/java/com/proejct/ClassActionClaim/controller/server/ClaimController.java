package com.proejct.ClassActionClaim.controller.server;

import com.proejct.ClassActionClaim.dto.RequestBody.ClaimRequestDTO;
import com.proejct.ClassActionClaim.dto.ResponseBody.ClaimResponseDTO;
import com.proejct.ClassActionClaim.dto.ResponseBody.ToClientResponse;
import com.proejct.ClassActionClaim.service.ClaimService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/claim")
@RequiredArgsConstructor
public class ClaimController {

    private final ClaimService claimService;

    @GetMapping
    public ToClientResponse<List<ClaimResponseDTO>> getClaims(@RequestBody ClaimRequestDTO claimRequestDTO) {
        return claimService.getClaims(claimRequestDTO);
    }

    @PostMapping("/save")
    public ToClientResponse<ClaimResponseDTO> saveClaims(@RequestBody ClaimRequestDTO claimRequestDTO) {
        return claimService.saveClaims(claimRequestDTO);
    }


}
