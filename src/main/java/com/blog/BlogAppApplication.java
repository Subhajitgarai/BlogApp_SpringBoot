package com.blog;

import com.blog.config.AppConstants;
import com.blog.dao.RoleRepo;
import com.blog.enteties.Role;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;

@SpringBootApplication
@OpenAPIDefinition
public class BlogAppApplication implements CommandLineRunner {
	@Autowired
	private RoleRepo roleRepo;

	public static void main(String[] args) {
		SpringApplication.run(BlogAppApplication.class, args);

	}
	@Bean
	public ModelMapper modelMapper(){
		return new ModelMapper();
	}

	@Override
	public void run(String... args) throws Exception {
		try{
			Role role=new Role();
			role.setRoleId(AppConstants.ADMIN_USER);
			role.setRoleName("ROLE_ADMIN");
			Role role1=new Role();
			role1.setRoleId(AppConstants.NORMAL_USER);
			role1.setRoleName("ROLE_NORMAL");
			List<Role> predefinedRoles = List.of(role, role1);
			List<Role> roles = roleRepo.saveAll(predefinedRoles);
			roles.forEach(r->{
				System.out.println(r.getRoleName());
			});
		}
		catch (Exception e){
			e.printStackTrace();
		}
	}
}
