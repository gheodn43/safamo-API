package com.backend.restapi.service;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

import org.springframework.stereotype.Service;

@Service
public class OtpService {
	private Map<String, OtpData> otpStore = new HashMap<>();

	public String generateOTP(String email) {
	 Random random = new Random();
	 StringBuilder otp = new StringBuilder();
	 for (int i = 0; i < 4; i++) {
	     int digit = random.nextInt(10); // Lấy một số ngẫu nhiên từ 0 đến 9
	     otp.append(digit);
	 }
	    // Lưu mã OTP và thời gian tạo vào cơ sở dữ liệu (otpStore) với key là email
	    otpStore.put(email, new OtpData(otp.toString(), System.currentTimeMillis()));
	    return otp.toString();
	}

	public boolean verifyOTP(String email, String userEnteredOTP) {
	    // Kiểm tra xem email có tồn tại trong cơ sở dữ liệu không
	    if (!otpStore.containsKey(email)) {
	        return false;
	    }

	    // Lấy thông tin mã OTP và thời gian tạo
	    OtpData otpData = otpStore.get(email);
	    String storedOTP = otpData.getOtp();
	    long creationTime = otpData.getCreationTime();

	    // Kiểm tra xem mã OTP đã hết hạn hay chưa (ví dụ: 5 phút hết hạn)
	    long currentTime = System.currentTimeMillis();
	    long timeElapsed = currentTime - creationTime;
	    long otpValidityPeriod = 5 * 60 * 1000;

	    if (timeElapsed > otpValidityPeriod) {
	        otpStore.remove(email);
	        return false;
	    }
	    boolean otpMatch = userEnteredOTP.equals(storedOTP);
	    otpStore.remove(email);

	    return otpMatch;
	}

	public class OtpData {
	    private String otp;
	    private long creationTime;

	    public OtpData(String otp, long creationTime) {
	        this.otp = otp;
	        this.creationTime = creationTime;
	    }

	    public String getOtp() {
	        return otp;
	    }

	    public long getCreationTime() {
	        return creationTime;
	    }
	}

}

