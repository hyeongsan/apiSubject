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
public class SubjectConroller {

	private static final Logger log = LoggerFactory.getLogger(SubjectConroller.class);

	  @PostMapping("/realNumber")
	  public ResponseEntity<?> realNumber(@RequestBody RealNumber realNumber) {
		  		  
		  List<Integer> list = new ArrayList();
		  
		  int a = realNumber.getA();
		  int b = realNumber.getB();
		  int c = realNumber.getC();
		  
		  double determinant;
		  double root;
		  double x1,x2;
		  int a1,a2;
		  
		  determinant=(b*b)-(4*a*c);
		  root = Math.sqrt(determinant);
		  
		  if(determinant>0){

			  x1=(-b+root)/(2*a);

			  x2=(-b-root)/(2*a);

			  System.out.print("이차방정식의 근은 " +Integer.parseInt(String.format("%,.0f",x1))+"과"+Integer.parseInt(String.format("%,.0f",x2))+" 두개입니다.");
			
			  a1=Integer.parseInt(String.format("%,.0f",x1));
			  a2=Integer.parseInt(String.format("%,.0f",x2));
			  
			  list.add(a1);
			  list.add(a2);
			  
			  Collections.sort(list); // 오름차순 정렬
			  
			  if(a1<0||a2<0) {
				  throw new CustomApiException("실수가 아닙니다.");
			  }			  			  
			  
			  Result<List> result= new Result();		  		 
			  result.setResult(list);		
			  
			  System.out.println("realNumber 실행");
			  
			  return new ResponseEntity<>(result, HttpStatus.OK);	
			  
		  }else if(determinant==0) {

				  x1=(-b+root)/(2*a);
				  throw new CustomApiException("이차방정식의 근은 중근으로, "+x1+"입니다.");
		
		  }else {
			  
			  	  throw new CustomApiException("이차방정식의 근이 없습니다.");
			  	  
		  }
	  }



	@PostMapping("/startEndSum")
	public ResponseEntity<?> startEndSum(@RequestBody StartEndNumber number) {

		int startNum = Integer.parseInt(number.getStart());
		int endNum = Integer.parseInt(number.getEnd());
		int sum = 0;

		for (int i = startNum; i <= endNum; i++) {
			sum += i;
		}

		Result result = new Result();
		result.setResult(String.valueOf(sum));
		
		System.out.println("startEndSum 실행");

		return new ResponseEntity<>(result, HttpStatus.OK);
	}

	
	
	@GetMapping("/primeNumber/{num}")
	public ResponseEntity<?> primeNumber(@PathVariable int num) {

		PrimeNumber primeNumber = new PrimeNumber();
		List<Integer> list = new ArrayList();
		int count = 0;

		for (int i = 2; i <= num; i++) {
			for (int j = 2; j <= i; j++) {
				if (i != j && i % j == 0) {
					break;
				}
				if (i == j) {
					list.add(i);
					count++;
				}
			}
		}

		primeNumber.setCount(count);
		primeNumber.setPn(list);
		
		System.out.println("primeNumber실행");
		
		return new ResponseEntity<>(primeNumber, HttpStatus.OK);
	}

}
