package com.microprojects.user.service;

import com.microprojects.user.VO.Department;
import com.microprojects.user.VO.ResponseTemplateVO;
import com.microprojects.user.entity.User;
import com.microprojects.user.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@Slf4j
public class UserService {

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private RestTemplate restTemplate;

	public User saveUser(User user) {
		log.info("inside saveUser of userService");
		return userRepository.save(user);
	}
	public ResponseTemplateVO getUserWithDepartment(Long userId) {
		log.info("inside getUserWithDepartment user of userService");
		ResponseTemplateVO vo = new ResponseTemplateVO();
		User user = userRepository.findByUserId(userId);

		Department department = restTemplate
				.getForObject(
						"http://DEPARTMENT-SERVICE/departments/"+user.getDepartmentId(),
						Department.class);
		vo.setUser(user);
		vo.setDepartment(department);
		return vo;
	}
}
