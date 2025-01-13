package com.example.moviecollection.data.repositories

import com.example.moviecollection.data.database.daos.RelationshipsDAO

class RelationshipsRepository (
    private val relationshipsDAO: RelationshipsDAO
){
    val ofGenre = relationshipsDAO.getAllOfGenre()
    val inFormat = relationshipsDAO.getAllInFormat()
    val withActors = relationshipsDAO.getAllWithActors()
}