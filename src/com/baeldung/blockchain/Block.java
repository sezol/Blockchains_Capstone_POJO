package com.baeldung.blockchain;


import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;


//define a simple POJO that will hold the data for our block
public class Block {
    private static Logger logger = Logger.getLogger(Block.class.getName());
    private String hash;
    private String previousHash;
    private String data;
    private long timeStamp;
    private int nonce;

    //hash constructor
    public Block(String data, String previousHash, long timeStamp) {
            this.data = data;
            this.previousHash = previousHash;
            this.timeStamp = new Date().getTime();
            this.timeStamp = timeStamp;
            this.hash = calculateBlockHash();
        }




    //generate the hash of our block in Java
        public String calculateBlockHash() {
            String dataToHash = previousHash + Long.toString(timeStamp) + Integer.toString(nonce) + data;
            MessageDigest digest = null;
            byte[] bytes = null;
            try {
                digest = MessageDigest.getInstance("SHA-256");
                bytes = digest.digest(dataToHash.getBytes("UTF-8"));
            } catch (NoSuchAlgorithmException | UnsupportedEncodingException ex) {
                logger.log(Level.SEVERE, ex.getMessage());
            }
            StringBuffer buffer = new StringBuffer();
            for (byte b : bytes) {
                buffer.append(String.format("%02x", b));
            }
            return buffer.toString();
        }

        public String getHash() {
            return this.hash;
        }
        public String getPreviousHash() {
            return this.previousHash;
        }
        public void setData(String data) {
            this.data = data;
        }

        //mining
        public String mineBlock(int prefix) {
            String prefixString = new String(new char[prefix]).replace('\0', '0');
            while (!hash.substring(0, prefix)
                    .equals(prefixString)) {
                nonce++;
                hash = calculateBlockHash();
            }
            return hash;
        }
    }