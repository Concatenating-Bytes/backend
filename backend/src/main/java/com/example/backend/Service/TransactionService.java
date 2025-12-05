package com.example.backend.Service;

import com.example.backend.DTO.*;
import com.example.backend.Entity.Transactions;
import com.example.backend.Entity.User;
import com.example.backend.Repo.TransactionRepo;
import com.example.backend.Repo.UserRepo;
import com.example.backend.enums.TransactionStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TransactionService {

    private final TransactionRepo transactionRepo;
    private final UserRepo userRepo;
    private final FaceRecognitionService faceRecognitionService;

    //getting all transactions
    public List<Transactions> getAllTransactions(){
        return transactionRepo.findAll();
    }

    //creating pending transaction
    @Transactional
    public TransactionInitiationResponse initiate(TransactionInitiationRequest transactionInitiationRequest){
        User sender=userRepo.findById(transactionInitiationRequest.getUser_id()).orElseThrow(()->new RuntimeException("User not found"));
        Transactions transactions=new Transactions();
        transactions.setAmount(transactionInitiationRequest.getAmount());
        transactions.setSender(sender);

        //DUMMY DATA
        transactions.setReceiver(sender);
        transactions.setStatus(TransactionStatus.PENDING);
        transactionRepo.save(transactions);
        TransactionInitiationResponse transactionInitiationResponse=new TransactionInitiationResponse();
        transactionInitiationResponse.setTransaction_id(transactions.getId());
        transactionInitiationResponse.setStatus("PENDING");
        return transactionInitiationResponse;
    }

    //verification & Approve/Rejection using face info
    @Transactional
    public TransactionVerifyFaceResponse verifyTransaction(TransactionVerifyFaceRequest transactionVerifyFaceRequest,String ip){
        Transactions transactions=transactionRepo.findById(transactionVerifyFaceRequest.getTransaction_id()).orElseThrow(()->new RuntimeException("Transaction not found"));


        //ownership check
        if(!transactions.getId().equals(transactionVerifyFaceRequest.getUser_id())){
            throw new RuntimeException("user does not own this transaction");
        }

        //state check
        if(transactions.getStatus()!=TransactionStatus.PENDING){
            throw new RuntimeException("Transaction already processed");
        }

        //Calling face verification
        FaceVerifyResult faceVerifyResult=faceRecognitionService.verifyFace(transactionVerifyFaceRequest.getUser_id(),transactionVerifyFaceRequest.getImage(),ip);
        TransactionVerifyFaceResponse transactionVerifyFaceResponse=new TransactionVerifyFaceResponse();
        transactionVerifyFaceResponse.setSimilarity(faceVerifyResult.getSimilarity());

        if(faceVerifyResult.isVerified()){
            transactions.setStatus(TransactionStatus.APPROVED);
            transactions.setApprovedAt(Instant.now());
            transactions.setFailureReason(null);
            transactionVerifyFaceResponse.setStatus("APPROVED");
            transactionVerifyFaceResponse.setMessage("Transaction approved by face verification");

        }
        else{
            transactions.setStatus(TransactionStatus.REJECTED);
            transactions.setFailureReason("Face mismatch");
            transactionVerifyFaceResponse.setStatus("REJECTED");
            transactionVerifyFaceResponse.setMessage("Transaction rejected due to face mismatch");


        }
        transactionRepo.save(transactions);
        return  transactionVerifyFaceResponse;
    }
}
