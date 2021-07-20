package com.example.bitgointerview;

import java.sql.Timestamp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BitgointerviewApplication {
	
	

	public static void main(String[] args) {
		SpringApplication.run(BitgointerviewApplication.class, args);
		System.out.println("Hello Block Chain");
		
		
		try {
			Blockchain rrCoin = new Blockchain(2);
//			rrCoin.addBlock(new Block(1, new Timestamp(System.currentTimeMillis()), "Amount: 1", ""));
//			rrCoin.addBlock(new Block(2, new Timestamp(System.currentTimeMillis()), "Amount: 10000", ""));		
//			rrCoin.addBlock(new Block(3, new Timestamp(System.currentTimeMillis()), "Amount: 10", ""));
//			rrCoin.printChain();
			
			
			rrCoin.createTransaction(new Transaction("Address1", "Address2", 100));
			rrCoin.createTransaction(new Transaction("Address2", "Address1", 50));
			
			
			System.out.println("Starting Miner");
			rrCoin.minePendingTransactions("Address3");
			
			System.out.println("Balance is " + rrCoin.getBalanceofAddress("Address3"));
			
			System.out.println("Starting Miner Again");
			rrCoin.minePendingTransactions("Address3");
			
			System.out.println("Balance is " + rrCoin.getBalanceofAddress("Address3"));
			
			rrCoin.printChain();
			System.out.println( "Is Block Chain Valid: " + rrCoin.isChainValid());
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
