package com.yly.androidroom

import androidx.room.*

@Entity(
    tableName = "pet",
    foreignKeys = [
        ForeignKey(entity = User::class, parentColumns = ["id"], childColumns = ["userId"])
    ],
    indices = [Index("userId")]
)
data class Pet(
    @PrimaryKey
    val id: Int,
    val userId: Int,
    val name: String
)

@Entity(
    tableName = "user"
)
data class User(
    @PrimaryKey
    val id: Int,
    val name: String
)

//含有关联查询的model不能为entity
data class UserNameAndAllPets(
    @Embedded
    val user: User,

//    @Relation(parentColumn = "id", entityColumn = "userId")
//    val pets: List<Pet>

    //关联查询,相当于外键查询,sql更方便
    @Relation(
        parentColumn = "id",
        entityColumn = "userId",
        entity = Pet::class,
        projection = ["name"]
    )
    val names: List<String>
)