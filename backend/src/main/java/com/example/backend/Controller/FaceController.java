package com.example.backend.Controller;

import com.example.backend.DTO.FaceEnrollRequest;
import com.example.backend.DTO.FaceIdentifyRequest;
import com.example.backend.DTO.FaceVerifyRequest;
import com.example.backend.DTO.FaceVerifyResult;
import com.example.backend.Service.FaceRecognitionService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/face")
@RequiredArgsConstructor
public class FaceController {
    private final FaceRecognitionService service;
    @PostMapping("/enroll")
    public  void enroll(@RequestBody FaceEnrollRequest faceEnrollRequest){
        service.enrollFace(faceEnrollRequest.getUser_id(),faceEnrollRequest.getImage());
    }
    @PostMapping("/verify")
    public FaceVerifyResult verify(@RequestBody FaceVerifyRequest request, HttpServletRequest req){
        return service.verifyFace(request.getUser_id(),request.getImage(),req.getRemoteAddr());

    }
    @PostMapping("/identify")
    public FaceVerifyResult identify(@RequestBody FaceIdentifyRequest req){
        return  service.identify(req.getImage());
    }

}
