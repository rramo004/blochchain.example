package com.example.bitgointerview;

import java.sql.Timestamp;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


public class Blockchain {

	public ArrayList<Block> chain;
	int difficulty;
	ArrayList<Transaction> pendingTransactions = new ArrayList<Transaction>();
	int miningReward;
	
	public Blockchain(int difficulty) {
		this.chain = new ArrayList<Block>();
		this.chain.add(generateGenesisBlock());
		this.difficulty = difficulty;
		this.miningReward = 100;
	}

	private Block generateGenesisBlock() {
		ArrayList<Transaction> genesisTrans = new ArrayList<Transaction>();
		genesisTrans.add(new Transaction("", "", 0));
		return new Block(new Timestamp(System.currentTimeMillis()), genesisTrans, "0");
	}
	
	public Block getLatestBlock() {
		return this.chain.get(this.chain.size() - 1);
	}
	
//	public void addBlock(Block newBlock) {
//		newBlock.previousHash = this.getLatestBlock().hash;
//		//newBlock.hash = newBlock.calculateHash();
//		newBlock.mineBlock(this.difficulty);
//		this.chain.add(newBlock);
//	}

	public void minePendingTransactions(String miningRewardAddress) {
		Block newBlock = new Block(new Timestamp(System.currentTimeMillis()), this.pendingTransactions, "0");
		newBlock.previousHash = this.getLatestBlock().hash;
		newBlock.mineBlock(this.difficulty);
		
		
		System.out.println("Block Successfully mined!");
		this.chain.add(newBlock);
		this.pendingTransactions = new ArrayList<Transaction>();
		this.pendingTransactions.add(new Transaction("", miningRewardAddress, this.miningReward));
	}
	
	
	public void createTransaction(Transaction transaction) {
		this.pendingTransactions.add(transaction);
	}
	
	public int getBalanceofAddress(String address) {
		int balance = 0;
		
		for (Block b : this.chain) {
			for (Transaction trans : b.transactions) {
				if (address.equals(trans.fromAddress)) {
					balance -= trans.amount;
				}
				
				if (address.equals(trans.toAddress)) {
					balance += trans.amount;
				}
			}
		}
		
		return balance;
	}
	
	
	public boolean isChainValid() {
		for (int i = 1; i < this.chain.size(); i++) {
			Block curr = this.chain.get(i);
			Block prev = this.chain.get(i-1);
			
			if (!curr.hash.equals(curr.calculateHash()))
			{
				return false;
			}
			
			if (!curr.previousHash.equals(prev.hash))
			{
				return false;
			}

		}

		return true;
	}
	
	
	public void printChain() {
		for (Block b: chain) {
			System.out.println(b.timestamp);
			for (Transaction trans : b.transactions) {
				System.out.println(trans.fromAddress);
				System.out.println(trans.toAddress);
				System.out.println(trans.amount);
			}
			System.out.println(b.previousHash);
			System.out.println(b.hash);
			System.out.println("---------------------------------------------------------------------\n");
		}
	}
	
}
