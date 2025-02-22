package com.yesfuture.ex01.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.yesfuture.ex01.domain.AttachQuestionVO;
import com.yesfuture.ex01.service.AttachService;
import com.yesfuture.ex01.util.FileUploadUtil;

import lombok.extern.log4j.Log4j;

@RestController
@RequestMapping(value = "/attach")
@Log4j
public class AttachUploadRESTController {
    
	@Autowired
    private String uploadPath;
    
    @Autowired
    private AttachService attachService;

    // 첨부 파일 생성 
	@PostMapping
	public ResponseEntity<ArrayList<AttachQuestionVO>> createAttachQuestion(MultipartFile[] files) {
		log.info("createAttachQuestion");

		ArrayList<AttachQuestionVO> list = new ArrayList<>();

		for (MultipartFile file : files) {

			// UUID 생성
			String chgName = UUID.randomUUID().toString();
			// 파일 저장
			FileUploadUtil.saveFile(uploadPath, file, chgName);

			String path = FileUploadUtil.makeDatePath();
			String extension = FileUploadUtil.subStrExtension(file.getOriginalFilename());

			AttachQuestionVO attachQuestionVO = new AttachQuestionVO();
			// 파일 경로 설정
			attachQuestionVO.setAttachQuestionPath(path);
			// 파일 실제 이름 설정
			attachQuestionVO.setAttachQuestionRealName(FileUploadUtil.subStrName(file.getOriginalFilename()));
			// 파일 변경 이름(UUID) 설정
			attachQuestionVO.setAttachQuestionChgName(chgName);
			// 파일 확장자 설정
			attachQuestionVO.setAttachQuestionExtension(extension);

			list.add(attachQuestionVO);
		}

		return new ResponseEntity<ArrayList<AttachQuestionVO>>(list, HttpStatus.OK);
	}
	
    // 첨부 파일 다운로드(GET) - Question
    @GetMapping(value = "/download", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public ResponseEntity<Resource> download(int attachQuestionId) throws IOException {
        log.info("download()");
        
        // attachId로 상세 정보 조회
        AttachQuestionVO attachQuestionVO = attachService.getAttachQuestionById(attachQuestionId);
        String attachPath = attachQuestionVO.getAttachQuestionPath();
        String attachChgName = attachQuestionVO.getAttachQuestionChgName();
        String attachExtension = attachQuestionVO.getAttachQuestionExtension();
        String attachRealName = attachQuestionVO.getAttachQuestionRealName();
        
        // 서버에 저장된 파일 정보 생성
        String resourcePath = uploadPath + File.separator + attachPath + File.separator + attachChgName;
        // 파일 리소스 생성
        Resource resource = new FileSystemResource(resourcePath);
        // 다운로드할 파일 이름을 헤더에 설정
        HttpHeaders headers = new HttpHeaders();
        String attachName = new String(attachRealName.getBytes("UTF-8"), "ISO-8859-1");
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + attachName + "." + attachExtension);

        // 파일을 클라이언트로 전송
        return new ResponseEntity<Resource>(resource, headers, HttpStatus.OK);
    } // end download()

	// 첨부 파일 삭제
    @PostMapping("/delete")
    public ResponseEntity<Integer> deleteAttach(String attachQuestionPath, String attachQuestionChgName) {
    	log.info("deleteAttach()");
    	FileUploadUtil.deleteFile(uploadPath, attachQuestionPath, attachQuestionChgName);
    	
    	return new ResponseEntity<Integer>(1, HttpStatus.OK);
    }
    
}