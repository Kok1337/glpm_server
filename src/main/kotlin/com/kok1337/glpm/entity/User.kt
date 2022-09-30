package com.kok1337.glpm.entity

import javax.persistence.*

@Entity
@Table(name = "info_users")
class User {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    var id: Long? = null
    @Column(name = "login")
    var login: String? = null
    @Column(name = "pass")
    var password: String? = null
    @Column(name = "filial_id")
    var filialId: Long? = null
}