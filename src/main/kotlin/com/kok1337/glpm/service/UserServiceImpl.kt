package com.kok1337.glpm.service

import com.kok1337.glpm.entity.User
import com.kok1337.glpm.repository.UserRepository
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException
import org.springframework.stereotype.Service

@Service
class UserServiceImpl constructor(
    private val userRepository: UserRepository,
) : UserService {
    override fun getUserById(id: Long): User {
        return userRepository.findById(id).orElseThrow { throw NotFoundException() }
    }

    override fun getUserByUsername(username: String): User {
        return userRepository.findByLogin(username).orElseThrow { throw NotFoundException() }
    }

    override fun getUserIdByLoginAndPassword(login: String, password: String): Long {
        return userRepository.getUserIdByLoginAndPass(login, password)
    }
}