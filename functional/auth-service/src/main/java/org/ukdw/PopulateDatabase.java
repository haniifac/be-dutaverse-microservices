package org.ukdw;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.ukdw.entity.UserAccountEntity;
import org.ukdw.repository.UserAccountRepository;

@Component
public class PopulateDatabase implements CommandLineRunner {
    @Autowired
    private UserAccountRepository userAccountRepository;

    @Override
    public void run(String... args) throws Exception {
        // Check if there are already users in the database to avoid duplicates
        if (userAccountRepository.count() == 0) {
            createInitialUsers();
            System.out.println("Default users inserted successfully.");
        } else {
            System.out.println("Users already exist, skipping insert.");
        }
    }

    private void createInitialUsers() {
        // Sample user data
        UserAccountEntity newUser2 = new UserAccountEntity(
                "admin@example.com",
                "Admin",
                "password",
                "REG124",
                "admin"
        );
        UserAccountEntity newUser3 = new UserAccountEntity(
                "teacher@example.com",
                "UserTeacher",
                "password",
                "REG125",
                "teacher"
        );

        UserAccountEntity teacher = new UserAccountEntity(
                "dendy.prtha@staff.ukdw.ac.id",
                "dendy",
                "qwe123",
                "TCR001",
                "teacher"
        );

        UserAccountEntity student = new UserAccountEntity(
                "prtha@student.ukdw.ac.id",
                "prtha",
                "qwe123",
                "STD001",
                "student"
        );

        // Save users to the database
        userAccountRepository.save(newUser2);
        userAccountRepository.save(newUser3);
        userAccountRepository.save(teacher);
        userAccountRepository.save(student);
    }
}
