package com.kawanansemut.core.entity

import com.kawanansemut.core.utility.Validate
import org.springframework.data.repository.CrudRepository
import java.util.*
import javax.persistence.*

@Entity
@EntityListeners(UserListeners::class)
class User(
        @Column(unique = true, nullable = false)
        var email: String,
        @Column(unique = true, nullable = false)
        val username: String,
        var password: String,
        var firstName: String,
        var lastName: String,
        var enabled: Boolean = true,
        var accountNonLocked: Boolean = true,
        var accountNonExpired: Boolean = true,
        var credentialsNonExpired: Boolean = true
) : BaseEntity()


interface UserRepository : CrudRepository<User, Long> {
    fun findByUsername(username: String): Optional<User>
    fun findByUsernameIgnoreCase(username: String): Optional<User>
    fun findByEmailIgnoreCase(email: String): Optional<User>
}

class UserListeners {
    @PrePersist
    @PreUpdate
    fun beforeSave(user: User) {
        Validate.stringNotEmpty(user.email).orThrow(Exception("Email is required"))
        Validate.stringNotEmpty(user.username).orThrow(Exception("Username is required"))
        Validate.stringNotEmpty(user.password).orThrow(Exception("Password is required"))
        Validate.stringNotEmpty(user.firstName).orThrow(Exception("First name is required"))
        Validate.stringNotEmpty(user.lastName).orThrow(Exception("Last name is required"))
        Validate.emailFormat(user.email).orThrow(Exception("Not valid email address format"))
        Validate.stringLengthInBetween(user.username, 6, 10).orThrow(Exception("Username length must be between 6 to 10"))

        Validate.usernameFormat(user.username).orThrow(Exception("Username must be only contains alpha numeric and underscore"))

//        val userRepository = AppContext.getBean(UserRepository::class.java)
//        val userByUsername = userRepository.findByUsernameIgnoreCase(user.username.toLowerCase())
//        val userByEmail = userRepository.findByEmailIgnoreCase(user.email.toLowerCase())
//        val passwordEncoder = AppContext.getBean(PasswordEncoder::class.java)
//        if (user.id > 0) {
//            // update user
//            val userBeforeUpdate = userRepository.findById(user.id).orElseThrow { Exception("User not found") }
//            Validate.isTrue(!(userByUsername.isPresent && userByUsername.get().id != user.id)).orThrow(Exception("Username already in use"))
//            Validate.isTrue(!(userByEmail.isPresent && userByEmail.get().id != user.id)).orThrow(Exception("Email already in use"))
//
//            if (userBeforeUpdate.password != user.password) {
//                //user change password ===> re-encode password
//                Validate.stringLengthInBetween(user.password, 6, 50).orThrow(Exception("Username length must be between 6 to 50"))
//                user.password = passwordEncoder.encode(user.password)
//            }
//        } else {
//            // create user
//            Validate.stringLengthInBetween(user.password, 6, 50).orThrow(Exception("Username length must be between 6 to 50"))
//            user.password = passwordEncoder.encode(user.password)
//            Validate.isTrue(!userByUsername.isPresent).orThrow(Exception("Username already in use"))
//            Validate.isTrue(!userByEmail.isPresent).orThrow(Exception("Email already in use"))
//        }
    }

    @PostPersist
    fun afterCreate(user: User) {
        //send email
    }
}