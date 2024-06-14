package ibf2023.csf.backend.services;

import java.io.IOException;
import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import ibf2023.csf.backend.repositories.ImageRepository;
import ibf2023.csf.backend.repositories.PictureRepository;

@Service
public class PictureService {

	@Autowired
	ImageRepository s3Repo;

	@Autowired
	PictureRepository picRepo;

	// TODO Task 5.1
	// You may change the method signature by adding parameters and/or the return type
	// You may throw any exception 
	public String save(String title, String comments, MultipartFile picture, String datetime) throws ParseException {

		String s3Url = "";

		try {
			s3Url = s3Repo.save(title, comments, picture, datetime);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if (picRepo.getMemory() >= picture.getSize()) {
			String _id = picRepo.save(title, comments, s3Url, datetime, picture.getSize());
			return _id;
		} else {
			return "";
		}

	}
}
