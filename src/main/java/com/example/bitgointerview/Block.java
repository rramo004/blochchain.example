/**
 * 
 */
package com.example.bitgointerview;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author rramos
 *
 */
public class Block {
	
	//Integer index;
	String previousHash;
	Timestamp timestamp;
	ArrayList<Transaction> transactions = new ArrayList<Transaction>();
	String hash;
	int difficulty;
	int nonce = 0;

	public Block(Timestamp timestamp, ArrayList<Transaction> transactions, String previousHash) {
		this.timestamp = timestamp;
		this.transactions = transactions;
		this.previousHash = previousHash;
		this.hash = this.calculateHash();	
	}


	public String calculateHash() {
		String hash = "";

		try {
			MessageDigest digest = MessageDigest.getInstance("SHA-256");
			String input = "";
			for (Transaction trans : this.transactions) {
				 input = String.valueOf(timestamp) + trans.fromAddress + trans.toAddress + String.valueOf(trans.amount) + String.valueOf(this.nonce);
			}
			
			hash = toHexString(digest.digest(input.getBytes(StandardCharsets.UTF_8)));
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		

		return hash;
	}
	
	
	private static String toHexString(byte[] hash) {
//        BigInteger number = new BigInteger(1, hash); 
//
//        StringBuilder hexString = new StringBuilder(number.toString(16)); 
//  
//        while (hexString.length() < 32) 
//        { 
//            hexString.insert(0, '0'); 
//        } 
		StringBuffer hexString = new StringBuffer();
		for (int i=0;i<hash.length;i++) {
		   hexString.append(Integer.toHexString(0x100 | 0xFF & hash[i]).substring(1));
		}
		
        return hexString.toString(); 
	}
	
	
	
	public void mineBlock(int difficulty) {
		StringBuffer outBuf = new StringBuffer(difficulty);
		for (int i = 0; i < difficulty; i++){
			outBuf.append("0");
		}
		//System.out.println(outBuf.toString());
		
		while (!this.hash.substring(0, difficulty).equals(outBuf.toString())) {
			this.nonce++;
			this.hash = this.calculateHash();
			//System.out.println(this.hash);
		}
		//System.out.println("Block Mined: " + this.hash);
	}
}
