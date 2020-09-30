package com.samcancode.security.services;

import java.util.Optional;
import java.util.Random;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.samcancode.security.domain.JpaOTP;
import com.samcancode.security.repository.JpaOtpRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class JpaOtpService {

	private JpaOtpRepository otpRepo;
	public JpaOtpService(JpaOtpRepository otpRepo) {
		this.otpRepo = otpRepo;
	}
	
	public String lookupOtp(String username) {
		Optional<JpaOTP> optOtp = otpRepo.findByUsername(username);
		JpaOTP otp = optOtp.orElseThrow(() -> new UsernameNotFoundException("OTP Not Available!"));
		return otp.getOtp();
	}
	
	public void generateOtp(String username) {
		Optional<JpaOTP> optOtp = otpRepo.findByUsername(username);

		if(optOtp.isPresent()) {
			JpaOTP a = optOtp.get();
			a.setOtp(generateOtpToken());
			otpRepo.save(a);
		}
		else {
			JpaOTP a = new JpaOTP();
			a.setUsername(username);
			a.setOtp(generateOtpToken());
			otpRepo.save(a);
		}
		
	}

	private String generateOtpToken() {
		// Generate random id, for example 283952-V8M32
		char[] chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789".toCharArray();
		Random rnd = new Random();
		StringBuilder sb = new StringBuilder((100000 + rnd.nextInt(900000)) + "-");
		for (int i = 0; i < 5; i++)
		    sb.append(chars[rnd.nextInt(chars.length)]);

		log.info("Generated OTP Token: " + sb.toString());
		return sb.toString();
	}
}
