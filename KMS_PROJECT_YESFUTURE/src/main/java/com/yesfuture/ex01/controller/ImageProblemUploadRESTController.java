package com.yesfuture.ex01.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.yesfuture.ex01.domain.AttachProblemVO;
import com.yesfuture.ex01.service.AttachService;
import com.yesfuture.ex01.util.FileUploadUtil;

import lombok.extern.log4j.Log4j;

@RestController
@RequestMapping(value = "/imageProblem")
@Log4j
public class ImageProblemUploadRESTController {
	
	@Autowired
	private String uploadPath;

	@Autowired
	private AttachService attachService;

	@PostMapping
	public ResponseEntity<ArrayList<AttachProblemVO>> createImage(MultipartFile[] files) {
	    log.info("createImage()");

	    ArrayList<AttachProblemVO> list = new ArrayList<>();

	    for (MultipartFile file : files) {

	        // UUID 생성
	        String chgName = UUID.randomUUID().toString();
	        // 파일 저장
	        FileUploadUtil.saveFile(uploadPath, file, chgName);

	        String path = FileUploadUtil.makeDatePath();
	        String extension = FileUploadUtil.subStrExtension(file.getOriginalFilename());

	        FileUploadUtil.createThumbnail(uploadPath, path, chgName, extension);
	        
	        AttachProblemVO attachProblemVO = new AttachProblemVO();
	        // 파일 경로 설정
	        attachProblemVO.setAttachProblemPath(path);
	        // 파일 실제 이름 설정
	        attachProblemVO.setAttachProblemRealName
	        (FileUploadUtil.subStrName(file.getOriginalFilename()));
	        // 파일 변경 이름(UUID) 설정
	        attachProblemVO.setAttachProblemChgName(chgName);
	        // 파일 확장자 설정
	        attachProblemVO.setAttachProblemExtension(extension);

	        list.add(attachProblemVO);
	    }

	    return new ResponseEntity<ArrayList<AttachProblemVO>>(list, HttpStatus.OK);
	}

	// 전송받은 파일 경로 및 파일 이름, 확장자로 
	// 이미지 파일을 호출
	@GetMapping("/display")
	public ResponseEntity<byte[]> display
	        (String attachProblemPath, String attachProblemChgName, String attachProblemExtension) {
	    log.info("display()");
	    ResponseEntity<byte[]> entity = null;
	    try {
	        // 파일을 읽어와서 byte 배열로 변환
	        String savedPath = uploadPath + File.separator 
	                + attachProblemPath + File.separator + attachProblemChgName; 
	        if(attachProblemChgName.startsWith("t_")) { // 섬네일 파일에는 확장자 추가
	            savedPath += "." + attachProblemExtension;
	        }
	        Path path = Paths.get(savedPath);
	        byte[] imageBytes = Files.readAllBytes(path);


	        Path extensionPath = Paths.get("." + attachProblemExtension);
	        // 이미지의 MIME 타입 확인하여 적절한 Content-Type 지정
	        String contentType = Files.probeContentType(extensionPath);

	        // HTTP 응답에 byte 배열과 Content-Type을 설정하여 전송
	        HttpHeaders httpHeaders = new HttpHeaders();
	        httpHeaders.setContentType(MediaType.parseMediaType(contentType));
	        entity = new ResponseEntity<byte[]>(imageBytes, httpHeaders, HttpStatus.OK);
	    } catch (IOException e) {
	        // 파일을 읽는 중에 예외 발생 시 예외 처리
	        e.printStackTrace();
	        return ResponseEntity.notFound().build(); // 파일을 찾을 수 없음을 클라이언트에게 알림
	    }

	    return entity;
	}


	@GetMapping("/get")
	public ResponseEntity<byte[]> getImage(int attachProblemId, String attachProblemExtension) {
	    log.info("getImage()");
	    
	    AttachProblemVO attachProblemVO = attachService.getAttachProblemById(attachProblemId);
	    ResponseEntity<byte[]> entity = null;
	    try {
	        // 파일을 읽어와서 byte 배열로 변환
	        String savedPath = uploadPath + File.separator 
	                + attachProblemVO.getAttachProblemPath() + File.separator; 

	        if(attachProblemExtension != null) {
	            savedPath += "t_" + attachProblemVO.getAttachProblemChgName() + "." 
	        + attachProblemVO.getAttachProblemExtension();                    
	        } else {
	            savedPath += attachProblemVO.getAttachProblemChgName();
	        }
	        Path path = Paths.get(savedPath);
	        byte[] imageBytes = Files.readAllBytes(path);

	        Path extensionPath = Paths.get("." + attachProblemVO.getAttachProblemExtension());
	        // 이미지의 MIME 타입 확인하여 적절한 Content-Type 지정
	        String contentType = Files.probeContentType(extensionPath);

	        // HTTP 응답에 byte 배열과 Content-Type을 설정하여 전송
	        HttpHeaders httpHeaders = new HttpHeaders();
	        httpHeaders.setContentType(MediaType.parseMediaType(contentType));
	        entity = new ResponseEntity<byte[]>(imageBytes, httpHeaders, HttpStatus.OK);
	    } catch (IOException e) {
	        // 파일을 읽는 중에 예외 발생 시 예외 처리
	        e.printStackTrace();
	        return ResponseEntity.notFound().build(); // 파일을 찾을 수 없음을 클라이언트에게 알림
	    }

	    return entity;
	}

	// 섬네일 및 원본 이미지 삭제 기능
	@PostMapping("/delete")
	public ResponseEntity<Integer> deleteImage
	        (String attachProblemPath, String attachProblemChgName, String attachProblemExtension) {
	    log.info("deleteAttach()");
	    log.info(attachProblemChgName);
	    FileUploadUtil.deleteFile(uploadPath, attachProblemPath, attachProblemChgName);
	    
	    String thumbnailName = "t_" + attachProblemChgName + "." + attachProblemExtension;
	    FileUploadUtil.deleteFile(uploadPath, attachProblemPath, thumbnailName);
	    
	    return new ResponseEntity<Integer>(1, HttpStatus.OK);
	}
}
