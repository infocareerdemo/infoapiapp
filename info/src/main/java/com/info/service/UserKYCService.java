package com.info.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.info.entity.UserKYC;
import com.info.repository.UserKYCRepository;

import jakarta.mail.MessagingException;

@Service
public class UserKYCService {

	@Autowired
	UserKYCRepository userKYCRepository;

	@Autowired
	Environment env;

	@Autowired
	UserService userService;

	public Map<String, Object> registerUser(UserKYC userKYC, MultipartFile img, MultipartFile vdo, String siteURL)
			throws IOException, MessagingException {

		Map<String, Object> map = new HashMap<>();

		UserKYC email = userKYCRepository.findByEmail(userKYC.getEmail());
		UserKYC phone = userKYCRepository.findByPhone(userKYC.getPhone());

		UserKYC user = new UserKYC();
		BeanUtils.copyProperties(userKYC, user);

		if (email == null) {
			if (phone == null) {

				UserKYC usr = userKYCRepository.save(user);

				String extUrl = env.getProperty("app.dir");

				if (img != null && !img.isEmpty()) {

					String userUrl = extUrl + "/infoImages";
					File newFolder = new File(userUrl);
					if (!newFolder.exists()) {
						newFolder.mkdirs();
					}
					byte[] bytes = img.getBytes();
					Path path = Paths.get(newFolder + "/" + "user_" + usr.getId() + "_" + img.getOriginalFilename());
					Files.write(path, bytes);

					String filePath = "user_" + usr.getId() + "_" + img.getOriginalFilename();

					usr.setImage(filePath);

				}

				if (vdo != null && !vdo.isEmpty()) {

					String userUrl = extUrl + "/infoVideo";
					File newFolder = new File(userUrl);
					if (!newFolder.exists()) {
						newFolder.mkdirs();
					}
					byte[] bytes = vdo.getBytes();
					Path path = Paths.get(newFolder + "/" + "user_" + usr.getId() + "_" + vdo.getOriginalFilename());
					Files.write(path, bytes);

					String filePath = "user_" + usr.getId() + "_" + vdo.getOriginalFilename();

					usr.setVideo(filePath);

				}

				UserKYC u = userKYCRepository.save(usr);

				userService.registerUser(u, siteURL);

				map.put("Message", "User Saved..!!");

			} else {
				map.put("Error", "Phone Already Exist");
			}

		} else {
			map.put("Error", "Email Already Exist");
		}

		return map;

	}

}
