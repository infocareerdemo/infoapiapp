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
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.info.entity.RefImage;
import com.info.entity.RefVideo;
import com.info.entity.UserKYC;
import com.info.repository.RefImageRepository;
import com.info.repository.RefVideoRepository;
import com.info.repository.UserKYCRepository;

@Service
public class UserKYCService {

	@Autowired
	UserKYCRepository userKYCRepository;

	@Autowired
	Environment env;

	@Autowired
	RefImageRepository refImageRepository;

	@Autowired
	RefVideoRepository refVideoRepository;

	public UserKYC registerUser(UserKYC userKYC, MultipartFile img, MultipartFile vdo) throws IOException {

		Map<String, Object> map = new HashMap<>();

		UserKYC email = userKYCRepository.findByEmail(userKYC.getEmail());
		UserKYC phone = userKYCRepository.findByPhone(userKYC.getPhone());

		UserKYC user = new UserKYC();
		BeanUtils.copyProperties(userKYC, user);

		if (email == null) {
			if (phone == null) {

				UserKYC usr = userKYCRepository.save(user);

				String extUrl = env.getProperty("ext.app.dir");

				if (img != null && !img.isEmpty()) {

					String userUrl = extUrl + "/infoImages/user/";
					File newFolder = new File(userUrl + usr.getId());
					if (!newFolder.exists()) {
						newFolder.mkdirs();
					}
					byte[] bytes = img.getBytes();
					Path path = Paths.get(newFolder + "/" + img.getOriginalFilename());
					Files.write(path, bytes);

					String filePath = newFolder + "/" + img.getOriginalFilename();

					RefImage refImage = new RefImage();
					refImage.setImgname(img.getOriginalFilename());
					refImage.setPath(filePath);
					refImage.setUserId(usr);
					RefImage image = refImageRepository.save(refImage);

					usr.setImage(image.getImgname());

				}

				if (vdo != null && !vdo.isEmpty()) {

					String userUrl = extUrl + "/infoVideo/user/";
					File newFolder = new File(userUrl + usr.getId());
					if (!newFolder.exists()) {
						newFolder.mkdirs();
					}
					byte[] bytes = vdo.getBytes();
					Path path = Paths.get(newFolder + "/" + vdo.getOriginalFilename());
					Files.write(path, bytes);

					String filePath = newFolder + "/" + vdo.getOriginalFilename();

//					vdo.transferTo(new File(filePath));

					RefVideo refVideo = new RefVideo();
					refVideo.setPath(filePath);
					refVideo.setUserId(usr);
					refVideo.setVdoname(vdo.getOriginalFilename());

					RefVideo video = refVideoRepository.save(refVideo);

				}

				userKYCRepository.save(usr);
			} else {
				map.put("Error", "Email Already Exist");
			}

		} else {
			map.put("Error", "Email Already Exist");
		}

		return user;

	}

//    public ResponseEntity<String> handleFileUpload(int id, MultipartFile file, String field) {
//        try {
//        	String extUrl = env.getProperty("ext.app.dir");
//        	String userUrl = extUrl + "/infoImages/user/";
//			File newFolder = new File(userUrl);
//			String filePath = newFolder + "/" + file.getOriginalFilename();
//
//            // Save the file to the server
//            file.transferTo(new File(filePath));
//
//            // Find the user by ID and update the image or video field
//            Optional<UserKYC> optionalUser = userKYCRepository.findById(id);
//            if (optionalUser.isPresent()) {
//            	UserKYC user = optionalUser.get();
//                if ("profileImage".equals(field)) {
//                    user.setProfileImage(filePath);
//                } else if ("profileVideo".equals(field)) {
//                    user.setProfileVideo(filePath);
//                }
//                userRepository.save(user);
//            }
//
//            return ResponseEntity.ok("File uploaded successfully: " + filename);
//        } catch (IOException e) {
//            e.printStackTrace();
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to upload the file.");
//        }
//    }

	public Optional<UserKYC> getUserById(int id) {
		Optional<UserKYC> user = userKYCRepository.findById(id);
		return user;
	}

}
