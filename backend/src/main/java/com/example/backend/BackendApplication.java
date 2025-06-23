package com.example.backend;

import com.example.backend.Entity.Transactions;
import com.example.backend.Entity.User;
import com.example.backend.Repo.TransactionRepo;
import com.example.backend.Repo.UserRepo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

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
//		user.setEmail("something@gmail.com");
//		user.setDateOfBirth("01-12-2002");
//		user.setFirstName("Sourasish");
//		user.setLastName("Biswas");
//		user.setPhone_no("+916289358146");
//
//		UUID senderId = UUID.fromString("a27dbc9e-9a7d-4c10-a696-3ec540876242");
//		UUID recieverId =UUID.fromString("a27dbc9e-9a9d-4c10-a696-3ec540876242");
//
//		transactions.setAmount(1000);
//		transactions.setStatus(true);
//		transactions.setSenderId(senderId);
//		transactions.setReceiverId(recieverId);
//		repo.save(transactions);
	}

}
