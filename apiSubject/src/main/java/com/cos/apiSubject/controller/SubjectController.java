package com.cos.apiSubject.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.cos.apiSubject.domain.StartEndNumber;
import com.cos.apiSubject.domain.PrimeNumber;
import com.cos.apiSubject.domain.RealNumber;
import com.cos.apiSubject.domain.Result;
import com.cos.apiSubject.handler.ex.CustomApiException;

@RestController
public class SubjectController {

	private static final Logger log = LoggerFactory.getLogger(SubjectController.class);

	  @PostMapping("/realNumber")
	  public ResponseEntity<?> realNumber(@RequestBody RealNumber realNumber) {
		  
		  /*
		   이차 방정식 근의 개수 판별식		   
		   b*b - 4ac > 0 -> 서로 다른 두 근
		   b*b - 4ac = 0 -> 중근
		   b*b - 4ac < 0 -> 근이 없다 
		  */
		  		  
		  List<Integer> list = new ArrayList();
		  
		  int a = realNumber.getA();
		  int b = realNumber.getB();
		  int c = realNumber.getC();
		  
		  double determinant;
		  double root;
		  double x1,x2;
		  int a1,a2;
		  int b1;
		  
		  // Math.sqrt() 함수는 숫자의 제곱근을 반환합니다.
		  determinant=(b*b)-(4*a*c);
		  root = Math.sqrt(determinant);
		  
		  if(determinant>0){ // 1. 서로 다른 두 근

			  x1=(-b+root)/(2*a);

			  x2=(-b-root)/(2*a);

			  System.out.print("이차방정식의 근은 " +Integer.parseInt(String.format("%,.0f",x1))+"과"+Integer.parseInt(String.format("%,.0f",x2))+" 두개입니다.");
			
			  // 소수점 .0을 제거 해줍니다.
			  a1=Integer.parseInt(String.format("%,.0f",x1));
			  a2=Integer.parseInt(String.format("%,.0f",x2));
			  
			  list.add(a1);
			  list.add(a2);
			  
			  Collections.sort(list); // 오름차순 정렬
			  			  			 
			  
			  Result<List> result= new Result();		  		 
			  result.setResult(list);		
			  
			  System.out.println("realNumber 실행");
			  
			  return new ResponseEntity<>(result, HttpStatus.OK); // json으로 응답해줍니다.
			  
		  }else if(determinant==0) {  // 2. 중근

				  x1=(-b+root)/(2*a);
				  b1= Integer.parseInt(String.format("%,.0f",x1));
				  Result<Integer> result= new Result();
				  result.setResult(b1);
				  
				  return new ResponseEntity<>(result, HttpStatus.OK); // json으로 응답해줍니다.
				  
		
		  }else { // 3. 근이 없는 경우는 exception 처리
			  
			  	  throw new CustomApiException("이차방정식의 근이 없습니다");
			  	  
		  }
	  }



	@PostMapping("/startEndSum")
	public ResponseEntity<?> startEndSum(@RequestBody StartEndNumber number) {

		int startNum = Integer.parseInt(number.getStart());
		int endNum = Integer.parseInt(number.getEnd());
		int sum = 0;

		// 1. 두 입력값 사이(입력값 포함)의 모든 값을 더해줍니다.
		for (int i = startNum; i <= endNum; i++) {
			sum += i;
		}

		Result result = new Result();
		result.setResult(sum); // 2. Result객체의 result필드에 값을 SET 해줍니다. result의 타입은 제네릭입니다.
		
		System.out.println("startEndSum 실행");

		return new ResponseEntity<>(result, HttpStatus.OK); // 3. messageConvertor를 통해, json으로 응답합니다.
	}

	
	
	@GetMapping("/primeNumber/{num}")
	public ResponseEntity<?> primeNumber(@PathVariable int num) {

		PrimeNumber primeNumber = new PrimeNumber();
		List<Integer> list = new ArrayList();
		int count = 0;

		// 1. 입력값 num 까지 소수를 구하는 식
		for (int i = 2; i <= num; i++) {
			for (int j = 2; j <= i; j++) {
				if (i != j && i % j == 0) {
					break;
				}
				if (i == j) { 
					list.add(i); // 2. 소수일 경우 list에 담아줍니다.
					count++;     // 3. 소수의 개수를 카운팅 해줍니다.
				}
			}
		}

		// 4. 응답할 객체에 갯수와 소수가 담긴 리스트를 SET 해줍니다.
		primeNumber.setCount(count);
		primeNumber.setPn(list);
		
		System.out.println("primeNumber실행");
		
		return new ResponseEntity<>(primeNumber, HttpStatus.OK); // 5. messageConvertor를 통해(부트지원), json으로 응답합니다.
	}

}
