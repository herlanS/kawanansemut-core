package com.kawanansemut.core.entity

import com.fasterxml.jackson.annotation.JsonBackReference
import com.kawanansemut.core.utility.U
import org.hibernate.annotations.NaturalId
import java.time.LocalDateTime
import java.io.*
import javax.persistence.*


@MappedSuperclass
open class BaseEntity : Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonBackReference
    val id: Long = 0L

    @NaturalId
    @Column(unique = true)
    var uuid: String? = null

    @JsonBackReference
    var deleted: Boolean = false

    @Basic
    var updated: LocalDateTime? = null

    @Basic
    var created: LocalDateTime? = null


    @PreUpdate
    internal fun onPreUpdate() {
        updated = LocalDateTime.now()
    }

    @PrePersist
    internal fun onPrePersist() {
        uuid = U.generateSlimUUID()
        created = LocalDateTime.now()
        updated = created
    }
}

@Entity
class ContentType(val name: String) : BaseEntity()

@Entity
class Role(val name: String, val system:Boolean=false) : BaseEntity()

@Entity
class Permission(val name: String, val contentType: ContentType,val system:Boolean=false) : BaseEntity()

