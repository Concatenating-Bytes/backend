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
        User sender = userRepo.findById(transactionInitiationRequest.getUser_id())
                .orElseThrow(() -> new RuntimeException("Sender not found"));
        
        // Find receiver by account number
        UserBankDetails receiverAccount = bankDetailsRepo.findByAccountNumber(transactionInitiationRequest.getReceiver())
                .orElseThrow(() -> new RuntimeException("Receiver not found with account number: " + transactionInitiationRequest.getReceiver()));
        
        User receiver = receiverAccount.getUser();
        
        // Validate sender has bank account
        UserBankDetails senderAccount = bankDetailsRepo.findByUserId(sender.getId())
                .orElseThrow(() -> new RuntimeException("Sender has no bank account"));
        
        // Check sender has sufficient balance
        if (senderAccount.getBalance() < transactionInitiationRequest.getAmount()) {
            throw new RuntimeException("Insufficient balance. Available: " + senderAccount.getBalance() + 
                                     ", Required: " + transactionInitiationRequest.getAmount());
        }
        
        Transactions transactions = new Transactions();
        transactions.setAmount(transactionInitiationRequest.getAmount());
        transactions.setSender(sender);
        transactions.setReceiver(receiver);
        transactions.setStatus(TransactionStatus.PENDING);
        transactionRepo.save(transactions);
        
        TransactionInitiationResponse transactionInitiationResponse = new TransactionInitiationResponse();
        transactionInitiationResponse.setTransaction_id(transactions.getId());
        transactionInitiationResponse.setStatus("PENDING");
        transactionInitiationResponse.setMessage("Transaction initiated. Please verify with face to complete.");
        return transactionInitiationResponse;
    }

    //verification & Approve/Rejection using face info
    @Transactional
    public TransactionVerifyFaceResponse verifyTransaction(
            TransactionVerifyFaceRequest request, String ip) {

        System.out.println("=== TRANSACTION VERIFICATION START ===");
        System.out.println("Transaction ID: " + request.getTransaction_id());
        System.out.println("User ID: " + request.getUser_id());
        
        Transactions transactions = transactionRepo.findById(request.getTransaction_id())
                .orElseThrow(() -> new RuntimeException("Transaction not found"));

        System.out.println("Current Status: " + transactions.getStatus());
        System.out.println("Amount: " + transactions.getAmount());
        
        // ✅ OWNERSHIP CHECK
        if (!transactions.getSender().getId().equals(request.getUser_id())) {
            throw new RuntimeException("User does not own this transaction");
        }

        // ✅ STATE CHECK
        if (transactions.getStatus() != TransactionStatus.PENDING) {
            throw new RuntimeException("Transaction already processed: " + transactions.getStatus());
        }

        // ✅ FACE VERIFICATION
        System.out.println("Calling face verification service...");
        FaceVerifyResult faceVerifyResult =
                faceRecognitionService.verifyFace(
                        request.getUser_id(),
                        request.getImage(),
                        ip
                );

        System.out.println("Face verified: " + faceVerifyResult.isVerified());
        System.out.println("Similarity: " + faceVerifyResult.getSimilarity());
        
        TransactionVerifyFaceResponse response = new TransactionVerifyFaceResponse();
        response.setSimilarity(faceVerifyResult.getSimilarity());

        // ✅ FETCH BOTH SENDER AND RECEIVER BANK ACCOUNTS
        UserBankDetails senderAccount =
                bankDetailsRepo.findByUserId(transactions.getSender().getId())
                        .orElseThrow(() -> new RuntimeException("Sender bank account not found"));
        
        UserBankDetails receiverAccount =
                bankDetailsRepo.findByUserId(transactions.getReceiver().getId())
                        .orElseThrow(() -> new RuntimeException("Receiver bank account not found"));

        System.out.println("Sender balance BEFORE: " + senderAccount.getBalance());
        System.out.println("Receiver balance BEFORE: " + receiverAccount.getBalance());
        
        if (faceVerifyResult.isVerified()) {

            // ✅ SUFFICIENT BALANCE CHECK (re-check before deduction)
            if (senderAccount.getBalance() < transactions.getAmount()) {
                transactions.setStatus(TransactionStatus.REJECTED);
                transactions.setFailureReason("Insufficient balance");
                transactionRepo.save(transactions);

                response.setStatus("REJECTED");
                response.setMessage("Insufficient balance. Available: " + senderAccount.getBalance() + 
                                  ", Required: " + transactions.getAmount());
                return response;
            }

            // ✅ PERFORM TRANSFER - DEDUCT FROM SENDER AND ADD TO RECEIVER
            float newSenderBalance = senderAccount.getBalance() - transactions.getAmount();
            float newReceiverBalance = receiverAccount.getBalance() + transactions.getAmount();
            
            senderAccount.setBalance(newSenderBalance);
            receiverAccount.setBalance(newReceiverBalance);
            
            System.out.println("Sender balance AFTER: " + newSenderBalance);
            System.out.println("Receiver balance AFTER: " + newReceiverBalance);
            
            bankDetailsRepo.save(senderAccount);
            bankDetailsRepo.save(receiverAccount);
            
            System.out.println("Bank accounts updated successfully");

            // ✅ TRANSACTION APPROVAL
            transactions.setStatus(TransactionStatus.APPROVED);
            transactions.setApprovedAt(Instant.now());
            transactions.setFailureReason(null);

            response.setStatus("APPROVED");
            response.setMessage("Transaction approved! Transferred: $" + transactions.getAmount() + 
                              " from " + transactions.getSender().getFirstName() + 
                              " to " + transactions.getReceiver().getFirstName());

        } else {
            System.out.println("Face verification FAILED");
            transactions.setStatus(TransactionStatus.REJECTED);
            transactions.setFailureReason("Face mismatch");

            response.setStatus("REJECTED");
            response.setMessage("Transaction rejected due to face mismatch. Similarity: " + faceVerifyResult.getSimilarity());
        }

        transactionRepo.save(transactions);
        System.out.println("Transaction status updated to: " + transactions.getStatus());
        System.out.println("=== TRANSACTION VERIFICATION END ===");
        
        return response;
    }

}
