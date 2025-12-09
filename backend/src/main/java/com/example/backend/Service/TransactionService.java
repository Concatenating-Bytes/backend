package com.example.backend.Service;

import com.example.backend.DTO.*;
import com.example.backend.Entity.Transactions;
import com.example.backend.Entity.User;
import com.example.backend.Entity.UserBankDetails;
import com.example.backend.Repo.BankDetailsRepo;
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
    private final BankDetailsRepo bankDetailsRepo;


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
    public TransactionVerifyFaceResponse verifyTransaction(
            TransactionVerifyFaceRequest request, String ip) {

        Transactions transactions = transactionRepo.findById(request.getTransaction_id())
                .orElseThrow(() -> new RuntimeException("Transaction not found"));

        // ✅ OWNERSHIP CHECK
        if (!transactions.getSender().getId().equals(request.getUser_id())) {
            throw new RuntimeException("User does not own this transaction");
        }

        // ✅ STATE CHECK
        if (transactions.getStatus() != TransactionStatus.PENDING) {
            throw new RuntimeException("Transaction already processed");
        }

        // ✅ FACE VERIFICATION
        FaceVerifyResult faceVerifyResult =
                faceRecognitionService.verifyFace(
                        request.getUser_id(),
                        request.getImage(),
                        ip
                );

        TransactionVerifyFaceResponse response = new TransactionVerifyFaceResponse();
        response.setSimilarity(faceVerifyResult.getSimilarity());

        // ✅ FETCH USER BANK ACCOUNT
        UserBankDetails bank =
                bankDetailsRepo.findByUserId(request.getUser_id())
                        .orElseThrow(() -> new RuntimeException("Bank account not found"));

        if (faceVerifyResult.isVerified()) {

            // ✅ SUFFICIENT BALANCE CHECK
            if (bank.getBalance() < transactions.getAmount()) {
                transactions.setStatus(TransactionStatus.REJECTED);
                transactions.setFailureReason("Insufficient balance");
                transactionRepo.save(transactions);

                response.setStatus("REJECTED");
                response.setMessage("Insufficient balance");
                return response;
            }

            // ✅ BALANCE DEDUCTION
            bank.setBalance(bank.getBalance() - transactions.getAmount());
            bankDetailsRepo.save(bank);

            // ✅ TRANSACTION APPROVAL
            transactions.setStatus(TransactionStatus.APPROVED);
            transactions.setApprovedAt(Instant.now());
            transactions.setFailureReason(null);

            response.setStatus("APPROVED");
            response.setMessage("Transaction approved by face verification");

        } else {
            transactions.setStatus(TransactionStatus.REJECTED);
            transactions.setFailureReason("Face mismatch");

            response.setStatus("REJECTED");
            response.setMessage("Transaction rejected due to face mismatch");
        }

        transactionRepo.save(transactions);
        return response;
    }

}
