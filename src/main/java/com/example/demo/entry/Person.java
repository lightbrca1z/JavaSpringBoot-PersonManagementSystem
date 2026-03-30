package com.example.demo.entry;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;


@Entity
@Table(name="people")
public class Person {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) 
    @Column
    private Long id;
    
    @Column(length = 50, nullable = false)
    @NotBlank(message="名前は書かないとダメ！")
    private String name;
    
    @Column(length = 200, nullable = true)
    @Email(message="メールアドレスを記述して下さい！")
    private String mail;
    
    @Column(nullable = true)
    @Min(value=0, message="マイナスの歳は存在しません！")
    @Max(value=200, message="200歳以上で現実的に不可能です！")
    private Integer age;

    @Column(nullable = true)
    private String memo;
    
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    
    public String getName() {
    	return name;
    }
    
    public void setName(String name) {
    	this.name = name;
    }
    
    public String getMail() {
    	return mail;
    }
    
    public void setMail(String mail) {
    	this.mail = mail;
    }
    
    public Integer getAge() {
    	return age;
    }
    
    public void setAge(Integer age) {
    	this.age = age;
    }
    
    public String getMemo() {
    	return memo;
    }
    
    public void setMemo(String memo) {
    	this.memo = memo;
    }
    
}
