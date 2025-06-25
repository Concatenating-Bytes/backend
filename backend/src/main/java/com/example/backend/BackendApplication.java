package com.example.backend;

import com.example.backend.DTO.UserTransactions;
import com.example.backend.Entity.Transactions;
import com.example.backend.Entity.User;
import com.example.backend.Repo.TransactionRepo;
import com.example.backend.Repo.UserRepo;
import com.example.backend.Service.UserService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@SpringBootApplication
public class BackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(BackendApplication.class, args);
//		ApplicationContext context = SpringApplication.run(BackendApplication.class, args);
//		User user=context.getBean(User.class);
//		Transactions transactions = context.getBean(Transactions.class);
//
//		TransactionRepo repo = context.getBean(TransactionRepo.class);
//
//
//
//		UserRepo repo2 = context.getBean(UserRepo.class);
//		user.setEmail("sadasdag@gmail.com");
//		user.setDateOfBirth("01-12-2002");
//		user.setFirstName("Niggaish");
//		user.setLastName("Biswas");
//		user.setPhone_no("+916289123456");
//		repo.save(user);
//
//		UUID senderId = UUID.fromString("4c6fa519-3602-4107-8209-d2025f52e018");
//		UUID receiverId =UUID.fromString("a27dbc9e-9a7d-4c10-a698-3ec540876242");
//
//		User u1 = repo2.findById(senderId).get();
//		User u2 = repo2.findById(receiverId).get();

//		UserService userService = context.getBean(UserService.class);
//		List<UserTransactions> allTransactions = userService.getUserTransactions(senderId);
//
//		System.out.println(allTransactions);

//		transactions.setAmount(1000);
//		transactions.setStatus(true);
//		transactions.setSender(u1);
//		transactions.setReceiver(u2);
//		repo.save(transactions);
	}

}
