package com.example.mvc;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

// 커맨드 객체로 사용될 클래스 정의
class MyCommandObject {
    private String value1;
    private Integer value2;

    // 반드시 세터 메서드가 있어야 함
    public void setValue1(String value1) { this.value1 = value1; }
    public void setValue2(Integer value2) { this.value2 = value2; }

    @Override
    public String toString() {
        return "MyCommandObject{value1='" + value1 + '\'' + ", value2=" + value2 + '}';
    }
}

class SignUpFormData {
    // 회원가입에 필요한 정보 생각하며 필드 정의(아이디, 이름, 이메일, 전화번호 등등)
    private String id;
    private String name;
    private String email;
    private String phoneNumber;

    // 세터 메서드 정의
    public void setId(String id) {this.id = id;}
    public void setName(String name) {this.name = name;}
    public void setEmail(String email) {this.email = email;}
    public void setPhoneNumber(String phoneNumber) {this.phoneNumber = phoneNumber;}

    // toString 메서드 정의(자동완성)
    @Override
    public String toString() {
        return "SignUpFormData{id='" + id + '\'' + ", name='" + name + '\'' + ", email='" + email + '\'' + ", phoneNumber='" + phoneNumber + '\'' + '}';
    }
}

class MyJsonData {
    private String value1;
    private Integer value2;

    public String getValue1() { return value1; }
    public Integer getValue2() { return value2; }
    public void setValue1(String value1) { this.value1 = value1; }
    public void setValue2(Integer value2) { this.value2 = value2; }
}

@Controller
@RequestMapping("/renew")
public class MyRenewController {

    // 요청 메시지의 Content-Type이 "applicaion/json"인 요청을 받아들이기 위해서 consumes를 MediaType.APPLICATION_JSON_VALUE로 설정
    // 응답 메시지의 Content-Type이 "applicaion/json"이므로 produces를 MediaType.APPLICATION_JSON_VALUE로 설정
    @GetMapping(value = "/json-test",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    // 반환 타입으로 MyJsonData를 설정하였고 ResponseBody 어노테이션을 통해 해당 값이 메시지 컨버터를 통해서 직렬화되어야 함을 알림
    @ResponseBody
    // RequestBody 어노테이션을 통해 요청 메시지의 바디에 포함된 JSON 문자열이 메시지 컨버터를 통해서 역직렬화되어 객체로 변환되어야 함을 알림
    public MyJsonData jsonTest(@RequestBody MyJsonData myJsonData) {
        System.out.println(myJsonData);
        return myJsonData;
    }



    @PostMapping("/sign_up")
    public String signUp(@ModelAttribute SignUpFormData signUpFormData) {
        return signUpFormData.toString();
    }



    @PostMapping("/test")
    // @ModelAttribute를 타입 앞에 붙여주고 메서드의 파라미터 값으로 전달되게 함
    public String commandObjectTest(@ModelAttribute MyCommandObject myCommandObject) {
        return myCommandObject.toString();
    }



    @GetMapping(value = "/echo", produces = MediaType.TEXT_PLAIN_VALUE)
    @ResponseBody
    public String echo(@RequestBody byte[] content) {
        String bytesToString = new String(content, StandardCharsets.UTF_8);
        System.out.println(bytesToString);
        return bytesToString;
    }
    /* 이것도 가능 */
//    @ResponseBody
//    public String example(@RequestBody String content) {
//        return content;
//    }



    @GetMapping(value = "/hello-html", produces = MediaType.TEXT_HTML_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public String helloHTML() {
        return "<h1>Hello</h1>";
    }



    @GetMapping(value = "/echo-repeat", produces = MediaType.TEXT_PLAIN_VALUE)
    // @RequestHeader 어노테이션을 통해서 X-Repeat-Count에 적힌 숫자 정보 가져오고 없으면 1로 초기화
    public String echoRepeat(@RequestParam("word") String word, @RequestHeader(value = "X-Repeat-Count", defaultValue = "1") Integer repeatCount) throws IOException {
        String result = "";
        for(int i=0;i<repeatCount;i++) {
            result += word + " ";
        }
        return result;
    }



    @GetMapping(value = "/dog-image", produces = MediaType.IMAGE_JPEG_VALUE)
    public byte[] dogImage() throws IOException {
        // resources 폴더의 static 폴더에 이미지 있어야 함
        File file = ResourceUtils.getFile("classpath:static/고양이 사진.jpg");
        // 파일의 바이트 데이터 모두 읽어오기
        byte[] bytes = Files.readAllBytes(file.toPath());

        return bytes;
    }



    @GetMapping(value = "/dog-image-file", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    // 헤더를 직접 조정하고 싶은 경우 ResponseEntity 타입을 반환하도록 설정 가능
    // (꺽쇠 안에는 응답 메시지의 바디 데이터에 포함될 타입을 지정)
    public ResponseEntity<byte[]> dogImageFile() throws IOException {
        File file = ResourceUtils.getFile("classpath:static/고양이 사진.jpg");
        byte[] bytes = Files.readAllBytes(file.toPath());

        String filename = "cat.jpg";
        // 헤더 값 설정
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment; filename=" + filename);

        return new ResponseEntity<>(bytes, headers, HttpStatus.OK);
    }
}
