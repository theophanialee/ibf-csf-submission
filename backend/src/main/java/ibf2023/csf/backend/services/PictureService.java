package ibf2023.csf.backend.services;

import java.io.IOException;

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
	public String save(String title, String comments, MultipartFile picture, String datatime) throws IOException {
		return s3Repo.save(title, comments, picture, datatime);
	}
}
