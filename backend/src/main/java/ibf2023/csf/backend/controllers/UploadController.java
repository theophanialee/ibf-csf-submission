package ibf2023.csf.backend.controllers;

import java.io.IOException;
import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import ibf2023.csf.backend.error.ResponseMessage;
import ibf2023.csf.backend.services.PictureService;
import jakarta.json.Json;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


// You can add addtional methods and annotations to this controller. 
// You cannot remove any existing annotations or methods from UploadController
@Controller
@RequestMapping(path="/api")
public class UploadController {

	@Autowired
	PictureService picSvc;

	// TODO Task 5.2
	// You may change the method signature by adding additional parameters and annotations.
	// You cannot remove any any existing annotations and parameters from postUpload()
	@PostMapping(path="/image/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<String> postUpload(@RequestPart String title, @RequestPart String comments,
			@RequestPart MultipartFile picture, @RequestPart String datetime) {
		if (comments==null){
			comments="";
		};
		System.out.println(">>>>>>>>> backend is called");
		String _id = "";
		try {
			_id = picSvc.save(title, comments, picture, datetime);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			return ResponseEntity.ok(
					Json.createObjectBuilder()
							.add("message", "IOException or Parsing exception when saving picture to s3.").build()
							.toString());
		}
		System.out.println(_id);

		if (_id.isEmpty()) {
			return ResponseEntity.ok(
					Json.createObjectBuilder()
							.add("message", "The upload has exceeded your monthly upload quota of 512MB").build()
							.toString());
		}

		return ResponseEntity.ok(
				Json.createObjectBuilder().add("_id", _id).build().toString()
		);
	}

	
}
