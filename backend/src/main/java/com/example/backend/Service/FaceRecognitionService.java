package com.example.backend.Service;

import com.example.backend.DTO.FaceVerifyResult;
import com.example.backend.Entity.FaceVerificationLog;
import com.example.backend.Entity.User;
import com.example.backend.Repo.FaceVerificationLogRepo;
import com.example.backend.Repo.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.Instant;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FaceRecognitionService {
    private final WebClient pythonFaceWebClient;
    private final UserRepo userRepo;
    private final FaceVerificationLogRepo logRepo;

    //ENROLL
    public void enrollFace(UUID userId,String image){
        if(image==null || image.isBlank()){
            throw new IllegalArgumentException("Image is required");
        }
        User user=userRepo.findById(userId).orElseThrow(()->new RuntimeException("User not found"));
        Map<String, Object>  req=Map.of("user_id",userId.toString(),
                "image",image);
try {
    pythonFaceWebClient.post().uri("/enroll").bodyValue(req).retrieve().bodyToMono(Void.class).block();

    user.setFaceEnrolled(true);
    user.setFaceEnrolledAt(Instant.now());
    userRepo.save(user);
    }
catch (Exception ex){
    throw new RuntimeException("Face enrollment failed: "+ ex.getMessage());

}
}

    //VERIFY
    public FaceVerifyResult verifyFace(UUID userId,String image,String ip){
        if(image==null || image.isBlank()){
            throw new IllegalArgumentException("Image is required");
        }
        User user=userRepo.findById(userId).orElseThrow(()->new RuntimeException("User not found"));
        Map<String, Object> req = Map.of(
                "user_id", userId.toString(),
                "image", image
        );
        Map<String,Object> response;
       try {
           response = pythonFaceWebClient.post().uri("/verify").bodyValue(req).retrieve()
                   .bodyToMono(Map.class).block();
       }
       catch (Exception ex){
           throw new RuntimeException("Face verification service unavailable ");
       }
       if(response==null || !response.containsKey("verified")){
           throw new RuntimeException("Invalid response from Python service");
       }
           Boolean verified=(Boolean)response.get("verified");
           Float similarity=response.get("similarity")!=null? ((Number)response.get("similarity")).floatValue():0.0f;

        FaceVerificationLog log=new FaceVerificationLog();
        log.setUser(user);
        log.setVerified(verified);
        log.setSimilarityScore(similarity);
        log.setIpAddress(ip);
        logRepo.save(log);

        FaceVerifyResult faceVerifyResult=new FaceVerifyResult();
        faceVerifyResult.setVerified( verified);
        faceVerifyResult.setSimilarity(similarity);
        faceVerifyResult.setMessage(verified?"Verified":"Failed");
        return  faceVerifyResult;
    }

    //IDENTIFY
    public FaceVerifyResult identify(String image){
        if (image == null || image.isBlank()) {
            throw new IllegalArgumentException("Image is required");
        }
        Map<String,Object> req=Map.of("image",image);
        Map<String,Object> response;
        try {
            response = pythonFaceWebClient.post().uri("/identify").bodyValue(req).retrieve().bodyToMono(Map.class).block();
        }
        catch (Exception ex){
            throw new RuntimeException("Face identification service unavailable");
        }
        if (response == null || !response.containsKey("user_id")) {
            throw new RuntimeException("Invalid identify response from Python service");
        }
        FaceVerifyResult faceVerifyResult = new FaceVerifyResult();
        faceVerifyResult.setSimilarity(response.get("similarity") != null
                ? ((Number) response.get("similarity")).floatValue()
                : 0.0f);
        String userId=(String) response.get("user_id");
        if("unknown".equalsIgnoreCase(userId)){
            faceVerifyResult.setVerified(false);
            faceVerifyResult.setMessage("No match found");
        }
        else{
            faceVerifyResult.setVerified(true);
            faceVerifyResult.setMatched_user_id(UUID.fromString(userId));
            faceVerifyResult.setMessage("User identified");
        }
        return  faceVerifyResult;
    }


}
