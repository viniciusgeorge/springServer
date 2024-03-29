package server.security;

import java.security.SecureRandom;
import java.util.concurrent.TimeUnit;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;



public class SmsCode {

	
	private class Code
	{
		private String code;
	    //private LocalDateTime expireTime;
	
	    public Code() {
	    	
	    	SecureRandom sr = new SecureRandom();
	        this.code = Integer.toString(sr.nextInt(900000) + 100000);
	      //  this.expireTime = LocalDateTime.now().plusSeconds(60);
	    }
	    
	    
	    public String getCode() {
	        return code;
	    }
	    
	    
	}
	
    //private static HashMap<String, Sms> smsMap = new HashMap<String,Sms>();

    private static Cache <String,Code> smsCache = CacheBuilder.newBuilder().expireAfterWrite(60, TimeUnit.SECONDS).build();
    
    public String newCode(String number)
    {
    	
    	
    	
    	Code sms = new Code();
    	smsCache.put(number, sms);
    	return sms.getCode();
    	
    }
    
    public boolean validate(String number, String code)
    {
    	Code sms = smsCache.getIfPresent(number);
    	System.out.println("teste1");
    	System.out.println(number);
    	if (sms != null)
    	{
    		System.out.println("teste2");
    		System.out.println(code);
    		System.out.println(sms.getCode());
    		if(sms.getCode().equals(code))
    		{
    			System.out.println("teste3");
    			return true;
    		}
    		
    		else
    		{
    			return false;
    		}
    	}
    	else
    	{
    		return false;
    	}
    }
	
}
