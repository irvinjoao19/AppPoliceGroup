package com.dsige.police.group.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.dsige.police.group.data.local.model.Empresa

@Dao
interface EmpresaDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertEmpresaTask(c: Empresa)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertEmpresaListTask(c: List<Empresa>)

    @Update
    fun updateEmpresaTask(vararg c: Empresa)

    @Delete
    fun deleteEmpresaTask(c: Empresa)

    @Query("SELECT * FROM Empresa")
    fun getEmpresas(): LiveData<List<Empresa>>

    @Query("DELETE FROM Empresa")
    fun deleteAll()
}