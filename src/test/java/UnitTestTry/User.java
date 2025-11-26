package UnitTestTry;

import groovy.transform.ToString;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;



//use of lombok to reduce boilerplate code
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class User {
    private Long id;
    private String name;
    private String email;
    private int age;
    private boolean active;
    private LocalDateTime createdAt;
    private LocalDateTime lastModified;
    

    // // Getters and Setters
    // public Long getId() {
    //     return id;
    // }

    // public void setId(Long id) {
    //     this.id = id;
    // }

    // public String getName() {
    //     return name;
    // }

    // public void setName(String name) {
    //     this.name = name;
    // }

    // public String getEmail() {
    //     return email;
    // }

    // public void setEmail(String email) {
    //     this.email = email;
    // }

    // public int getAge() {
    //     return age;
    // }

    // public void setAge(int age) {
    //     this.age = age;
    // }

    // public boolean isActive() {
    //     return active;
    // }

    // public void setActive(boolean active) {
    //     this.active = active;
    // }

    // @Override
    // public String toString() {
    //     return "User{" +
    //             "id=" + id +
    //             ", name='" + name + '\'' +
    //             ", email='" + email + '\'' +
    //             ", age=" + age +
    //             ", active=" + active +
    //             '}';
    // }
    
}
