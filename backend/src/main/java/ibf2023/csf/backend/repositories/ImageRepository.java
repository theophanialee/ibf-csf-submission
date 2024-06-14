package ibf2023.csf.backend.repositories;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.PutObjectResult;

@Repository
public class ImageRepository {

	 @Autowired
    private AmazonS3 s3;

	// TODO Task 4.1
	// You may change the method signature by adding parameters and/or the return type
	// You may throw any exception 
	public String save(String title, String comments, MultipartFile picture, String datetime) throws IOException {
		// IMPORTANT: Write the native mongo query in the comments above this method
  // User metadata 
  Map<String, String> userData = new HashMap<>();
  userData.put("upload-timestamp", (new Date()).toString());
  userData.put("filename", title);

  // Metadata for the file
  ObjectMetadata metadata = new ObjectMetadata();
  metadata.setContentType(picture.getContentType());
  metadata.setContentLength(picture.getSize());
  metadata.setUserMetadata(userData);

  String key = UUID.randomUUID().toString().substring(0, 8);

  // bucket name, key, input stream, metadata
  PutObjectRequest putReq = new PutObjectRequest("csf-assessment-pha", key
		, picture.getInputStream(), metadata);
  // Make the object publically available
  putReq = putReq.withCannedAcl(CannedAccessControlList.PublicRead);

  s3.putObject(putReq);

  return s3.getUrl("csf-assessment-pha", key).toString();
	}
}

