package com.yly.androidroom

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.yly.androidroom.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class MainActivity : AppCompatActivity(), CoroutineScope {

    private val job = Job()

    override val coroutineContext: CoroutineContext
        get() = job

    private lateinit var binding: ActivityMainBinding

    private val petDao by lazy {
        AppDatabase.getInstance(this).petDao()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.getData.setOnClickListener {
            petDao.loadUserAndPets().observe(this, Observer {
                println(it)
            })
        }

        binding.insert.setOnClickListener {
            launch {
                AppDatabase.getInstance(this@MainActivity).runInTransaction {
                    //事务
                    val user_A = User(2, "yuliyang")
                    val user_B = User(2, "yuliyang")
                    petDao.insertUser(user_A)
                    petDao.insertUser(user_B)
                }
            }
        }

        binding.dynamicSql.setOnClickListener {
            launch {
//                AppDatabase.getInstance(context = this@MainActivity).compileStatement(
//                    """
//                   insert into user(id,name) values (3,"new")
//                """.trimIndent()
//                ).executeInsert()

                val result = AppDatabase.getInstance(context = this@MainActivity).compileStatement(
                    """
                   select name from user where id = 1
                """.trimIndent()
                ).simpleQueryForString()
                println(result)
            }
        }
    }

    override fun onDestroy() {
        job.cancel()
        super.onDestroy()
    }
}
